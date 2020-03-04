package org.folio.reader;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.apache.commons.collections4.iterators.ObjectArrayIterator;
import org.folio.value.ListValue;
import org.folio.value.MissingValue;
import org.folio.value.StringValue;
import org.folio.value.Value;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class InstanceReader implements Reader {
    private JsonObject instance;

    public InstanceReader(JsonObject instance) {
        this.instance = instance;
    }

    @Override
    public Value readFieldValueByPath(String path) {
        Iterator<String> pathItemsIterator = new ObjectArrayIterator(path.split("\\."));
        return findByPathRecursive(instance, pathItemsIterator);
    }

    private Value findByPathRecursive(JsonObject currentNode, Iterator<String> pathItemsIterator) {
        while (pathItemsIterator.hasNext()) {
            String pathItem = pathItemsIterator.next();
            if (currentNode.containsKey(pathItem)) {
                Object childNode = instance.getValue(pathItem);
                if (childNode instanceof String) {
                    return StringValue.of((String) childNode);
                } else if (childNode instanceof JsonArray) {
                    return findValueInArray(pathItemsIterator, (JsonArray) childNode);
                } else if (childNode instanceof JsonObject) {
                    currentNode = (JsonObject) childNode;
                }
            }
        }
        return MissingValue.getInstance();
    }

    private Value findValueInArray(Iterator<String> pathItemsIterator, JsonArray jsonArray) {
        if (pathItemsIterator.hasNext()) {
            // if pathItemsIterator has next - then assume to receive array of JsonObjects
            String arrayPathItem = pathItemsIterator.next();
            // assume that pathItem is the last one
            List<String> list = new ArrayList<>();
            jsonArray.getList().forEach(node -> {
                Map<String, String> objectMap = (Map) node;
                if (objectMap.containsKey(arrayPathItem)) {
                    list.add(objectMap.get(arrayPathItem));
                }
            });
            return ListValue.of(list);
        } else {
            // assume that array contains only strings
            return ListValue.of(jsonArray.getList());
        }
    }
}
