package org.folio.reader;

import org.folio.processor.rule.Rule;
import org.folio.reader.field.FieldValue;

public interface FieldReader {

    FieldValue read(Rule rule);
}
