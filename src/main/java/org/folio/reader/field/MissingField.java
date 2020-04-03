package org.folio.reader.field;

public class MissingField implements FieldValue {
    private static final MissingField INSTANCE = new MissingField();

    private MissingField() {
    }

    public static MissingField getInstance() {
        return INSTANCE;
    }

    @Override
    public Object getValue() {
        return null;
    }

    @Override
    public Type getType() {
        return Type.MISSING;
    }
}
