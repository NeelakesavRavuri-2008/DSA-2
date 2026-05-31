package case-study-codes;
import java.util.ArrayList;
import java.util.List;

class BTreeNode {
    boolean leaf;
    List<Integer> keys;
    List<BTreeNode> child;

    BTreeNode(boolean leaf) {
        this.leaf = leaf;
        this.keys = new ArrayList<>();
        this.child = new ArrayList<>();
    }
}

class BTree {

    boolean search(BTreeNode node, int key) {
        int i = 0;

        while (i < node.keys.size() && key > node.keys.get(i)) {
            i++;
        }

        if (i < node.keys.size() && key == node.keys.get(i)) {
            return true;
        }

        if (node.leaf) {
            return false;
        }

        return search(node.child.get(i), key);
    }
}

