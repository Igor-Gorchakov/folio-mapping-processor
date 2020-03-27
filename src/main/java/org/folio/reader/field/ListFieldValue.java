package org.folio.reader.field;

import java.util.List;

public class ListFieldValue implements FieldValue<List<String>> {
    private List<String> data;

    private ListFieldValue(List<String> data) {
        this.data = data;
    }

    public static ListFieldValue of(List<String> value) {
        return new ListFieldValue(value);
    }

    @Override
    public List<String> getData() {
        return data;
    }

    @Override
    public Type getType() {
        return Type.LIST;
    }
}
