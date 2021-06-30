package me.bytebeats.algo.trees;

import me.bytebeats.algs.ds.TreeNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author bytebeats
 * @Email <happychinapc@gmail.com>
 * @Github https://github.com/bytebeats
 * @Created on 2021/6/30 16:50
 * @Version 1.0
 * @Description TO-DO
 */

public class BinaryTreeCodec {//剑指 Offer 37

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        if (root == null) {
            return "null:";
        }
        return root.val + ":" + serialize(root.left) + serialize(root.right);
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        String[] elements = data.split(":");
        List<String> list = new ArrayList();
        list.addAll(Arrays.asList(elements));
        return buildTree(list);
    }

    private TreeNode buildTree(List<String> elements) {
        if (elements == null || elements.isEmpty()) {
            return null;
        }
        String val = elements.remove(0);
        if ("null".equals(val)) {
            return null;
        }
        TreeNode root = new TreeNode(Integer.parseInt(val));
        root.left = buildTree(elements);
        root.right = buildTree(elements);
        return root;
    }
}
