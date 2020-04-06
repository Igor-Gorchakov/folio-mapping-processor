package org.folio.writer.impl;

import org.folio.processor.rule.Condition;
import org.folio.reader.values.RepeatableValue;
import org.folio.reader.values.SimpleValue;
import org.folio.reader.values.StringValue;
import org.folio.writer.RecordWriter;
import org.marc4j.MarcStreamWriter;
import org.marc4j.MarcWriter;
import org.marc4j.marc.DataField;
import org.marc4j.marc.MarcFactory;
import org.marc4j.marc.Record;
import org.marc4j.marc.Subfield;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import static org.folio.reader.values.SimpleValue.SubType.LIST_OF_STRING;
import static org.folio.reader.values.SimpleValue.SubType.STRING;

public class MarcRecordWriter implements RecordWriter {
    protected final String ENCODING = StandardCharsets.UTF_8.name();
    private final MarcFactory FACTORY = MarcFactory.newInstance();
    protected final Record RECORD = FACTORY.newRecord();

//    @Override
//    public void writeControlField(SimplifiedRecordField recordField) {
//        if (recordField.isControlField()) {
//            RECORD.addVariableField(FACTORY.newControlField(recordField.getTag(), recordField.getData()));
//        } else if (recordField.isDataField()) {
//            List<VariableField> variableFields = RECORD.find(recordField.getTag(), "");
//            if (variableFields.isEmpty()) {
//                DataField dataField = FACTORY.newDataField(recordField.getTag(), recordField.getIndicator1(), recordField.getIndicator2());
//                Subfield subfield = FACTORY.newSubfield(recordField.getSubField(), recordField.getData());
//                dataField.addSubfield(subfield);
//                RECORD.addVariableField(dataField);
//            } else {
//                DataField existingDataField = (DataField) variableFields.get(0);
//                Subfield newSubField = FACTORY.newSubfield(recordField.getSubField(), recordField.getData());
//                existingDataField.addSubfield(newSubField);
//            }
//        }
//    }
//
//    @Override
//    public void writeDataField(RepeatableRecordField repeatableRecordField) {
//
//    }

    @Override
    public void writeSimpleField(SimpleValue simpleValue) {
        Condition condition = simpleValue.getCondition();
        if (STRING.equals(simpleValue.getSubType())) {
            StringValue stringValue = (StringValue) simpleValue;
            if (condition.isControlFieldCondition()) {
                addControlField(condition.getTag(), stringValue.getValue());
            } else if (condition.isDataFieldCondition()) {
                addDataFieldWithSingleSubField(condition.getTag(), ' ', ' ', condition.getSubField().toCharArray()[0], stringValue.getValue());
            }
        } else if (LIST_OF_STRING.equals(simpleValue.getSubType())) {
            // addDataFieldWithMultipleSubFields(); - для каждого элемента массива порождаем новый сабфилд
        }
    }

    @Override
    public void writeRepeatableField(RepeatableValue dataField) {
        addDataFieldWithSingleSubField();
    }

    private void addControlField(String tag, String value) {
        this.RECORD.addVariableField(FACTORY.newControlField(tag, value));
    }

    private void addDataFieldWithSingleSubField(String tag, char indicator1, char indicator2, char subFieldCode, String subFieldValue) {
        DataField dataField = FACTORY.newDataField(tag, indicator1, indicator2);
        Subfield subfield = FACTORY.newSubfield(subFieldCode, subFieldValue);
        dataField.addSubfield(subfield);
        RECORD.addVariableField(dataField);
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
