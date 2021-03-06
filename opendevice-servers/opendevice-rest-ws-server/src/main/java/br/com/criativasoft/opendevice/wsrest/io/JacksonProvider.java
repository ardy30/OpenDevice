/*
 * ******************************************************************************
 *  Copyright (c) 2013-2014 CriativaSoft (www.criativasoft.com.br)
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors:
 *  Ricardo JL Rufino - Initial API and Implementation
 * *****************************************************************************
 */

package br.com.criativasoft.opendevice.wsrest.io;

import br.com.criativasoft.opendevice.core.json.CommandJacksonMapper;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;


/**
 * JAX-RS Provider to configure JSON Serialization of Commands.</br>
 * It must be used in conjunction with 'jackson-jaxrs-json-provider' maven dependecy.
 *
 * @author Ricardo JL Rufino
 * @date 09/07/14.
 */
@Provider
public class JacksonProvider implements ContextResolver<ObjectMapper> {

    private final ObjectMapper mapper;

    public JacksonProvider() {
        mapper = new CommandJacksonMapper().getMapper();
//        SimpleModule module = new SimpleModule("ODev-Rest", new Version(0, 1, 0, "alpha"));
//        module.addSerializer(ErrorResponse.ErrorMessage.class, new HttpResponseSerializer());
//        mapper.registerModule(module);
    }

    @Override
    public ObjectMapper getContext(Class<?> type) {
        return mapper;
    }
}