package huffman;

import org.javatuples.Pair;

import java.util.ArrayList;

public class TreeDecompressor {

    private final TreeNode tree;

    public TreeDecompressor(byte[] treeData, int numNodes) {
        ArrayList<CompressedTreeNode> nodeList = new ArrayList<>();
        for (int i = 0; i < treeData.length/4; i++ ){
            CompressedTreeNode node = new CompressedTreeNode(treeData[i*4], treeData[(i*4)+1], treeData[(i*4)+2], treeData[(i*4)+3]);
            nodeList.add(node);
        }

        this.tree = decompressTree(nodeList, numNodes-1).getValue0();
    }

    private Pair<TreeNode, Integer> decompressTree(ArrayList<CompressedTreeNode> nodeList, int rootIndex){
        TreeNode tempTree;
        if (nodeList.get(rootIndex).getlType() == nodeList.get(rootIndex).getrType() && nodeList.get(rootIndex).getlType() == 0){
            tempTree = new TreeNode(new TreeNode(nodeList.get(rootIndex).getlData()), new TreeNode(nodeList.get(rootIndex).getrData()));
            tempTree.setNumber(rootIndex);
        } else if (nodeList.get(rootIndex).getlType() == nodeList.get(rootIndex).getrType() && nodeList.get(rootIndex).getlType() == 1) {
            tempTree = new TreeNode("null");
            tempTree.setNumber(rootIndex);
        } else {
            if (nodeList.get(rootIndex).getlType() == 0){
                tempTree = new TreeNode(new TreeNode(nodeList.get(rootIndex).getlType()),'l');
                tempTree.setNumber(rootIndex);
            } else {
                tempTree = new TreeNode(new TreeNode(nodeList.get(rootIndex).getrType()),'r');
                tempTree.setNumber(rootIndex);
            }
        }

        if (tempTree.right == null){
            Pair<TreeNode, Integer> nextRecursion = decompressTree(nodeList, rootIndex-1);
            tempTree.setRight(nextRecursion.getValue0());
            rootIndex = nextRecursion.getValue1();
        }

        if (tempTree.left == null){
            Pair<TreeNode, Integer> nextRecursion = decompressTree(nodeList, rootIndex-1);
            tempTree.setLeft(nextRecursion.getValue0());
            rootIndex = nextRecursion.getValue1();
        }

        return new Pair<TreeNode, Integer>(tempTree, rootIndex);

    }

    public TreeNode getTree() {
        return tree;
    }
}
