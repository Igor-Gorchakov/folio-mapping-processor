package org.folio.writer.impl;

import org.folio.reader.values.CompositeValue;
import org.folio.reader.values.SimpleValue;
import org.folio.writer.RecordWriter;
import org.folio.writer.fields.RecordControlField;
import org.folio.writer.fields.RecordDataField;

public abstract class AbstractRecordWriter implements RecordWriter {

    @Override
    public void write(SimpleValue simpleValue) {

    }

    @Override
    public void write(CompositeValue compositeValue) {

    }

    protected abstract void writeControlField(RecordControlField recordControlField);

    protected abstract void writeDataField(RecordDataField recordDataField);
}
