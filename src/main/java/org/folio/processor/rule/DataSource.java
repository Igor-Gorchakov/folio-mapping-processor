package org.folio.processor.rule;

import io.vertx.core.json.JsonObject;

public class DataSource {

    private String tag;
    private String subField;
    private String indicator;
    private String from;
    private Translation translation;

    public DataSource(String tag, JsonObject dataSource) {
        this.tag = tag;
        if (dataSource.containsKey("subfield")) {
            this.subField = dataSource.getString("subfield");
        } else if (dataSource.containsKey("indicator")) {
            this.indicator = dataSource.getString("indicator");
        }
        this.from = dataSource.getString("from");
        if (dataSource.containsKey("translation")) {
            this.translation = new Translation(dataSource.getJsonObject("translation"));
        }
    }

    public String getTag() {
        return this.tag;
    }

    public String getSubField() {
        return subField;
    }

    public String getIndicator() {
        return indicator;
    }

    public String getFrom() {
        return from;
    }

    public Translation getTranslation() {
        return translation;
    }

    public boolean isSubFieldDataSource() {
        return this.subField != null;
    }

    public boolean isIndicatorDataSource() {
        return this.indicator != null;
    }
}
