package org.folio.reader;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import io.vertx.core.json.JsonObject;
import net.minidev.json.JSONArray;
import org.folio.reader.field.FieldValue;
import org.folio.reader.field.ListFieldValue;
import org.folio.reader.field.MissingFieldValue;
import org.folio.reader.field.StringFieldValue;

import java.util.ArrayList;
import java.util.List;

public class JPathSyntaxReader implements FieldReader {
    private final DocumentContext documentContext;

    public JPathSyntaxReader(JsonObject entity) {
        this.documentContext = JsonPath.parse(
                entity.encode(),
                Configuration.defaultConfiguration().addOptions(Option.SUPPRESS_EXCEPTIONS)
        );
    }

    @Override
    public FieldValue read(String path) {
        Object object = documentContext.read(path);
        if (object instanceof String) {
            String string = (String) object;
            return StringFieldValue.of(string);
        } else if (object instanceof JSONArray) {
            JSONArray array = (JSONArray) object;
            if (array.isEmpty()) {
                return MissingFieldValue.getInstance();
            } else {
                List<String> listOfStrings = new ArrayList<>();
                array.forEach(arrayItem -> listOfStrings.add(arrayItem.toString()));
                return ListFieldValue.of(listOfStrings);
            }
        }
        return MissingFieldValue.getInstance();
    }
}
