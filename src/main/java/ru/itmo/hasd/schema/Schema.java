package ru.itmo.hasd.schema;

import lombok.experimental.Accessors;

import java.util.List;

@Accessors(chain = true)
public record Schema(String name, List<Field> fields) {

    @Override
    public String toString() {
        var sb = new StringBuilder("class ")
                .append(name)
                .append(";\n");

        sb.append("fields [\n");
        for (var field : fields) {
            sb.append(field.toString())
                    .append("\n");
        }
        sb.append("];");
        return sb.toString();
    }

    /* Note: Схема сообщения
     *
     * class ???;
     * fields [
     *  field %s, type %s, value %s;
     *  ...
     * ];
     */

}
