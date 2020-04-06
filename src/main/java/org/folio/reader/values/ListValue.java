package org.folio.reader.values;

import org.folio.processor.rule.Condition;

import java.util.List;

public class ListValue extends SimpleValue<List<String>> {
    private List<String> value;

    public ListValue(List<String> list, Condition condition) {
        this.value = list;
        this.condition = condition;
    }

    @Override
    public List<String> getValue() {
        return this.value;
    }

    public void setValue(List<String> value) {
        this.value = value;
    }

    @Override
    public SubType getSubType() {
        return SubType.LIST_OF_STRING;
    }
}
