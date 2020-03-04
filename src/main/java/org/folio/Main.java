package org.folio;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.apache.commons.io.IOUtils;

import java.io.FileReader;
import java.io.IOException;

// TODO create RuleProcessor only one time

public class Main {
    public static void main(String[] args) throws IOException {
        JsonObject rules = new JsonObject(IOUtils.toString(new FileReader("src/main/resources/rules.json")));
        JsonArray instances = new JsonArray(IOUtils.toString(new FileReader("src/main/resources/instances.json")));
        for (Object objectInstance : instances) {
            JsonObject instance = JsonObject.mapFrom(objectInstance);
            String marcRecord = new RuleProcessor().process(instance, rules);
            System.out.println("Marc record: " + marcRecord);
        }
    }
}
