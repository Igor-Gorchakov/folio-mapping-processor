package org.folio.reader.field;

import java.util.List;

public class ListStringField implements FieldValue<List<String>> {
    private List<String> data;

    private ListStringField(List<String> data) {
        this.data = data;
    }

    public static ListStringField of(List<String> value) {
        return new ListStringField(value);
    }

    @Override
    public List<String> getValue() {
        return data;
    }

    @Override
    public Type getType() {
        return Type.LIST_OF_STRINGS;
    }
}
