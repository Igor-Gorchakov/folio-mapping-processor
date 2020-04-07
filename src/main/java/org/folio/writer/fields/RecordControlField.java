package org.folio.writer.fields;

import org.folio.reader.values.SimpleValue;

public class RecordControlField {
    private String tag;
    private String data;

    public RecordControlField(SimpleValue value) {
        this.tag = value.getCondition().getTag();
        this.data = value.getCondition().getSubfield();
    }

    public String getTag() {
        return tag;
    }

    public String getData() {
        return data;
    }
}
