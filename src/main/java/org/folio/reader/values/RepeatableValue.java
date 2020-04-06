package org.folio.reader.values;

import java.util.ArrayList;
import java.util.List;

public class RepeatableValue implements FieldValue<List<List<StringValue>>> {
    private List<List<StringValue>> value = new ArrayList<>();

    @Override
    public List<List<StringValue>> getValue() {
        return value;
    }

    @Override
    public Type getType() {
        return Type.REPEATABLE;
    }

    public boolean addEntry(List<StringValue> entry) {
        return this.value.add(entry);
    }


}
