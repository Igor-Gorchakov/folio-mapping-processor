package org.folio.processor.translation;

import io.vertx.core.json.JsonObject;
import org.folio.writer.RecordField;

public enum TranslationsHolder implements TranslationFunction {
    TRIM_WHITESPACES() {
        @Override
        public void apply(RecordField recordField, JsonObject parameters, Settings settings) {
            String translated = recordField.getData().trim();
            recordField.setData(translated);
        }
    },
    SET_ALTERNATIVE_TITLE_TYPE() {
        @Override
        public void apply(RecordField recordField, JsonObject parameters, Settings settings) {
            // use settings to find item by the given id from the recordField.data
            recordField.setData("Former title");
        }
    }

}
