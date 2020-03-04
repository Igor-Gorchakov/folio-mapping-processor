package org.folio.builder;

import org.folio.value.Value;
import org.folio.rule.Rule;
import org.marc4j.MarcStreamWriter;
import org.marc4j.MarcWriter;
import org.marc4j.marc.DataField;
import org.marc4j.marc.MarcFactory;
import org.marc4j.marc.Record;


import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

public class MarcRecordBuilder {
    private MarcFactory factory;
    private Record record;

    public MarcRecordBuilder() {
        this.factory = MarcFactory.newInstance();;
        this.record = factory.newRecord();
    }

    public void addValue(Rule rule, Value data) {
        if (data.getType().equals(Value.Type.STRING)) {
                addNewControlField(rule.getTag(), data);
            } else {
                addNewDataField(rule.getTag(), data);
        }
    }

    private void addNewControlField(String tag, Value data) {
        System.out.println(tag + " " + data);
//        record.addVariableField(factory.newControlField(tag, data));
    }

    private void addNewDataField(String tag, Value data) {
        System.out.println(tag + " " + data);
//        record.addVariableField(factory.newDataField(tag, '/', '/', data));
    }

    public String getResult() {
        OutputStream outputStream = new ByteArrayOutputStream();
        MarcWriter writer = new MarcStreamWriter(outputStream, "UTF8");
        writer.write(this.record);
        writer.close();
        return outputStream.toString();
    }
}
