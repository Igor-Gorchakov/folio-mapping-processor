package org.folio.reader;

public interface FieldValue<T> {
    T getData();

    Type getType();

    enum Type {
        MISSING,
        STRING,
        LIST
    }
}
