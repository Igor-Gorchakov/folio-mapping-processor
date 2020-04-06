package org.folio.reader;

import org.folio.processor.rule.Rule;
import org.folio.reader.values.FieldValue;

public interface FieldReader {

    FieldValue read(Rule rule);
}
