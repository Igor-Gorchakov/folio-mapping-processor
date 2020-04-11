package org.folio.processor.rule;

import io.vertx.core.json.JsonObject;

public class DataSource {

    private String tag;
    private String subField;
    private String indicator;
    private String from;
    private Translation translation;

    public DataSource(String tag, JsonObject condition) {
        this.tag = tag;
        if (condition.containsKey("subfield")) {
            this.subField = condition.getString("subfield");
        } else if (condition.containsKey("indicator")) {
            this.indicator = condition.getString("indicator");
        }
        this.from = condition.getString("from");
        if (condition.containsKey("translation")) {
            this.translation = new Translation(condition.getJsonObject("translation"));
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

    public boolean isSubFieldCondition() {
        return this.subField != null;
    }

    public boolean isIndicatorCondition() {
        return this.indicator != null;
    }
}
