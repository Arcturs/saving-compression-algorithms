package ru.itmo.hasd.lab5;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static ru.itmo.hasd.lab5.SearchStatus.LEAF_FOUND;
import static ru.itmo.hasd.lab5.SearchStatus.NOT_FOUND;
import static ru.itmo.hasd.lab5.SearchStatus.PART_CONTAINS;

@Getter
public class LoudsTree {

    private final List<Boolean> LBS;
    private final List<Character> labels;
    private final List<Boolean> isLeaf;

    public LoudsTree() {
        LBS = new ArrayList<>();
        LBS.add(true);
        LBS.add(false);

        labels = new ArrayList<>();
        labels.add(null);
        labels.add(null);

        isLeaf = new ArrayList<>();
        isLeaf.add(false);
        isLeaf.add(false);
    }

    public SearchStatus match(String s) {
        return search(2, s.toCharArray(), 0);
    }

    private SearchStatus search(int index, char[] chars, int wordOffset) {
        var charIndex = rank1(index);
        while(LBS.get(index)) {
            if (chars[wordOffset] == labels.get(charIndex)) {
                if (isLeaf.get(index) && wordOffset + 1 == chars.length) {
                    return LEAF_FOUND;
                }
                if (wordOffset + 1 == chars.length) {
                    return PART_CONTAINS;
                }
                return search(select0(charIndex), chars, ++wordOffset);
            }
            index++;
            charIndex++;
        }
        return NOT_FOUND;
    }

    private int rank1(int to) {
        return (int) LBS.subList(0, to + 1)
                .stream()
                .filter(elm -> elm)
                .count();
    }

    private int select0(int label) {
        var count = 0;
        var i = 0;
        for (var lb : LBS) {
            i++;
            if (!lb) {
                if (++count == label) {
                    break;
                }
            }
        }
        return i + 1;
    }

}
