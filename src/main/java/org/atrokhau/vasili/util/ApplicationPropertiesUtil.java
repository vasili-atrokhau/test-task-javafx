package org.atrokhau.vasili.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ApplicationPropertiesUtil {

    private static final String PROPERTIES_PATH = "src/main/resources/application.properties";

    private ApplicationPropertiesUtil() {
    }

    public static Properties readProperties() {
        Properties properties = new Properties();

        try(FileInputStream inputStream = new FileInputStream(PROPERTIES_PATH)) {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return properties;
    }

}
