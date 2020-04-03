package org.folio.processor.rule;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.ArrayList;
import java.util.List;


public class Rule {
    private String tag;
    private List<Mapping> mapping = new ArrayList<>();

    public Rule(JsonObject rule) {
        this.tag = rule.getString("tag");
        JsonArray mapping = rule.getJsonArray("mapping");
        if (!mapping.isEmpty()) {
            mapping.forEach(item -> this.mapping.add(new Mapping((JsonObject) item)));
        } else {
            throw new IllegalArgumentException(String.format("Given rule does not have mapping, rule : %s", rule));
        }
    }

    public String getTag() {
        return tag;
    }

    public List<Mapping> getMapping() {
        return mapping;
    }

    public boolean isSimpleFieldRule() {
        return mapping.size() == 1;
    }

    public boolean isRepeatableFieldRule() {
        return mapping.size() > 1;
    }

    @Override
    public String toString() {
        return "Rule{" +
                "tag='" + tag + '\'' +
                ", mapping=" + mapping +
                '}';
    }
}
