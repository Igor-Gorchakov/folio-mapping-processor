package org.folio.rule;

import io.vertx.core.json.JsonObject;

import java.util.List;

public class Rule {

    private String from;
    private String tag;
    private String translation;
    private String field;

    public Rule(String tag, Object objectRule) {
        if (objectRule instanceof JsonObject) {
            JsonObject jsonRule = (JsonObject) objectRule;
            this.tag = tag;
            this.from = jsonRule.getString("from");
            this.translation = jsonRule.getString("translation");
            this.field = jsonRule.getString("field");
        } else {
            throw new IllegalArgumentException("Rule is not a JsonObject, rule: " + objectRule);
        }
    }



    public String getFrom() {
        return from;
    }

    public String getTag() {
        return tag;
    }

    public String getTranslation() {
        return translation;
    }

    public String getField() {
        return field;
    }
}
