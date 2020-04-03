package org.folio.reader.field;

public interface FieldValue<T> {
    T getValue();

    Type getType();

    enum Type {
        MISSING,
        STRING,
        LIST_OF_STRINGS,
        OBJECT,
        LIST_OF_OBJECTS
    }
}
