package org.jwtdemo.security.service;

import java.io.Serializable;


public class RestResponse implements Serializable {


   	private static final long serialVersionUID = -7009960883704960632L;
	private final String message;

    public RestResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
