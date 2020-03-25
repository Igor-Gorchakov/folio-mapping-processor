package org.folio.writer.impl;

import org.folio.writer.RecordWriter;
import org.marc4j.MarcWriter;
import org.marc4j.MarcXmlWriter;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

public class XmlRecordWriter extends MarcRecordWriter implements RecordWriter {
    @Override
    public String getResult() {
        OutputStream outputStream = new ByteArrayOutputStream();
        MarcWriter writer = new MarcXmlWriter(outputStream, ENCODING);
        writer.write(RECORD);
        writer.close();
        return outputStream.toString();
    }
}
