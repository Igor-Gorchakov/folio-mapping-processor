package org.folio;

import io.vertx.core.json.JsonObject;
import org.folio.builder.MarcRecordBuilder;
import org.folio.reader.InstanceReader;
import org.folio.reader.Reader;
import org.folio.value.Value;
import org.folio.rule.RuleContainer;

import java.util.Iterator;

public class RuleProcessor {

    public String process(JsonObject instance, JsonObject rules) {
        Reader reader = new InstanceReader(instance);
        MarcRecordBuilder marcRecordBuilder = new MarcRecordBuilder();
        Iterator ruleIterator = rules.iterator();
        while (ruleIterator.hasNext()) {
            RuleContainer ruleContainer = new RuleContainer(JsonObject.mapFrom(ruleIterator.next()));
            ruleContainer.getRules().forEach(rule -> {
                Value readValue = reader.readFieldValueByPath(rule.getFrom());
                Value finalValue = runTranslations(rule.getTranslation(), readValue);
                marcRecordBuilder.addValue(rule, finalValue);
            });
        }
        return marcRecordBuilder.getResult();
    }

    private Value runTranslations(String translation, Value value) {
        return value;
    }
}
