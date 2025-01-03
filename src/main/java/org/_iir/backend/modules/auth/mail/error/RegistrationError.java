package org._iir.backend.modules.auth.mail.error;

import lombok.Data;

@Data
public class RegistrationError {
    private String field;
    private String message;

    public RegistrationError(String field, String message) {
        this.field = field;
        this.message = message;
    }
}
