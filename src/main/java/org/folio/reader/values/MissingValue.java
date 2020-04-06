package org.folio.reader.values;

public class MissingValue implements FieldValue {
    private static final MissingValue INSTANCE = new MissingValue();

    private MissingValue() {
    }

    public static MissingValue getInstance() {
        return INSTANCE;
    }

    @Override
    public Type getType() {
        return Type.MISSING;
    }
}
