package org.folio;

import io.vertx.core.json.JsonObject;

import java.util.List;

public class Rule {
    private String source;
    private String targetPath;
    private List<String> fields;
    private String translation;

    public Rule(String source, Object objectRule) {
        if (objectRule instanceof JsonObject) {
            JsonObject jsonRule = (JsonObject) objectRule;
            this.targetPath = jsonRule.getString("target");
            this.translation = jsonRule.getString("translation");
            this.fields = jsonRule.getJsonArray("fields").getList();
            this.source = source;
        } else {
            throw new IllegalArgumentException("Rule is not a JsonObject");
        }
    }

    public String getSource() {
        return source;
    }

    public String getTargetPath() {
        return targetPath;
    }

    public List<String> getFields() {
        return fields;
    }

    public String getTranslation() {
        return translation;
    }
}
