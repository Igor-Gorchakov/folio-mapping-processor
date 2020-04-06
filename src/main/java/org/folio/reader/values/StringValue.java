package org.folio.reader.values;

import org.folio.processor.rule.Condition;

public class StringValue extends SimpleValue<String> {
    private String value;

    public StringValue(String value, Condition condition) {
        this.value = value;
        this.condition = condition;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public SubType getSubType() {
        return SubType.STRING;
    }
}
