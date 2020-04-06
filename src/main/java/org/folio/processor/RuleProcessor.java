package org.folio.processor;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.folio.processor.functions.Settings;
import org.folio.processor.rule.Rule;
import org.folio.reader.FieldReader;
import org.folio.reader.values.FieldValue;
import org.folio.reader.values.ListValue;
import org.folio.reader.values.RepeatableValue;
import org.folio.reader.values.SimpleValue;
import org.folio.reader.values.StringValue;
import org.folio.writer.RecordWriter;

import java.util.Iterator;

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
                    processSimpleValue((SimpleValue) fieldValue, writer);
                    break;
                case REPEATABLE:
                    processRepeatableValue((RepeatableValue) fieldValue, writer);
                    break;
                case MISSING:
                default:
                    continue;
            }
        }
        return writer.getResult();
    }

    private void processSimpleValue(SimpleValue simpleValue, RecordWriter writer) {
        if (STRING.equals(simpleValue.getSubType())) {
            StringValue stringValue = (StringValue) simpleValue;
            // run translation
        } else if (LIST_OF_STRING.equals(simpleValue.getSubType())) {
            ListValue listValue = (ListValue) simpleValue;
            // run translation
        }
        writer.writeSimpleField(simpleValue);
    }

    private void processRepeatableValue(RepeatableValue repeatableValue, RecordWriter writer) {
        writer.writeRepeatableField(repeatableValue);
    }

}