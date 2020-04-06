package org.folio.processor;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.folio.processor.functions.Settings;
import org.folio.processor.functions.TranslationFunction;
import org.folio.processor.functions.TranslationsHolder;
import org.folio.processor.rule.Rule;
import org.folio.processor.rule.Translation;
import org.folio.reader.FieldReader;
import org.folio.reader.values.FieldValue;
import org.folio.reader.values.ListValue;
import org.folio.reader.values.RepeatableValue;
import org.folio.reader.values.SimpleValue;
import org.folio.reader.values.StringValue;
import org.folio.writer.RecordWriter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.folio.reader.values.SimpleValue.SubType.LIST_OF_STRING;
import static org.folio.reader.values.SimpleValue.SubType.STRING;

public final class RuleProcessor {
    private Settings settings;
    private JsonArray rules;

    public RuleProcessor(JsonArray rules) {
        this.rules = rules;
    }

    public String process(FieldReader reader, RecordWriter writer) {
        Iterator ruleIterator = rules.iterator();
        while (ruleIterator.hasNext()) {
            Rule rule = new Rule(JsonObject.mapFrom(ruleIterator.next()));
            FieldValue fieldValue = reader.read(rule);
            switch (fieldValue.getType()) {
                case SIMPLE:
                    SimpleValue simpleValue = (SimpleValue) fieldValue;
                    applyTranslation(simpleValue);
                    writer.writeSimpleValue(simpleValue);
                    break;
                case REPEATABLE:
                    RepeatableValue repeatableValue = (RepeatableValue) fieldValue;
                    applyTranslation(repeatableValue);
                    writer.writeRepeatableValue(repeatableValue);
                    break;
                case MISSING:
            }
        }
        return writer.getResult();
    }

    private void applyTranslation(SimpleValue simpleValue) {
        Translation translation = simpleValue.getCondition().getTranslation();
        if (translation != null) {
            TranslationFunction translationFunction = TranslationsHolder.valueOf(translation.getFunction());
            if (STRING.equals(simpleValue.getSubType())) {
                StringValue stringValue = (StringValue) simpleValue;
                String readValue = stringValue.getValue();
                String translatedValue = translationFunction.apply(readValue, translation.getParameters(), settings);
                stringValue.setValue(translatedValue);
            } else if (LIST_OF_STRING.equals(simpleValue.getSubType())) {
                ListValue listValue = (ListValue) simpleValue;
                List<String> translatedValues = new ArrayList<>();
                for (String readValue : listValue.getValue()) {
                    String translatedValue = translationFunction.apply(readValue, translation.getParameters(), settings);
                    translatedValues.add(translatedValue);
                }
                listValue.setValue(translatedValues);
            }
        }
    }

    private void applyTranslation(RepeatableValue repeatableValue) {
        List<List<StringValue>> readValues = repeatableValue.getValue();
        for (List<StringValue> readEntry : readValues) {
            readEntry.forEach(stringValue -> {
                Translation translation = stringValue.getCondition().getTranslation();
                if (translation != null) {
                    TranslationFunction translationFunction = TranslationsHolder.valueOf(translation.getFunction());
                    String readValue = stringValue.getValue();
                    String translatedValue = translationFunction.apply(readValue, translation.getParameters(), settings);
                    stringValue.setValue(translatedValue);
                }
            });
        }
    }
}