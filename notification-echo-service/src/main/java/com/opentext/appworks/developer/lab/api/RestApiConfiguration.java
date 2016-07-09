/**
 * Copyright © 2016 Open Text.  All Rights Reserved.
 */
package com.opentext.appworks.developer.lab.api;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import org.glassfish.jersey.server.ResourceConfig;

public class RestApiConfiguration extends ResourceConfig {

    public RestApiConfiguration() {
        // scan this package for our JAX-RS endpoint classes
        packages("com.opentext.appworks.developer.lab.api");
        // use Jackson as the JSON marshalling implementation
        register(JacksonJsonProvider.class);
    }

}
