package org.folio.reader.impl;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import io.vertx.core.json.JsonObject;
import net.minidev.json.JSONArray;
import org.folio.reader.FieldReader;
import org.folio.reader.FieldValue;

import java.util.ArrayList;
import java.util.List;

public class JPathFieldReader implements FieldReader {
    private final DocumentContext documentContext;

    public JPathFieldReader(JsonObject entity) {
        this.documentContext = JsonPath.parse(entity.toString());
    }

    @Override
    public FieldValue read(String path) {
        Object object = documentContext.read(path);
        if (object instanceof String) {
            // check on empty value
            String string = (String) object;
            return StringFieldValue.of(string);
        } else if (object instanceof JSONArray) {
            // check on empty value
            JSONArray array = (JSONArray) object;
            List<String> listOfStrings = new ArrayList<>();
            array.forEach(arrayItem -> listOfStrings.add(arrayItem.toString()));
            return ListFieldValue.of(listOfStrings);
        }
        return MissingFieldValue.getInstance();
    }
}
