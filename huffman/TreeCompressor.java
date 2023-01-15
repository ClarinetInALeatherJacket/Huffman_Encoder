package huffman;

import java.util.ArrayList;

public class TreeCompressor {

    byte[] compressedTree;

    public TreeCompressor(TreeNode huffmanTree){
        this.numberTreeNodes(huffmanTree, 0);
        ArrayList<Integer> compressedTreeNumbers = compressTree(huffmanTree);
        this.compressedTree = new byte[compressedTreeNumbers.size()];

        for (int counter = 0; counter < this.compressedTree.length; counter++){
            this.compressedTree[counter] = (byte) compressedTreeNumbers.get(counter).intValue();
        }
    }

    public byte[] getCompressedTree() {
        return compressedTree;
    }

    /**
     * Recursively assigns numbers to each node in the given tree via postorder traversal
     * @param tree The tree to be numbered
     * @param num The number from which the system should start, typically 0
     * @return the next number to be used in the recursive numbering process
     */
    private int numberTreeNodes(TreeNode tree, int num){
        if (!tree.getLeft().isLeaf()) num = numberTreeNodes(tree.getLeft(), num);
        if (!tree.getRight().isLeaf()) num = numberTreeNodes(tree.getRight(), num);
        tree.setNumber(num);
        return (num+1);
    }

    private ArrayList<Integer> compressTree(TreeNode tree){
        ArrayList<Integer> out = new ArrayList<>();
        if (!tree.getLeft().isLeaf()) out.addAll(compressTree(tree.getLeft()));
        if (!tree.getRight().isLeaf()) out.addAll(compressTree(tree.getRight()));

        int leftType = tree.getLeft().isLeaf() ? 0 : 1;
        int rightType = tree.getRight().isLeaf() ? 0 : 1;
        out.add(leftType);
        out.add(leftType == 0 ? tree.getLeft().getSymbol() : tree.getLeft().getNumber());
        out.add(rightType);
        out.add(rightType == 0 ? tree.getRight().getSymbol() : tree.getRight().getNumber());

        return out;
    }
}
