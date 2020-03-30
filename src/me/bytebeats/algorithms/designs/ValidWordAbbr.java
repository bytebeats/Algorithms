package me.bytebeats.algorithms.designs;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ValidWordAbbr {
    private Map<String, Integer> abbrSet = new HashMap<>();

    public ValidWordAbbr(String[] dictionary) {
        if (dictionary == null || dictionary.length == 0) {
            return;
        }
        String abbr = null;
        for (String word : dictionary) {
            if (word == null) {
                continue;
            }
            abbr = abbr(word);
            if (abbr == null) {
                continue;
            }
            if (abbrSet.containsKey(abbr)) {
                abbrSet.put(abbr, abbrSet.get(abbr) + 1);
            } else {
                abbrSet.put(abbr, 1);
            }
        }
    }

    public boolean isUnique(String word) {
        if (word == null) {
            return false;
        }
        String abbr = abbr(word);
        if (abbrSet.containsKey(abbr)) {
            return false;
        } else {
            return true;
        }
    }

    private String abbr(String word) {
        if (word == null) {
            return null;
        }
        if (word.length() < 3) {
            return word;
        }
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (int i = 0; i < word.length(); i++) {
            if (i == 0) {
                sb.append(word.charAt(i));
            } else if (i == word.length() - 1) {
                sb.append(count);
                sb.append(word.charAt(i));
            } else {
                count++;
            }
        }
        return sb.toString();
    }
}
