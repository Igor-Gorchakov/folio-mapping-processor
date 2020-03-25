package org.folio.writer;

public class RecordField {
    private String tag;
    private String data;
    private char subField;
    private char indicator1 = '\\';
    private char indicator2 = '\\';

    public RecordField(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public char getSubField() {
        return subField;
    }

    public void setSubField(char subField) {
        this.subField = subField;
    }

    public char getIndicator1() {
        return indicator1;
    }

    public void setIndicator1(char indicator1) {
        this.indicator1 = indicator1;
    }

    public char getIndicator2() {
        return indicator2;
    }

    public void setIndicator2(char indicator2) {
        this.indicator2 = indicator2;
    }


    public boolean isControlField() {
        return false;
    }

    public boolean isDataField() {
        return true;
    }

    @Override
    public String toString() {
        return "RecordField{" +
                "tag='" + tag + '\'' +
                ", data='" + data + '\'' +
                ", subField=" + subField +
                ", indicator1=" + indicator1 +
                ", indicator2=" + indicator2 +
                '}';
    }
}
