package org.folio.writer;

import org.folio.reader.values.RepeatableValue;
import org.folio.reader.values.SimpleValue;

public interface RecordWriter {

    void write(SimpleValue simpleValue);

    void write(RepeatableValue repeatableValue);

    String getResult();
}
