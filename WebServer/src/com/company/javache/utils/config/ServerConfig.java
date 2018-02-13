package com.company.javache.utils.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.company.javache.utils.ServerConstants.CONFIG_PATH;
import static com.company.javache.utils.ServerConstants.HANDLER_SUFFIX;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public class ServerConfig implements IConfig {
    private List<String> configurationContent;
    private static final String CONFIG_FULL_PATH = System.getProperty("user.dir") +
            File.separator +
            CONFIG_PATH;

    @Override
    public boolean readConfiguration() {
        try (BufferedReader br = new BufferedReader(new FileReader(CONFIG_FULL_PATH))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            String handlers = sb.toString();
            this.configurationContent = Arrays.stream(handlers.split("[ ,\r\n]")).filter(h -> h.endsWith(HANDLER_SUFFIX)).collect(Collectors.toList());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<String> getConfigurationContent() {
        return configurationContent;
    }
}
