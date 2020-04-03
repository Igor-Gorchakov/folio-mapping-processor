package org.folio.reader;

import org.folio.processor.rule.Rule;
import org.folio.reader.field.FieldValue;
import org.folio.reader.field.MissingField;

public abstract class AbstractFieldReader implements FieldReader {

    @Override
    public FieldValue read(Rule rule) {
        if (rule.isSimpleFieldRule()) {
            return readSimplifiedField(rule.getMapping().get(0));
        } else if (rule.isRepeatableFieldRule()) {
            return readRepeatableField(rule);
        }
        return MissingField.getInstance();
    }

    // returns only ListObjectField
    protected abstract FieldValue readRepeatableField(Rule rule);

    // returns only StringField || ListStringField
    protected abstract FieldValue readSimplifiedField(Rule rule);
}
