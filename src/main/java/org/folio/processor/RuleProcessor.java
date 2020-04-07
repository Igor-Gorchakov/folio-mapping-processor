package org.folio.processor;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.folio.processor.functions.Settings;
import org.folio.processor.functions.TranslationFunction;
import org.folio.processor.functions.TranslationsHolder;
import org.folio.processor.rule.Condition;
import org.folio.processor.rule.Rule;
import org.folio.processor.rule.Translation;
import org.folio.reader.Reader;
import org.folio.reader.values.CompositeValue;
import org.folio.reader.values.ListValue;
import org.folio.reader.values.RuleValue;
import org.folio.reader.values.SimpleValue;
import org.folio.reader.values.StringValue;
import org.folio.writer.RecordWriter;
import org.folio.writer.fields.RecordControlField;
import org.folio.writer.fields.RecordDataField;

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

    public String process(Reader reader, RecordWriter writer) {
        Iterator ruleIterator = rules.iterator();
        while (ruleIterator.hasNext()) {
            Rule rule = new Rule(JsonObject.mapFrom(ruleIterator.next()));
            RuleValue ruleValue = reader.read(rule);
            switch (ruleValue.getType()) {
                case SIMPLE:
                    process((SimpleValue) ruleValue, writer);
                    break;
                case COMPOSITE:
                    process((CompositeValue) ruleValue, writer);
                    break;
                case MISSING:
            }
        }
        return writer.getResult();
    }

    private void process(SimpleValue simpleValue, RecordWriter writer) {
        translate(simpleValue);
        Condition condition = simpleValue.getCondition();
        if (condition.isSubfieldCondition() || condition.isIndicatorCondition()) {
            RecordDataField recordDataField = new RecordDataField(simpleValue);
            writer.writeDataField(recordDataField);
        } else {
            RecordControlField recordControlField = new RecordControlField(simpleValue);
            writer.writeControlField(recordControlField);
        }
    }

    private void process(CompositeValue compositeValue, RecordWriter writer) {
        translate(compositeValue);
        RecordDataField recordDataField = new RecordDataField(compositeValue);
        writer.writeDataField(recordDataField);
    }


    private void translate(SimpleValue simpleValue) {
        Translation translation = simpleValue.getCondition().getTranslation();
        if (translation != null) {
            TranslationFunction translationFunction = TranslationsHolder.lookup(translation.getFunction());
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
                    TranslationFunction translationFunction = TranslationsHolder.lookup(translation.getFunction());
                    String readValue = stringValue.getValue();
                    String translatedValue = translationFunction.apply(readValue, translation.getParameters(), settings);
                    stringValue.setValue(translatedValue);
                }
            });
        }
    }


}