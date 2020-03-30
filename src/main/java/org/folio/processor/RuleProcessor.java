package org.folio.processor;

import io.vertx.core.json.JsonObject;
import org.folio.processor.rule.Rule;
import org.folio.processor.rule.RuleContainer;
import org.folio.processor.translation.Settings;
import org.folio.processor.translation.Translation;
import org.folio.processor.translation.TranslationFunction;
import org.folio.processor.translation.TranslationsHolder;
import org.folio.reader.FieldReader;
import org.folio.reader.field.FieldValue;
import org.folio.writer.RecordField;
import org.folio.writer.RecordWriter;

import java.util.Iterator;
import java.util.List;

import static org.folio.reader.field.FieldValue.Type.LIST;
import static org.folio.reader.field.FieldValue.Type.MISSING;
import static org.folio.reader.field.FieldValue.Type.STRING;

public final class RuleProcessor {
    private Settings settings;
    private JsonObject rules;
    private String LIST_FIELD_VALUE_DELIMITER = " ";

    public RuleProcessor(JsonObject rules) {
        this.rules = rules;
    }

    public String process(FieldReader fieldReader, RecordWriter recordWriter) {
        Iterator ruleIterator = rules.iterator();
        while (ruleIterator.hasNext()) {
            RuleContainer ruleContainer = new RuleContainer(JsonObject.mapFrom(ruleIterator.next()));
            ruleContainer.getRules().forEach(rule -> {
                FieldValue fieldValue = fieldReader.read(rule.getFrom());
                if (!MISSING.equals(fieldValue.getType())) {
                    RecordField recordField = createRecordField(rule, fieldValue);
                    recordField = runTranslation(rule, recordField);
                    recordWriter.write(recordField);
                }
            });
        }
        return recordWriter.getResult();
    }

    private RecordField createRecordField(Rule rule, FieldValue fieldValue) {
        RecordField recordField = new RecordField(rule.getTag());
        if (STRING.equals(fieldValue.getType())) {
            String stringValue = (String) fieldValue.getData();
            recordField.setData(stringValue);
        } else if (LIST.equals(fieldValue.getType())) {
            List<String> listValue = (List) fieldValue.getData();
            recordField.setData(String.join(LIST_FIELD_VALUE_DELIMITER, listValue));
        }
        recordField.setSubField(rule.getSubField());
        return recordField;
    }

    private RecordField runTranslation(Rule rule, RecordField recordField) {
        if (rule.getTranslation() != null) {
            Translation translation = rule.getTranslation();
            TranslationFunction function = TranslationsHolder.valueOf(translation.getFunction().toUpperCase());
            function.apply(recordField, translation.getParameters(), settings);
        }
        return recordField;
    }
}