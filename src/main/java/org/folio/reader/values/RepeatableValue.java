package org.folio.reader.values;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

public class RepeatableValue implements FieldValue {
    private List<List<StringValue>> values = new ArrayList<>();

    @Override
    public Type getType() {
        return Type.REPEATABLE;
    }

    public boolean addEntry(List<StringValue> entry) {
        return this.values.add(entry);
    }
}
