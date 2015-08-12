package com.recaptcha.utils;

import java.util.Map;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * Created by aquarius on 8/12/2015.
 */
abstract public class ReCaptchaChecker {
    public static final String RECAPTCHA_VERIF_URL = "https://www.google.com/recaptcha/api/siteverify";
    public static final String RECAPTCHA_SERVER_KEY_SECRET = "6LdeAQsTAAAAACdaimkyRdJNUbqGXM3tmxdJe1Ql";

    public static ReCaptchaCheckerReponse checkReCaptcha(String secret, String response) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("secret", secret);
        map.add("response", response);

        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.postForObject(RECAPTCHA_VERIF_URL, map, ReCaptchaCheckerReponse.class);
    }

    private void checkRecaptcha(String recaptchaResponse) {
        ReCaptchaCheckerReponse rep = ReCaptchaChecker.checkReCaptcha(RECAPTCHA_SERVER_KEY_SECRET, recaptchaResponse);
        if (!rep.getSuccess()) {
            throw new RuntimeException("Captcha fail");
        }
    }
}
