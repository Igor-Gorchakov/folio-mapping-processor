package org.folio.reader;

import org.folio.value.Value;

public interface Reader {

    Value readFieldValueByPath(String path);
}
