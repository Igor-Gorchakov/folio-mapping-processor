package org.folio.processor.rule;

import io.vertx.core.json.JsonObject;
import org.folio.processor.translation.Translation;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

public class Rule {
    private String from;
    private String tag;
    private char subField;
    private Translation translation;

    public Rule(String tag, Object rule) {
        if (rule instanceof JsonObject) {
            JsonObject jsonRule = (JsonObject) rule;
            this.tag = requireNonNull(tag, format("Wrong rules configuration: 'tag' is missing: %s", jsonRule));
            this.from = requireNonNull(jsonRule.getString("from"), format("Wrong rules configuration: 'from' is missing: %s", jsonRule));
            if (jsonRule.containsKey("subField")) {
                this.subField = jsonRule.getString("subField").charAt(0);
            }
            if (jsonRule.containsKey("translation")) {
                this.translation = new Translation(jsonRule.getJsonObject("translation"));
            }
        } else {
            throw new IllegalArgumentException(format("Rule is not a JsonObject, rule: %s", rule));
        }
    }

    public String getFrom() {
        return from;
    }

    public String getTag() {
        return tag;
    }

    public Translation getTranslation() {
        return translation;
    }

    public char getSubField() {
        return subField;
    }
}
