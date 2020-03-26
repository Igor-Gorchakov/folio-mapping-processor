package org.folio.reader.field;

public class MissingFieldValue implements FieldValue {
    private static final MissingFieldValue DATA = new MissingFieldValue();

    private MissingFieldValue() {
    }

    public static MissingFieldValue getInstance() {
        return DATA;
    }

    @Override
    public Object getData() {
        return null;
    }

    @Override
    public Type getType() {
        return Type.MISSING;
    }
}
