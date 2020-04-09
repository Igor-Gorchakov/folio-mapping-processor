package org.folio.reader;

import org.folio.processor.rule.Rule;
import org.folio.reader.values.RuleValue;

public interface EntityReader {

    RuleValue read(Rule rule);
}
