package org.folio.processor;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.folio.processor.rule.Rule;
import org.folio.processor.translation.Settings;
import org.folio.reader.FieldReader;
import org.folio.reader.field.FieldValue;
import org.folio.writer.RecordWriter;

import java.util.Iterator;

import static org.folio.reader.field.FieldValue.Type.MISSING;

public final class RuleProcessor {
    private Settings settings;
    private JsonArray rules;

    public RuleProcessor(JsonArray rules) {
        this.rules = rules;
    }

    public String process(FieldReader fieldReader, RecordWriter recordWriter) {
        Iterator ruleIterator = rules.iterator();
        while (ruleIterator.hasNext()) {
            Rule rule = new Rule(JsonObject.mapFrom(ruleIterator.next()));
            FieldValue fieldValue = fieldReader.read(rule);
            if (!MISSING.equals(fieldValue.getType())) {
//                RecordField recordField = createRecordField(ruleContainer, fieldValue);
//                recordWriter.write(recordField);
            }

//            ruleContainer.getRules().forEach(rule -> {
//                FieldValue fieldValue = fieldReader.read(rule.getFrom());
//                if (!MISSING.equals(fieldValue.getType())) {
//                    RecordField recordField = createRecordField(rule, fieldValue);
//                    recordWriter.write(recordField);
//                }
//            });
        }
        return recordWriter.getResult();
    }
//
//    private RecordField createRecordField(RuleContainer ruleContainer, FieldValue fieldValue) {
//        RecordField recordField = new RecordField(ruleContainer.getTag());
//        recordField.setData("");
//
////        if (STRING.equals(fieldValue.getType())) {
////            String stringValue = (String) fieldValue.getValue();
////            recordField.setData(stringValue);
////        } else if (LIST_OF_STRINGS.equals(fieldValue.getType())) {
////            List<String> listValue = (List) fieldValue.getValue();
////            recordField.setData(String.join(" ", listValue));
////        }
//
////        recordField.setSubField(f.getSubField());
//        return recordField;
//    }
//
//    private RecordField runTranslation(Rule rule, RecordField recordField) {
//        if (rule.getTranslation() != null) {
//            Translation translation = rule.getTranslation();
//            TranslationFunction function = TranslationsHolder.valueOf(translation.getFunction().toUpperCase());
//            function.apply(recordField, translation.getParameters(), settings);
//        }
//        return recordField;
//    }
}