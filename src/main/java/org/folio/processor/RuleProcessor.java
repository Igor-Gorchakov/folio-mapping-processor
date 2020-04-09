package org.folio.processor;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.folio.processor.rule.Rule;
import org.folio.processor.rule.Translation;
import org.folio.processor.translations.Settings;
import org.folio.processor.translations.TranslationFunction;
import org.folio.processor.translations.TranslationsHolder;
import org.folio.reader.EntityReader;
import org.folio.reader.values.CompositeValue;
import org.folio.reader.values.ListValue;
import org.folio.reader.values.RuleValue;
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

    public String process(EntityReader entityReader, RecordWriter writer) {
        Iterator ruleIterator = rules.iterator();
        while (ruleIterator.hasNext()) {
            Rule rule = new Rule(JsonObject.mapFrom(ruleIterator.next()));
            RuleValue ruleValue = entityReader.read(rule);
            switch (ruleValue.getType()) {
                case SIMPLE:
                    SimpleValue value = (SimpleValue) ruleValue;
                    translate(value);
                    writer.write(value);
                    break;
                case COMPOSITE:
                    CompositeValue compositeValue = (CompositeValue) ruleValue;
                    translate(compositeValue);
                    writer.write(compositeValue);
                    break;
                case MISSING:
            }
        }

        return writer.getResult();
    }

    private void translate(SimpleValue simpleValue) {
        Translation translation = simpleValue.getCondition().getTranslation();
        if (translation != null) {
            TranslationFunction translationFunction = TranslationsHolder.lookup(translation);
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

    private void translate(CompositeValue compositeValue) {
        List<List<StringValue>> readValues = compositeValue.getValue();
        for (List<StringValue> readEntry : readValues) {
            readEntry.forEach(stringValue -> {
                Translation translation = stringValue.getCondition().getTranslation();
                if (translation != null) {
                    TranslationFunction translationFunction = TranslationsHolder.lookup(translation);
                    String readValue = stringValue.getValue();
                    String translatedValue = translationFunction.apply(readValue, translation.getParameters(), settings);
                    stringValue.setValue(translatedValue);
                }
            });
        }
    }


}