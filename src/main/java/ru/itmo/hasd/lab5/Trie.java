package ru.itmo.hasd.lab5;

import java.util.ArrayDeque;
import java.util.Queue;

public class Trie {

    private final Node rootNode;

    public Trie() {
        rootNode = new Node('_');
    }

    public void add(String s) {
        add(s.toCharArray(), 0, rootNode);
    }

    private static void add(char[] chars, int offset, Node node) {
        var key = chars[offset];
        Node childNode;
        if (node.keyExists(key)) {
            childNode = node.getChildNode(key);
        } else {
            childNode = new Node(key);
            node.add(childNode);
        }

        if (chars.length == offset + 1) {
            childNode.setLeaf(true);
            return;
        }
        add(chars, ++offset, childNode);
    }

    public LoudsTree convert() {
        var loudsTree = new LoudsTree();
        Queue<Node> queue = new ArrayDeque<>();
        queue.add(rootNode);
        while(!queue.isEmpty()) {
            processQueue(queue, loudsTree);
        }
        return loudsTree;
    }

    private static void processQueue(Queue<Node> queue, LoudsTree louds) {
        var node = queue.poll();
        if (node.hasChild()) {
            for(var characterNodeMap : node.getChildNodes().entrySet()) {
                queue.add(characterNodeMap.getValue());
                louds.getLBS().add(true);
                louds.getLabels().add(characterNodeMap.getKey());
                louds.getIsLeaf().add(characterNodeMap.getValue().isLeaf());
            }
        }
        louds.getLBS().add(false);
        louds.getIsLeaf().add(false);
    }

}
