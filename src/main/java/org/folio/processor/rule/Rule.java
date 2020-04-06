package org.folio.processor.rule;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.ArrayList;
import java.util.List;


public class Rule {
    private List<Condition> conditions = new ArrayList<>();

    public Rule(JsonObject rule) {
        String tag = rule.getString("tag");
        JsonArray mapping = rule.getJsonArray("conditions");
        if (mapping.isEmpty()) {
            throw new IllegalArgumentException(String.format("Given rule does not have condition, rule : %s", rule));
        } else {
            mapping.forEach(item -> this.conditions.add(new Condition(tag, (JsonObject) item, mapping.size() > 1)));
        }
    }

    public List<Condition> getConditions() {
        return conditions;
    }
}
