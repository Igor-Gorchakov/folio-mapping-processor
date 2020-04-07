package org.folio.writer;

import org.folio.writer.fields.RecordControlField;
import org.folio.writer.fields.RecordDataField;

public interface RecordWriter {

    void writeControlField(RecordControlField field);

    void writeDataField(RecordDataField recordDataField);

    String getResult();
}
