package com.insane.apiwtb.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Value("${HOME}")
    private String homePath;

    @Value("${config.mediaFolderName}")
    private String imageFolderName;

    @Value("${config.hostName}")
    private String host;

    private final String imageURL;

    public AppConfig() {
        // End point set on ImageController to get images from server
        // The string mentioned here must be the same as on ImageController endPoint
        this.imageURL = "/api/wtb-v1/image";
    }

    public String getImageURL(String filename) {
        return host+imageURL+"/"+filename;
    }

    public String getImagePath() {
        return this.homePath+"/"+imageFolderName;
    }

    public String getImagePath(String filename) {
        return this.homePath+"/"+imageFolderName+"/"+filename;
    }
}
