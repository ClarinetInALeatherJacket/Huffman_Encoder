package huffman;

import org.javatuples.Pair;

import java.util.Comparator;

public class LeafListComparator implements Comparator<Pair<TreeNode, Integer>>{


    @Override
    public int compare(Pair<TreeNode, Integer> o1, Pair<TreeNode, Integer> o2) {
        return o1.getValue1().compareTo(o2.getValue1());
    }

    @Override
    public Comparator reversed() {
        return Comparator.super.reversed();
    }

}
