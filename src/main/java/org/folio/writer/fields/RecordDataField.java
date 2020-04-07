package org.folio.writer.fields;

import org.folio.reader.values.SimpleValue;
import org.folio.reader.values.StringValue;

import java.util.List;
import java.util.Map;

public class RecordDataField {
    private String tag;
    private Character indicator1 = ' ';
    private Character indicator2 = ' ';
    private Map<Character, String> subfields;

    public RecordDataField(List<StringValue> dataFieldEntry) {

    }

    public RecordDataField(SimpleValue simpleValue) {

    }

    public String getTag() {
        return this.tag;
    }

    public Character getIndicator1() {
        return this.indicator1;
    }

    public Character getIndicator2() {
        return this.indicator2;
    }

    public Map<Character, String> getSubfields() {
        return this.subfields;
    }
}
