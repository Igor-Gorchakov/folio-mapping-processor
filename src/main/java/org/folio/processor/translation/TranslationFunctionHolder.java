package org.folio.processor.translation;

import io.vertx.core.json.JsonObject;
import org.folio.writer.RecordField;

public enum TranslationFunctionHolder implements TranslationFunction {
    TRIM_WHITESPACES() {
        @Override
        public void apply(RecordField recordField, JsonObject params, Settings settings) {
            String translated = recordField.getData().trim();
            recordField.setData(translated);
        }
    },

    GET_CLASSIFICATION_NUMBER() {
        @Override
        public void apply(RecordField recordField, JsonObject params, Settings settings) {
            // use settings
        }
    }
}
