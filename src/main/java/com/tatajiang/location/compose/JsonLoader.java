package com.tatajiang.location.compose;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;

@Component
public class JsonLoader {

    private final ResourceLoader resourceLoader;

    @Autowired
    public JsonLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public File loadJsonFile(String filename) {
        try {
            Resource resource = resourceLoader.getResource("static/:" + filename);
            return resource.getFile();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
