package me.bytebeats.algo.designs;

import java.util.ArrayList;
import java.util.List;

public class Codec {

    // Encodes a list of strings to a single string.
    public String encode(List<String> strs) {
        char splitter = (char) 258;
        char emptyPH = (char) 259;
        StringBuilder sb = new StringBuilder();
        if (strs != null && !strs.isEmpty()) {
            for (int i = 0; i < strs.size(); i++) {
                if (i != 0) {
                    sb.append(splitter);
                }
                String e = strs.get(i);
                if ("".equals(e)) {
                    sb.append(emptyPH);
                } else {
                    sb.append(e);
                }
            }
        } else {
            return null;
        }
        return sb.toString();
    }

    // Decodes a single string to a list of strings.
    public List<String> decode(String s) {
        String splitter = Character.toString((char) 258);
        String emptyPH = Character.toString((char) 259);
        List<String> res = new ArrayList<>();
        if (s == null || "".equals(s)) {

        } else {
            if (s.contains(splitter)) {
                for (String e : s.split(splitter)) {
                    if (e.contains(emptyPH)) {
                        e = e.replaceAll(emptyPH, "");
                    }
                    res.add(e);
                }
            } else {
                res.add(s);
            }
        }
        return res;
    }
}