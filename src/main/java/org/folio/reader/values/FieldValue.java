package org.folio.reader.values;

public interface FieldValue {
    Type getType();

    enum Type {
        MISSING,
        SIMPLE,
        REPEATABLE
    }
}
