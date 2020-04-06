package org.folio.reader;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import io.vertx.core.json.JsonObject;
import net.minidev.json.JSONArray;
import org.folio.processor.rule.Condition;
import org.folio.processor.rule.Rule;
import org.folio.reader.values.FieldValue;
import org.folio.reader.values.MissingValue;
import org.folio.reader.values.RepeatableValue;
import org.folio.reader.values.SimpleValue;
import org.folio.reader.values.StringValue;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;

public class JPathSyntaxReader extends AbstractFieldReader {
    private final DocumentContext documentContext;

    public JPathSyntaxReader(JsonObject entity) {
        this.documentContext = JsonPath.parse(
                entity.encode(),
                Configuration.defaultConfiguration().addOptions(Option.SUPPRESS_EXCEPTIONS)
        );
    }

    @Override
    protected FieldValue readRepeatableField(Rule rule) {
        List<SimpleEntry<Condition, JSONArray>> matrix = new ArrayList<>();
        for (Condition condition : rule.getConditions()) {
            String path = condition.getFrom();
            if (JsonPath.isPathDefinite(path)) {
                throw new IllegalStateException(format("The given mapping is intended to map only array of string: %s", condition));
            }
            JSONArray array = this.documentContext.read(condition.getFrom(), JSONArray.class);
            matrix.add(new SimpleEntry<>(condition, array));
        }
        int matrixLength = matrix.size();
        int matrixWidth = matrix.get(0).getValue().size();
        if (matrixWidth == 0) {
            return MissingValue.getInstance();
        } else {
            RepeatableValue repeatableField = new RepeatableValue();
            for (int widthIndex = 0; widthIndex < matrixWidth; widthIndex++) {
                List<StringValue> objectField = new ArrayList<>();
                for (int lengthIndex = 0; lengthIndex < matrixLength; lengthIndex++) {
                    SimpleEntry<Condition, JSONArray> entry = matrix.get(lengthIndex);
                    objectField.add(SimpleValue.of((String) entry.getValue().get(widthIndex), entry.getKey()));
                }
                repeatableField.addEntry(objectField);
            }
            return repeatableField;
        }
    }

    @Override
    protected FieldValue readSimpleField(Condition condition) {
        String path = condition.getFrom();
        Object readValue = documentContext.read(path);
        if (readValue instanceof String) {
            String string = (String) readValue;
            return SimpleValue.of(string, condition);
        } else if (readValue instanceof JSONArray) {
            JSONArray array = (JSONArray) readValue;
            if (array.isEmpty()) {
                return MissingValue.getInstance();
            }
            if (array.get(0) instanceof String) {
                List<String> listOfStrings = new ArrayList<>();
                array.forEach(arrayItem -> listOfStrings.add(arrayItem.toString()));
                return SimpleValue.of(listOfStrings, condition);
            } else if (array.get(0) instanceof Map) {
                throw new IllegalArgumentException(format("Reading a list of complex fields is not supported, mapping: %s", condition));
            }
        } else if (readValue instanceof Map) {
            throw new IllegalArgumentException(format("Reading a complex field is not supported, mapping: %s", condition));
        }
        return MissingValue.getInstance();
    }
}
