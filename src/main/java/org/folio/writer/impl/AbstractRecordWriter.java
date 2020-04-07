package org.folio.writer.impl;

import org.folio.processor.rule.Condition;
import org.folio.reader.values.CompositeValue;
import org.folio.reader.values.SimpleValue;
import org.folio.reader.values.StringValue;
import org.folio.writer.RecordWriter;

import java.util.List;

import static org.folio.reader.values.SimpleValue.SubType.LIST_OF_STRING;
import static org.folio.reader.values.SimpleValue.SubType.STRING;

public abstract class AbstractRecordWriter implements RecordWriter {

    @Override
    public void write(SimpleValue simpleValue) {
        Condition condition = simpleValue.getCondition();
        if (STRING.equals(simpleValue.getSubType())) {
            StringValue stringValue = (StringValue) simpleValue;
            if (condition.isSubfieldCondition() || condition.isIndicatorCondition()) {
                writeDataField();
            } else {
                writeControlField(condition.getTag(), stringValue.getValue());
            }
        } else if (LIST_OF_STRING.equals(simpleValue.getSubType())) {
            if (condition.isSubfieldCondition() || condition.isIndicatorCondition()) {
                writeDataField();
            } else {
            }
        }
    }

    @Override
    public void write(CompositeValue compositeValue) {
        String tag = compositeValue.getValue().get(0).get(0).getCondition().getTag();
        for (List<StringValue> dataField : compositeValue.getValue()) {
            writeDataField();
        }
    }

    protected abstract void writeControlField(String tag, String data);

    protected abstract void writeDataField();
}
