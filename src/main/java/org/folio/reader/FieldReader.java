package org.folio.reader;

import org.folio.reader.field.FieldValue;

public interface FieldReader {

    FieldValue read(String path);
}
