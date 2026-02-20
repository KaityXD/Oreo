package net.amar.oreojava.db.tables;

import net.amar.oreojava.db.annotations.NonNull;
import net.amar.oreojava.db.annotations.StringField;
import net.amar.oreojava.db.annotations.Table;

@Table(value = "data")
public class Data {

    @StringField
    @NonNull
    String fieldName;

    @StringField
    @NonNull
    String fieldValue;

    public Data(String fieldName, String fieldValue) {
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public String getFieldName() {
        return fieldName;
    }
    public String getFieldValue() {
        return fieldValue;
    }
}
