package org.folio.reader.values;

public interface FieldValue<T> {
    T getValue();

    Type getType();

    enum Type {
        MISSING,
        SIMPLE,
        REPEATABLE
    }
}
