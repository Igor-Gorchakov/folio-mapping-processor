package org.folio.reader.field;

public class StringFieldValue implements FieldValue<String> {
    private String data;

    private StringFieldValue(String data) {
        this.data = data;
    }

    public static StringFieldValue of(String value) {
        return new StringFieldValue(value);
    }

    @Override
    public String getData() {
        return data;
    }

    @Override
    public Type getType() {
        return Type.STRING;
    }

    @Override
    public String toString() {
        return "StringData{" +
                "data='" + data + '\'' +
                '}';
    }
}
