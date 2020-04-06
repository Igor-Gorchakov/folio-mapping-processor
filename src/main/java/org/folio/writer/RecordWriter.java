package org.folio.writer;

import org.folio.reader.values.RepeatableValue;
import org.folio.reader.values.SimpleValue;

public interface RecordWriter {

    void writeSimpleField(SimpleValue simpleValue);

    void writeRepeatableField(RepeatableValue dataField);

    String getResult();
}
