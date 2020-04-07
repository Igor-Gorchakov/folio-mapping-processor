package org.folio.processor.rule;

import io.vertx.core.json.JsonObject;

public class Condition {

    private String tag;
    private String subfield;
    private String indicator;
    private String from;
    private Translation translation;

    public Condition(String tag, JsonObject condition) {
        this.tag = tag;
        if (condition.containsKey("subfield")) {
            this.subfield = condition.getString("subfield");
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

    public String getSubfield() {
        return subfield;
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

    public boolean isSubfieldCondition() {
        return this.subfield != null;
    }

    public boolean isIndicatorCondition() {
        return this.indicator != null;
    }
}
