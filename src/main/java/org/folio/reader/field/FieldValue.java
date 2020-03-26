package org.folio.reader.field;

public interface FieldValue<T> {
    T getData();

    Type getType();

    enum Type {
        MISSING,
        STRING,
        LIST
    }
}
