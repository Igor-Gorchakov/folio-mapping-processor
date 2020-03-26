package org.folio.writer.impl;

import org.folio.writer.RecordWriter;
import org.folio.writer.RecordField;
import org.marc4j.MarcStreamWriter;
import org.marc4j.MarcWriter;
import org.marc4j.marc.DataField;
import org.marc4j.marc.MarcFactory;
import org.marc4j.marc.Record;
import org.marc4j.marc.Subfield;
import org.marc4j.marc.VariableField;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class MarcRecordWriter implements RecordWriter {
    protected final String ENCODING = StandardCharsets.UTF_8.name();
    private final MarcFactory FACTORY = MarcFactory.newInstance();
    protected final Record RECORD = FACTORY.newRecord();

    @Override
    public void write(RecordField recordField) {
        if (recordField.isControlField()) {
            RECORD.addVariableField(FACTORY.newControlField(recordField.getTag(), recordField.getData()));
        } else if (recordField.isDataField()) {
            List<VariableField> variableFields = RECORD.find(recordField.getTag(), "");
            if (variableFields.isEmpty()) {
                DataField dataField = FACTORY.newDataField(recordField.getTag(), recordField.getIndicator1(), recordField.getIndicator2());
                Subfield subfield = FACTORY.newSubfield(recordField.getSubField(), recordField.getData());
                dataField.addSubfield(subfield);
                RECORD.addVariableField(dataField);
            } else {
                DataField existingDataField = (DataField) variableFields.get(0);
                Subfield newSubField = FACTORY.newSubfield(recordField.getSubField(), recordField.getData());
                existingDataField.addSubfield(newSubField);
            }
        }
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
