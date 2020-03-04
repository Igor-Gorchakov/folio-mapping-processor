package org.folio.value;

public class MissingValue implements Value {
    private static final MissingValue VALUE = new MissingValue();

    private MissingValue() {}

    public static MissingValue getInstance() {
        return VALUE;
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
