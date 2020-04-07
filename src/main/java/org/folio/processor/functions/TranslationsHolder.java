package org.folio.processor.functions;

import io.vertx.core.json.JsonObject;

public enum TranslationsHolder implements TranslationFunction {
    STUB() {
        @Override
        public String apply(String value, JsonObject parameters, Settings settings) {
            return value + "-STUB";
        }
    },
    SET_INDICATOR() {
        @Override
        public String apply(String value, JsonObject parameters, Settings settings) {
            return null;
        }
    };

    public static TranslationFunction lookup(String function) {
        return valueOf(function.toUpperCase());
    }
}
