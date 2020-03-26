package org.folio.reader;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.folio.reader.field.FieldValue;
import org.folio.reader.field.ListFieldValue;
import org.folio.reader.field.StringFieldValue;

import java.util.ArrayList;
import java.util.List;

public class JsonObjectFieldReader implements FieldReader {
    private JsonObject entity;

    public JsonObjectFieldReader(JsonObject entity) {
        this.entity = entity;
    }

    @Override
    public FieldValue read(String path) {
        String[] pathItems = path.split("\\.");
        return findByPathRecursively(pathItems, 0, entity);
    }

    private FieldValue findByPathRecursively(String[] pathItems, int index, Object parentNode) {
        if (parentNode instanceof String) {
            return StringFieldValue.of((String) parentNode);
        } else if (parentNode instanceof JsonObject) {
            Object childNode = ((JsonObject) parentNode).getValue(pathItems[index]);
            return findByPathRecursively(pathItems, ++index, childNode);
        } else if (parentNode instanceof JsonArray) {
            List<StringFieldValue> stringFieldValues = new ArrayList<>();
            JsonArray array =  (JsonArray) parentNode;
            for (Object arrayItem : array) {
                stringFieldValues.add((StringFieldValue) findByPathRecursively(pathItems, index, arrayItem));
            }
            List<String> stringList = new ArrayList<>();
            stringFieldValues.forEach(value -> stringList.add(value.getData()));
            return ListFieldValue.of(stringList);
        } else {
            throw new IllegalArgumentException("Wrong type of the parent node");
        }
    }
}
