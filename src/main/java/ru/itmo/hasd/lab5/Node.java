package ru.itmo.hasd.lab5;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class Node {

    private final char key;
    private final Map<Character, Node> childNodes = new HashMap<>();

    @Setter
    private boolean isLeaf;

    public Node(char k) {
        key = k;
    }

    public void add(Node childNode) {
        childNodes.put(childNode.getKey(), childNode);
    }

    public boolean hasChild() {
        return !childNodes.isEmpty();
    }

    public boolean keyExists(char c) {
        return childNodes.containsKey(c);
    }

    public Node getChildNode(char c) {
        return childNodes.get(c);
    }

}
