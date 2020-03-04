package org.folio.value;

public interface Value<T> {
    T getValue();

    Type getType();

    enum Type {
        MISSING,
        STRING,
        LIST
    }
}
