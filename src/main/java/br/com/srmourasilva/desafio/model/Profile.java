package br.com.srmourasilva.desafio.model;

public enum Profile {
    /**
     * Full user controller access
     */
    ADMIN,

    /**
     * List user access
     */
    USER
    ;

    public static class Constant {
        public final static String ADMIN = "ADMIN";
        public final static String USER = "USER";
    }
}
