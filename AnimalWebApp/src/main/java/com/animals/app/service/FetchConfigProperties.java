package com.animals.app.service;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class FetchConfigProperties {
    private static Logger LOG = LogManager.getLogger(FetchConfigProperties.class);

    public void fetchConfig(Properties prop) {
        //This file contains the reCAPTCHA config properties mentioned above.
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("project.properties").getFile());

        try (InputStream input = new FileInputStream(file)) {
            prop.load(input);
        }
        catch (IOException e){
            LOG.error(e);
        }
    }
}
