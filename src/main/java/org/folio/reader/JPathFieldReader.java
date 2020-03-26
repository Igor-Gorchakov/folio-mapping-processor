package org.folio.reader;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import io.vertx.core.json.JsonObject;
import net.minidev.json.JSONArray;
import org.folio.reader.field.FieldValue;
import org.folio.reader.field.ListFieldValue;
import org.folio.reader.field.MissingFieldValue;
import org.folio.reader.field.StringFieldValue;

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
            String string = (String) object;
            return StringFieldValue.of(string);
        } else if (object instanceof JSONArray) {
            JSONArray array = (JSONArray) object;
            List<String> listOfStrings = new ArrayList<>();
            array.forEach(arrayItem -> listOfStrings.add(arrayItem.toString()));
            return ListFieldValue.of(listOfStrings);
        }
        return MissingFieldValue.getInstance();
    }
}
