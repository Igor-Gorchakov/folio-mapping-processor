package org.folio.reader.field;

import java.util.HashMap;
import java.util.Map;

public class ObjectField implements FieldValue<Map<String, FieldValue>> {
    private Map<String, FieldValue> value = new HashMap<>();

    @Override
    public Map<String, FieldValue> getValue() {
        return value;
    }

    @Override
    public Type getType() {
        return Type.OBJECT;
    }

    public ObjectField put(String key, FieldValue fieldValue) {
        this.value.put(key, fieldValue);
        return this;
    }
}
