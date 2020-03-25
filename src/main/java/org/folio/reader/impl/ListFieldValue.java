package org.folio.reader.impl;

import org.folio.reader.FieldValue;

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

    @Override
    public String toString() {
        return "ListData{" +
                "data=" + data +
                '}';
    }
}
