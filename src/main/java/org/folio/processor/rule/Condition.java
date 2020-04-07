package org.folio.processor.rule;

import io.vertx.core.json.JsonObject;

public class Condition {

    private String tag;
    private String subField;
    private String indicator;
    private String from;
    private Translation translation;

    public Condition(String tag, JsonObject mapping, boolean isRepeatableMappingRule) {
        this.tag = tag;
        if (mapping.containsKey("subField")) {
            this.subField = mapping.getString("subField");
        } else if (mapping.containsKey("indicator")) {
            this.indicator = mapping.getString("indicator");
        }
        this.from = mapping.getString("from");
        if (mapping.containsKey("translation")) {
            this.translation = new Translation(mapping.getJsonObject("translation"));
        }
        if (isRepeatableMappingRule) {
            if (this.subField == null && this.indicator == null) {
                throw new IllegalArgumentException("Subfield/Indicator is required to map repeatable field");
            }
        }
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

    public boolean isControlFieldCondition() {
        return this.subField == null;
    }

    public boolean isDataFieldCondition() {
        return this.subField != null;
    }

    public String getTarget() {
        return this.subField == null ? this.indicator : this.subField;
    }

    public String getTag() {
        return this.tag;
    }
}
