package org.folio.writer.impl;

import org.marc4j.MarcJsonWriter;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

public class JsonRecordWriter extends MarcRecordWriter {
    @Override
    public String getResult() {
        OutputStream outputStream = new ByteArrayOutputStream();
        org.marc4j.MarcWriter writer = new MarcJsonWriter(outputStream);
        writer.write(RECORD);
        writer.close();
        return outputStream.toString();
    }
}
