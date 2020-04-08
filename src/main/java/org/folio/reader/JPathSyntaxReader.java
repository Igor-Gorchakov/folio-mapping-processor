package org.folio.reader;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import io.vertx.core.json.JsonObject;
import net.minidev.json.JSONArray;
import org.folio.processor.rule.Condition;
import org.folio.processor.rule.Rule;
import org.folio.reader.values.*;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;

public class JPathSyntaxReader extends AbstractReader {
    private final DocumentContext documentContext;

    public JPathSyntaxReader(JsonObject entity) {
        this.documentContext = JsonPath.parse(
                entity.encode(),
                Configuration.defaultConfiguration().addOptions(Option.SUPPRESS_EXCEPTIONS)
        );
    }

    @Override
    protected RuleValue readCompositeValue(Rule rule) {
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
            CompositeValue compositeValue = new CompositeValue();
            for (int widthIndex = 0; widthIndex < matrixWidth; widthIndex++) {
                List<StringValue> entry = new ArrayList<>();
                for (int lengthIndex = 0; lengthIndex < matrixLength; lengthIndex++) {
                    SimpleEntry<Condition, JSONArray> field = matrix.get(lengthIndex);
                    JSONArray jsonArray = field.getValue();
                    if (jsonArray.isEmpty()) {
                        entry.add(SimpleValue.ofNullable(field.getKey()));
                    } else {
                        entry.add(SimpleValue.of((String) jsonArray.get(widthIndex), field.getKey()));
                    }
                }
                compositeValue.addEntry(entry);
            }
            return compositeValue;
        }
    }

    @Override
    protected RuleValue readSimpleValue(Condition condition) {
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
