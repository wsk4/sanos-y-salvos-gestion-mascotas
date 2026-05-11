package com.sanosysalvos.gestormascotas.exception;

// Creamos nuestra propia excepción heredando de RuntimeException
public class MascotaNotFoundException extends RuntimeException {

    public MascotaNotFoundException(String message) {
        super(message);
    }
}
