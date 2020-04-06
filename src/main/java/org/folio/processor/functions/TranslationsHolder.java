package org.folio.processor.functions;

import io.vertx.core.json.JsonObject;

public enum TranslationsHolder implements TranslationFunction {
    TRIM_WHITESPACES() {
        @Override
        public String apply(String value, JsonObject parameters, Settings settings) {
            return value.trim();
        }
    }
}
