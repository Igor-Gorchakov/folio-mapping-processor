package org.folio.rule;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class RuleContainer {
    private List<Rule> rules = new ArrayList<>();

    public RuleContainer(JsonObject container) {
        Iterator<Map.Entry<String, Object>> containerIterator = container.iterator();
        while (containerIterator.hasNext()) {
            Map.Entry<String, Object> entry = containerIterator.next();
            String tag = entry.getKey();
            JsonArray arrayOfRules = (JsonArray)entry.getValue();
            arrayOfRules.forEach(rule -> rules.add(new Rule(tag, rule)));
        }
    }

    public List<Rule> getRules() {
        return rules;
    }
}
