package org.folio.processor.translation;

import io.vertx.core.json.JsonObject;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

public class Translation {
    private String function;
    private JsonObject parameters;

    public Translation(JsonObject translation) {
        this.function = requireNonNull(translation.getString("function"), format("Wrong rules configuration: 'function' is missing: %s", translation));
        this.parameters = translation.getJsonObject("parameters");
    }

    public String getFunction() {
        return function;
    }

    public JsonObject getParameters() {
        return parameters;
    }
}
