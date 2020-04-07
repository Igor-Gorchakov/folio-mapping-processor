package org.folio.writer.fields;


public class RecordControlField {
    private String tag;
    private String data;

    public RecordControlField(String tag, String data) {
        this.tag = tag;
        this.data = data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTag() {
        return tag;
    }

    public String getData() {
        return data;
    }
}
