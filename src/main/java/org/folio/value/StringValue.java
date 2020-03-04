package org.folio.value;

public class StringValue implements Value<String> {
    private String value;

    private StringValue(String value) {
        this.value = value;
    }

    public static StringValue of(String value) {
        return new StringValue(value);
    }

    @Override
    public String getValue() {
        return null;
    }

    @Override
    public Type getType() {
        return Type.STRING;
    }

    @Override
    public String toString() {
        return "StringValue{" +
                "value='" + value + '\'' +
                '}';
    }
}
