package huffman;

import org.javatuples.*;
import java.util.*;


public class TreeBuilder {

    private TreeNode tree;
    public TreeBuilder(HashMap<Byte, Integer> freqDict){
        this.tree = build(freqDict);
    }

    private TreeNode build(HashMap<Byte, Integer> freqDict){
        ArrayList<Byte> symbols = new ArrayList<>(freqDict.keySet());
        ArrayList<Pair<TreeNode, Integer>> leafList = new ArrayList<>();
        for (Byte symbol: symbols){
            leafList.add(new Pair<>(new TreeNode(symbol), freqDict.get(symbol)));
        }

        while (leafList.size() > 1){
            leafList.sort(new LeafListComparator());

            Pair<TreeNode, Integer> lowestFreq = leafList.get(0);
            Pair<TreeNode, Integer> secondLowestFreq = leafList.get(1);

            TreeNode tree = lowestFreq.getValue1() > secondLowestFreq.getValue1() ?
                    new TreeNode(secondLowestFreq.getValue0(), lowestFreq.getValue0()) :
                    new TreeNode(lowestFreq.getValue0(), secondLowestFreq.getValue0());

            leafList.add(new Pair<>(tree, lowestFreq.getValue1()+secondLowestFreq.getValue1()));
            leafList.remove(lowestFreq);
            leafList.remove(secondLowestFreq);
        }

        TreeNode output = leafList.get(0).getValue0();
        if (output.isLeaf()){
            output = new TreeNode(output, new TreeNode(output.getSymbol()+1));
        }
        return output;
    }

    public TreeNode getTree() {
        return tree;
    }
}
