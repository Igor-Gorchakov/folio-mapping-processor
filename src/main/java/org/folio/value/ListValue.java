package org.folio.value;

import java.util.List;

public class ListValue implements Value<List<String>> {
    private List<String> value;

    private ListValue(List<String> value) {
        this.value = value;
    }

    public static ListValue of(List<String> value) {
        return new ListValue(value);
    }

    @Override
    public List<String> getValue() {
        return value;
    }

    @Override
    public Type getType() {
        return Type.LIST;
    }

    @Override
    public String toString() {
        return "ListValue{" +
                "value=" + value +
                '}';
    }
}
