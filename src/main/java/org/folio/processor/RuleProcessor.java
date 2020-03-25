package org.folio.processor;

import io.vertx.core.json.JsonObject;
import org.folio.writer.RecordWriter;
import org.folio.writer.RecordField;
import org.folio.writer.impl.MarcRecordWriter;
import org.folio.processor.rule.Rule;
import org.folio.processor.rule.RuleContainer;
import org.folio.processor.translation.Settings;
import org.folio.processor.translation.Translation;
import org.folio.processor.translation.TranslationFunction;
import org.folio.processor.translation.TranslationFunctionHolder;
import org.folio.reader.FieldReader;
import org.folio.reader.FieldValue;
import org.folio.reader.impl.JPathFieldReader;

import java.util.Iterator;
import java.util.List;

import static org.folio.reader.FieldValue.Type.*;

public final class RuleProcessor {
    private Settings settings;
    private JsonObject rules;

    public RuleProcessor(JsonObject rules) {
        this.rules = rules;
    }

    public String process(JsonObject instance) {
        FieldReader fieldReader = new JPathFieldReader(instance);
        RecordWriter marcRecordWriter = new MarcRecordWriter();
        Iterator ruleIterator = rules.iterator();
        while (ruleIterator.hasNext()) {
            RuleContainer ruleContainer = new RuleContainer(JsonObject.mapFrom(ruleIterator.next()));
            ruleContainer.getRules().forEach(rule -> {
                FieldValue fieldValue = fieldReader.read(rule.getFrom());
                if (!MISSING.equals(fieldValue.getType())) {
                    RecordField recordField = createRecordField(rule, fieldValue);
                    recordField = runTranslation(rule, recordField);
                    marcRecordWriter.write(recordField);
                }
            });
        }
        return marcRecordWriter.getResult();
    }

    private RecordField createRecordField(Rule rule, FieldValue fieldValue) {
        RecordField recordField = new RecordField(rule.getTag());
        if (STRING.equals(fieldValue.getType())) {
            String stringValue = (String) fieldValue.getData();
            recordField.setData(stringValue);
        } else if (LIST.equals(fieldValue.getType())) {
            List<String> listValue = (List) fieldValue.getData();
            recordField.setData(String.join(" ", listValue));
        }
        recordField.setSubField(rule.getSubField());
        return recordField;
    }

    private RecordField runTranslation(Rule rule, RecordField recordField) {
        if (rule.getTranslation() != null) {
            Translation translation = rule.getTranslation();
            TranslationFunction function = TranslationFunctionHolder.valueOf(translation.getFunction().toUpperCase());
            function.apply(recordField, translation.getParameters(), settings);
        }
        return recordField;
    }
}