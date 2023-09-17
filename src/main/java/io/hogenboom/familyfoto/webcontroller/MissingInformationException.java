package io.hogenboom.familyfoto.webcontroller;

public class MissingInformationException extends IllegalArgumentException {

    public MissingInformationException(String field) {
        super("Field '%s' is not allowed to be null".formatted(field));
    }

}
