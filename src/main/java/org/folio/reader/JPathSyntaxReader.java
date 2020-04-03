package org.folio.reader;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import net.minidev.json.JSONArray;
import org.folio.processor.rule.Mapping;
import org.folio.processor.rule.Rule;
import org.folio.reader.field.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;

public class JPathSyntaxReader extends AbstractFieldReader {
    private final DocumentContext documentContext;

    public JPathSyntaxReader(JsonObject entity) {
        this.documentContext = JsonPath.parse(
                entity.encode(),
                Configuration.defaultConfiguration()
                        .addOptions(Option.SUPPRESS_EXCEPTIONS)
                        .addOptions(Option.DEFAULT_PATH_LEAF_TO_NULL)
        );
    }

    @Override
    protected FieldValue readRepeatableField(Rule rule) {
//        Object repeatableFieldValueType = documentContext.read(mappings.get(0).getFrom());
//        if (repeatableFieldValueType instanceof JSONArray) {
//            JSONArray jsonArrayValue = (JSONArray) repeatableFieldValueType;
//            if (jsonArrayValue.isEmpty()) {
//                return MissingField.getInstance();
//            } else {
//
//            }
//        } else {
//            throw new IllegalStateException(format("The given rule is intended to map array of complex objects. Rule: %s", rule));
//        }
//        List<JSONArray> arrays = new ArrayList<>();
//        for (Mapping mapping : rule.getMapping()) {
//            String pathToRead = mapping.getFrom();
//            if (JsonPath.isPathDefinite(pathToRead)) {
//                throw new IllegalStateException(format("The given mapping is intended to map only array of values: %s", mapping));
//            }
//            arrays.add(documentContext.read(pathToRead, JSONArray.class));
//        }
//        ListObjectField repeatableField = new ListObjectField();
//        for (int i = 0; i < arrays.size(); i++) {
//            ObjectField objectField = new ObjectField();
//            JSONArray array = arrays.get(i);
//
//
//
//            repeatableField.add()
//        }
    }

    @Override
    protected FieldValue readSimplifiedField(Rule rule) {
        Mapping mapping = rule.getMapping().get(0);
        String path = mapping.getFrom();
        Object readValue = documentContext.read(path);
        if (readValue instanceof String) {
            String string = (String) readValue;
            return StringField.of(string);
        } else if (readValue instanceof JSONArray) {
            JSONArray array = (JSONArray) readValue;
            if (array.isEmpty()) {
                return MissingField.getInstance();
            }
            if (array.get(0) instanceof String) {
                List<String> listOfStrings = new ArrayList<>();
                array.forEach(arrayItem -> listOfStrings.add(arrayItem.toString()));
                return ListStringField.of(listOfStrings);
            } else if (array.get(0) instanceof Map) {
                throw new IllegalArgumentException(format("Reading a list of complex fields is not supported by the single rule: %s", rule));
            }
        } else if (readValue instanceof Map) {
            throw new IllegalArgumentException(format("Reading a complex field is not supported by the single rule: %s", rule));
        }
        return MissingField.getInstance();
    }
}
