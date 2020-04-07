package org.folio.writer.fields;

import org.folio.reader.values.CompositeValue;

public class RecordDataField {
    private String tag;
    private char indicator1;
    private char indicator2;

    public RecordDataField(CompositeValue repeatableValue) {

    }


    public static RecordDataField of(CompositeValue compositeValue) {
        return new RecordDataField(compositeValue);
    }
}
