package org.folio.writer.fields;

import org.folio.processor.rule.Condition;
import org.folio.reader.values.SimpleValue;

public class RecordControlField {
    private String tag;
    private char indicator1 = ' ';
    private char indicator2 = ' ';
    private char subfieldCode;
    private String subfieldData;

    public RecordControlField(SimpleValue simpleValue) {
        Condition condition = simpleValue.getCondition();
        this.tag = condition.getTag();
//        if (condition.)
    }

    public static RecordControlField of(SimpleValue value) {
        return new RecordControlField(value);
    }

    public String getTag() {
        return tag;
    }

    public char getIndicator1() {
        return indicator1;
    }

    public char getIndicator2() {
        return indicator2;
    }

    public char getSubfieldCode() {
        return subfieldCode;
    }

    public String getSubfieldData() {
        return subfieldData;
    }
}
