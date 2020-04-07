package org.folio.processor.translations;

import io.vertx.core.json.JsonObject;
import org.folio.processor.rule.Translation;

public enum TranslationsHolder implements TranslationFunction {
    STUB() {
        @Override
        public String apply(String value, JsonObject parameters, Settings settings) {
            return value + "-STUB";
        }
    },
    SET_ALTERNATIVE_TITLE_TYPE() {
        @Override
        public String apply(String value, JsonObject parameters, Settings settings) {
            return value;
        }
    },
    SET_ELECTRONIC_ACCESS_INDICATOR() {
        @Override
        public String apply(String value, JsonObject parameters, Settings settings) {
            return "0";
        }
    };

    public static TranslationFunction lookup(Translation translation) {
        return valueOf(translation.getFunction().toUpperCase());
    }
}
