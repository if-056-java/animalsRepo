package com.recaptcha.utils;

import org.codehaus.jackson.annotate.JsonProperty;

import javax.ws.rs.*;
import java.util.List;

/**
 * Created by aquarius on 8/12/2015.
 */
public class ReCaptchaCheckerReponse {
    @JsonProperty("success")
    private Boolean success;

    @JsonProperty("error-codes")
    private List<String> errorCodes;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<String> getErrorCodes() {
        return errorCodes;
    }

    public void setErrorCodes(List<String> errorCodes) {
        this.errorCodes = errorCodes;
    }
    ReCaptchaCheckerReponse() {}

};

