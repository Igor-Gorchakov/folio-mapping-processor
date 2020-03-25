package org.folio.writer;

public interface RecordWriter {

    void write(RecordField recordField);

    String getResult();
}
