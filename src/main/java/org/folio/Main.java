package org.folio;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.apache.commons.io.IOUtils;
import org.folio.processor.RuleProcessor;

import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        JsonArray instances = new JsonArray(IOUtils.toString(new FileReader("src/main/resources/instances.json")));
        JsonObject rules = new JsonObject(IOUtils.toString(new FileReader("src/main/resources/rules.json")));
        RuleProcessor ruleProcessor = new RuleProcessor(rules);
        for (Object objectInstance : instances) {
            JsonObject instance = JsonObject.mapFrom(objectInstance);
            String marcRecord = ruleProcessor.process(instance);
            System.out.println("Result marc record: " + marcRecord);
        }
    }
}
