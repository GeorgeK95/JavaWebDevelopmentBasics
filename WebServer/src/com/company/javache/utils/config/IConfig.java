package com.company.javache.utils.config;

import java.util.List;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public interface IConfig {
    boolean readConfiguration();

    List<String> getConfigurationContent();
}
