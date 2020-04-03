package org.folio.processor.rule;

import io.vertx.core.json.JsonObject;
import org.folio.processor.translation.Translation;

public class Mapping {
    private String subField;
    private String indicator;
    private String from;
    private Translation translation;

    public Mapping(JsonObject dataSource) {
        if (dataSource.containsKey("subField")) {
            this.subField = dataSource.getString("subField");
        } else if (dataSource.containsKey("indicator")) {
            this.indicator = dataSource.getString("indicator");
        }
        this.from = dataSource.getString("from");
        if (dataSource.containsKey("translation")) {
            this.translation = new Translation(dataSource.getJsonObject("translation"));
        }
    }

    public String getSubField() {
        return subField;
    }

    public String getIndicator() {
        return indicator;
    }

    public String getFrom() {
        return from;
    }

    public Translation getTranslation() {
        return translation;
    }
}
