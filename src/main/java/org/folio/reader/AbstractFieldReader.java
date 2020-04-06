package org.folio.reader;

import org.folio.processor.rule.Condition;
import org.folio.processor.rule.Rule;
import org.folio.reader.values.FieldValue;
import org.folio.reader.values.MissingValue;

public abstract class AbstractFieldReader implements FieldReader {

    @Override
    public FieldValue read(Rule rule) {
        if (isSimpleFieldRule(rule)) {
            return readSimpleField(rule.getConditions().get(0));
        } else if (isRepeatableFieldRule(rule)) {
            return readRepeatableField(rule);
        }
        return MissingValue.getInstance();
    }

    private boolean isSimpleFieldRule(Rule rule) {
        return rule.getConditions().size() == 1;
    }

    private boolean isRepeatableFieldRule(Rule rule) {
        return rule.getConditions().size() > 1;
    }

    protected abstract FieldValue readRepeatableField(Rule rule);

    protected abstract FieldValue readSimpleField(Condition condition);
}
