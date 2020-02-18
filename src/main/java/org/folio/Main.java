package org.folio;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;


public class Main {
    public static void main(String[] args) throws IOException {
        // from instance to marc json
        JsonObject instance = new JsonObject(getFileContent("/Users/mac/IdeaProjects/mapping-poc/src/main/resources/Instance.json"));
        JsonObject rules = new JsonObject(getFileContent("/Users/mac/IdeaProjects/mapping-poc/src/main/resources/rules.json"));
        Iterator iterator = rules.iterator();
        while (iterator.hasNext()) {
            RuleContainer ruleContainer = new RuleContainer(JsonObject.mapFrom(iterator.next()));
            JsonObject marcField = new JsonObject();
            ruleContainer.getRules().forEach(rule -> {
                Object value = instance.getValue(rule.getTargetPath());
                if (value != null) {
                    writeValueToRecord(rule, value, marcField);
                }
            });
        }
    }

    private static void writeValueToRecord(Rule rule, Object value, JsonObject marcField) {
        if (value instanceof String) {
            writeString(marcField, (String) value);
        } else if (value instanceof JsonObject) {
            // TODO read again
        } else if (value instanceof JsonArray) {
            // TODO read again
        } else {
            throw new IllegalArgumentException("Can not map the given value: " + value);
        }
    }

    private static void writeString(JsonObject marcField, String string) {
        System.out.println("String : " + string);
    }

    public static String getFileContent(String fileName) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(fileName));
        return new String(encoded, Charset.defaultCharset());
    }
}
