package org.folio.writer.impl;

import org.folio.processor.rule.Condition;
import org.folio.reader.values.CompositeValue;
import org.folio.reader.values.ListValue;
import org.folio.reader.values.SimpleValue;
import org.folio.reader.values.StringValue;
import org.folio.writer.RecordWriter;
import org.folio.writer.fields.RecordControlField;
import org.folio.writer.fields.RecordDataField;

import java.util.Collections;
import java.util.List;

import static org.folio.reader.values.SimpleValue.SubType.LIST_OF_STRING;
import static org.folio.reader.values.SimpleValue.SubType.STRING;

public abstract class AbstractRecordWriter implements RecordWriter {

    @Override
    public void write(SimpleValue simpleValue) {
        Condition condition = simpleValue.getCondition();
        String tag = condition.getTag();
        if (STRING.equals(simpleValue.getSubType())) {
            StringValue stringValue = (StringValue) simpleValue;
            if (condition.isSubfieldCondition() || condition.isIndicatorCondition()) {
                RecordDataField recordDataField = buildDataFieldForStringValues(tag, Collections.singletonList(stringValue));
                writeDataField(recordDataField);
            } else {
                RecordControlField recordControlField = new RecordControlField(tag, stringValue.getValue());
                writeControlField(recordControlField);
            }
        } else if (LIST_OF_STRING.equals(simpleValue.getSubType())) {
            ListValue listValue = (ListValue) simpleValue;
            if (condition.isSubfieldCondition() || condition.isIndicatorCondition()) {
                RecordDataField recordDataField = buildDataFieldForListOfStrings(listValue);
                writeDataField(recordDataField);
            } else {
                for (String value : listValue.getValue()) {
                    RecordControlField recordControlField = new RecordControlField(tag, value);
                    writeControlField(recordControlField);
                }
            }
        }
    }

    @Override
    public void write(CompositeValue compositeValue) {
        String tag = compositeValue.getValue().get(0).get(0).getCondition().getTag();
        for (List<StringValue> entry : compositeValue.getValue()) {
            RecordDataField recordDataField = buildDataFieldForStringValues(tag, entry);
            writeDataField(recordDataField);
        }
    }

    protected abstract void writeControlField(RecordControlField recordControlField);

    protected abstract void writeDataField(RecordDataField recordDataField);

    private RecordDataField buildDataFieldForListOfStrings(ListValue listValue) {
        Condition condition = listValue.getCondition();
        String tag = listValue.getCondition().getTag();
        RecordDataField field = new RecordDataField(tag);
        for (String stringValue : listValue.getValue()) {
            if (listValue.getCondition().isSubfieldCondition()) {
                char subFieldCode = condition.getSubfield().charAt(0);
                String subFieldData = stringValue;
                field.addSubField(subFieldCode, subFieldData);
            } else if (condition.isIndicatorCondition()) {
                char indicator = stringValue.charAt(0);
                if ("1".equals(condition.getIndicator())) {
                    field.setIndicator1(indicator);
                } else if ("2".equals(condition.getIndicator())) {
                    field.setIndicator2(indicator);
                }
            }
        }
        return field;
    }

    private RecordDataField buildDataFieldForStringValues(String tag, List<StringValue> entry) {
        RecordDataField field = new RecordDataField(tag);
        for (StringValue stringValue : entry) {
            Condition condition = stringValue.getCondition();
            if (condition.isSubfieldCondition()) {
                char subFieldCode = condition.getSubfield().charAt(0);
                String subFieldData = stringValue.getValue();
                field.addSubField(subFieldCode, subFieldData);
            } else if (condition.isIndicatorCondition()) {
                char indicator = stringValue.getValue().charAt(0);
                if ("1".equals(condition.getIndicator())) {
                    field.setIndicator1(indicator);
                } else if ("2".equals(condition.getIndicator())) {
                    field.setIndicator2(indicator);
                }
            }
        }
        return field;
    }

}
