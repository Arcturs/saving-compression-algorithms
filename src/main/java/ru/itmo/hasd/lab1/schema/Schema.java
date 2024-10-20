package ru.itmo.hasd.lab1.schema;

import lombok.experimental.Accessors;

import java.util.List;

@Accessors(chain = true)
public record Schema(String name, List<Field> fields) {

    @Override
    public String toString() {
        var sb = new StringBuilder("class ")
                .append(name)
                .append(";\n");

        for (var field : fields) {
            sb.append(field.toString()).append("\n");
        }

        return sb.toString();
    }

    /* Note: Схема сообщения
     *
     * class ???;
     * field %s, type %s, value %s;
     * field %s, type %s, value-type %s, values [%s || %s || ...]; - collection
     * field %s, type %s, key-type %s, value-type %s, values [%s--%s || %s--%s || ...]; - map
     * ...
     */

}
