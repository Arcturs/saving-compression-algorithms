package ru.itmo.hasd.lab1.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
@Accessors(chain = true)
public class LightObject2 {

    private Long var1;
    private Long var2;

    private Integer var3;
    private Integer var4;

    private Double var5;
    private Double var6;

    private Float var7;
    private Float var8;

    private String var9;
    private String var10;

    private Character var11;
    private Character var12;

    private Boolean var13;
    private Boolean var14;

    private List<String> var15;
    private List<String> var16;

    private Set<Integer> var17;
    private Set<Integer> var18;

    private Map<String, Long> var19;
    private Map<String, Long> var20;

}