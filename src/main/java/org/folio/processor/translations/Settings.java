package org.folio.processor.translations;

import io.vertx.core.json.JsonArray;
import org.apache.commons.collections4.list.UnmodifiableList;

import java.util.List;

public class Settings {
    private UnmodifiableList<JsonArray> instanceTypes;
    private UnmodifiableList<JsonArray> contributors;
    private UnmodifiableList<JsonArray> identifiers;
    private UnmodifiableList<JsonArray> classifications;

    public List<JsonArray> getInstanceTypes() {
        return instanceTypes;
    }

    public List<JsonArray> getContributors() {
        return contributors;
    }

    public List<JsonArray> getIdentifiers() {
        return identifiers;
    }

    public List<JsonArray> getClassifications() {
        return classifications;
    }
}
