package com.bbva.wshomebanking.presentation.response.errors;

import java.util.List;

public class ErrorResponse {
    private final String message;
    private final List<String> errors;

    public ErrorResponse(String message, List<String> errors) {
        this.message = message;
        this.errors = errors;
    }

    public String getMessage() {
        return message;
    }

    public List<String> getErrors() {
        return errors;
    }

}
