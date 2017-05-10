package com.example.mamton.testapp;

import android.util.Pair;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void addition_isCorrect() throws Exception {
        String[] words = new String[]{"q", "as", "am"};

        Comparator<Pair<Character, Integer>> comparator
                = new Comparator<Pair<Character, Integer>>() {
            @Override
            public int compare(final Pair<Character, Integer> o1,
                    final Pair<Character, Integer> o2) {
                if (o1.first == o2.first) {
                    return 0;
                } else {
                    return (o1.second == o2.second)
                            ? -1
                            : o2.second - o1.second;
                }
            }
        };
        TreeMap<Pair<Character, Integer>, Set<Character>> chars = new TreeMap<>(comparator);
        //HashMap<Character, Set<Character>> links

        for (int i = 0; i < words.length - 1; i++) {
            String first = words[i];
            String second = words[i + 1];
            boolean hasChar = false;
            for (int j = 0; j < first.length() && !hasChar; j++) {
                if (second.length() < j + 1) {
                    break;
                }
                char firstChar = first.charAt(j);
                char secondChar = second.charAt(j);
                if (firstChar != secondChar) {
                    Character keyChar = new Character(firstChar);
                    Set<Character> characters = chars.get(keyChar);
                    int count = 1;
                    if (characters == null) {
                        characters = new HashSet<>();
                    } else {
                        //todo тут
                        //count = chars.ceilingEntry().getKey().second + 1;
                    }
                    chars.put(new Pair<Character, Integer>(keyChar, count), characters);
                    //todo тут организовать мультимэп
                    //links.put(new Character(secondChar), new HashSet<Character>());
                    characters.add(secondChar);
                    hasChar = true;
                }
            }
        }
        Set<Pair<Character, Integer>> keys = chars.keySet();
        for (Pair<Character, Integer> key : keys) {
            chars.get(key);
            //todo тут обрываем его связи и перекладывем буквы котрые на него ссылаются
            //links.get(key.first);

        }


    }

    //
    class Node {
        private char symbol;
        private List<Node> nextNodes = new ArrayList<>();

    }
}