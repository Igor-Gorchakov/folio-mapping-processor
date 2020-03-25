package org.folio.writer.impl;

import org.folio.writer.RecordWriter;
import org.marc4j.MarcJsonWriter;
import org.marc4j.MarcWriter;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

public class JsonRecordWriter extends MarcRecordWriter implements RecordWriter {
    @Override
    public String getResult() {
        OutputStream outputStream = new ByteArrayOutputStream();
        MarcWriter writer = new MarcJsonWriter(outputStream);
        writer.write(RECORD);
        writer.close();
        return outputStream.toString();
    }
}
