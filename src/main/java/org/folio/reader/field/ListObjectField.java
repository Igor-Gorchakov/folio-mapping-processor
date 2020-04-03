package org.folio.reader.field;

import java.util.ArrayList;
import java.util.List;

public class ListObjectField implements FieldValue<List<ObjectField>> {
    private List<ObjectField> value = new ArrayList<>();

    @Override
    public List<ObjectField> getValue() {
        return value;
    }

    @Override
    public Type getType() {
        return null;
    }

    public boolean add(ObjectField objectField) {
        return value.add(objectField);
    }
}
