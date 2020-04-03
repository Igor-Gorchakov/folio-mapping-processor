package org.folio.reader.field;

public class StringField implements FieldValue<String> {
    private String data;

    private StringField(String data) {
        this.data = data;
    }

    public static StringField of(String value) {
        return new StringField(value);
    }

    @Override
    public String getValue() {
        return data;
    }

    @Override
    public Type getType() {
        return Type.STRING;
    }
}
