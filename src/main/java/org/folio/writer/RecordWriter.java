package org.folio.writer;

import org.folio.reader.values.RepeatableValue;
import org.folio.reader.values.SimpleValue;

public interface RecordWriter {

    void writeSimpleValue(SimpleValue simpleValue);

    void writeRepeatableValue(RepeatableValue repeatableValue);

    String getResult();
}
