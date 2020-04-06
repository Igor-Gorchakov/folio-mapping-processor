package org.folio.reader;

public class DotSeparatedSyntaxReader  {
//    private JsonObject entity;
//
//    public DotSeparatedSyntaxReader(JsonObject entity) {
//        this.entity = entity;
//    }
//
//
//    public FieldValue readSimpleField(String path) {
//        String[] pathItems = path.split("\\.");
//        return findByPathRecursively(pathItems, 0, entity);
//    }
//
//    private FieldValue findByPathRecursively(String[] pathItems, int index, Object parentNode) {
//        if (parentNode instanceof String) {
//            return StringField.of((String) parentNode);
//        } else if (parentNode instanceof JsonObject) {
//            Object childNode = ((JsonObject) parentNode).getValue(pathItems[index]);
//            return findByPathRecursively(pathItems, ++index, childNode);
//        } else if (parentNode instanceof JsonArray) {
//            List<StringField> stringFields = new ArrayList<>();
//            JsonArray array = (JsonArray) parentNode;
//            for (Object arrayItem : array) {
//                stringFields.add((StringField) findByPathRecursively(pathItems, index, arrayItem));
//            }
//            List<String> stringList = new ArrayList<>();
//            stringFields.forEach(value -> stringList.add(value.getValue()));
//            return ListStringField.of(stringList);
//        } else {
//            return MissingValue.getInstance();
//        }
//    }
//
//
//    protected FieldValue readRepeatableField(List<Rule> rules) {
//        return null;
//    }
//
//    protected FieldValue readSimplifiedField(Rule rule) {
//        return null;
//    }
}
