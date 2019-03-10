package com.yevbes.prueba.error;

import java.io.IOException;

class TypicodeException extends IOException {
    private int responseCode;
    private String message;

    public TypicodeException(int code, String message) {
        this.responseCode = code;
        this.message = message;
    }

    public int getResponseCode() {
        return responseCode;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
