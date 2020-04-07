package org.folio.writer.impl;

import org.folio.processor.rule.Condition;
import org.folio.reader.values.ListValue;
import org.folio.reader.values.StringValue;
import org.folio.writer.fields.RecordControlField;
import org.folio.writer.fields.RecordDataField;
import org.marc4j.MarcStreamWriter;
import org.marc4j.MarcWriter;
import org.marc4j.marc.DataField;
import org.marc4j.marc.MarcFactory;
import org.marc4j.marc.Record;
import org.marc4j.marc.Subfield;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class MarcRecordWriter extends AbstractRecordWriter {
    protected final String ENCODING = StandardCharsets.UTF_8.name();
    private final MarcFactory FACTORY = MarcFactory.newInstance();
    protected final Record RECORD = FACTORY.newRecord();

//    @Override
//    public void write(SimpleValue simpleValue) {
//        if (STRING.equals(simpleValue.getSubType())) {
//            StringValue stringValue = (StringValue) simpleValue;
//            if (simpleValue.getCondition().isControlFieldCondition()) {
//                addControlField(stringValue);
//            } else if (simpleValue.getCondition().isDataFieldCondition()) {
//                addDataFieldWithSingleSubField(stringValue);
//            }
//        } else if (LIST_OF_STRING.equals(simpleValue.getSubType())) {
//            ListValue listValue = (ListValue) simpleValue;
//            addDataFieldWithMultipleSubFields(listValue);
//        }
//    }
//
//    @Override
//    public void write(RepeatableValue repeatableValue) {
//        String tag = repeatableValue.getValue().get(0).get(0).getCondition().getTag();
//        for (List<StringValue> entry : repeatableValue.getValue()) {
//            DataField dataField = FACTORY.newDataField(tag, ' ', ' ');
//            entry.forEach(stringValue -> {
//                Condition condition = stringValue.getCondition();
//                char subFieldCode = condition.getSubField().charAt(0);
//                Subfield subfield = FACTORY.newSubfield(subFieldCode, stringValue.getValue());
//                dataField.addSubfield(subfield);
//            });
//            RECORD.addVariableField(dataField);
//        }
//    }

    private void addControlField(StringValue stringValue) {
        this.RECORD.addVariableField(FACTORY.newControlField(stringValue.getCondition().getTag(), stringValue.getValue()));
    }

    private void addDataFieldWithSingleSubField(StringValue stringValue) {
        Condition condition = stringValue.getCondition();
        char subFieldCode = condition.getSubfield().charAt(0);
        DataField dataField = FACTORY.newDataField(condition.getTag(), ' ', ' ');
        Subfield subfield = FACTORY.newSubfield(subFieldCode, stringValue.getValue());
        dataField.addSubfield(subfield);
        RECORD.addVariableField(dataField);
    }

    private void addDataFieldWithMultipleSubFields(ListValue listValue) {
        Condition condition = listValue.getCondition();
        char subFieldCode = condition.getSubfield().charAt(0);
        DataField dataField = FACTORY.newDataField(condition.getTag(), ' ', ' ');
        for (String stringValue : listValue.getValue()) {
            Subfield subfield = FACTORY.newSubfield(subFieldCode, stringValue);
            dataField.addSubfield(subfield);
        }
        RECORD.addVariableField(dataField);
    }

    @Override
    protected void writeControlField(RecordControlField recordControlField) {

    }

    @Override
    protected void writeDataField(RecordDataField recordDataField) {

    }

    @Override
    public String getResult() {
        OutputStream outputStream = new ByteArrayOutputStream();
        MarcWriter writer = new MarcStreamWriter(outputStream, ENCODING);
        writer.write(RECORD);
        writer.close();
        return outputStream.toString();
    }
}
