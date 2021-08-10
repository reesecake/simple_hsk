package com.reesecake.simple_hsk.vocab;

public class VocabNotFoundException extends RuntimeException {

    VocabNotFoundException(Long id) {
        super("Could not find vocab with id: " + id);
    }
}
