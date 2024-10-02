package ru.itmo.hasd.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
@Accessors(chain = true)
public class LightObject {

    private Long var1;
    private Integer var2;
    private Double var3;
    private Float var4;
    private String var5;
    private Character var6;
    private Boolean var7;
    private List<String> var8;
    private Set<Integer> var9;
    private Map<String, Long> var10;

}
