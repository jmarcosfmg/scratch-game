package org.jmarcosfmg.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jmarcosfmg.runner.dto.config.Config;

import java.io.File;
import java.io.IOException;

public class JsonParser {

    public Config readConfig(String file) throws IOException {
        assert (file != null && !file.isBlank());

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File(file), Config.class);
    }

    public void printAsJson(Object o) throws IOException {
        System.out.println(new ObjectMapper().writeValueAsString(o));
    }
}
