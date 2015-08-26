package com.animals.app.service;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import java.io.*;

/**
 * Created by oleg on 21.08.2015.
 */
public class CreateAnimalImage {

    private static final Logger LOG = LogManager.getLogger(CreateAnimalImage.class);

    public static String createAnimalImage(String imageBytes, String pathToImageStorage) {
        byte[] decodedBytes = null;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            decodedBytes = decoder.decodeBuffer(imageBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }

        InputStream is = new ByteArrayInputStream(decodedBytes);

        //назва файлу, яка буде зберігатись в базі
        String fileName = System.currentTimeMillis() + ".png";

        //куда буде грузитись
        String uploadedFileLocation = pathToImageStorage + fileName;

        //Load and save image
        OutputStream out = null;
        try {
            int read = 0;
            byte[] bytes = new byte[1024];

            out = new FileOutputStream(new File(uploadedFileLocation));
            while ((read = is.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.flush();
            out.close();

            return fileName;
        } catch (IOException e) {
            LOG.error(e);
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                LOG.error(ex);
            }
        }
        return null;
    }
}
