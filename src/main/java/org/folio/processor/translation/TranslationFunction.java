package org.folio.processor.translation;

import io.vertx.core.json.JsonObject;
import org.folio.writer.RecordField;

@FunctionalInterface
public interface TranslationFunction {
    void apply(RecordField recordField, JsonObject parameters, Settings settings);
}
