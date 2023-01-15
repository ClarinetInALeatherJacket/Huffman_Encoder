package huffman;

import java.util.Objects;

public class TreeNode {


    private TreeNode left;
    private TreeNode right;
    private int number;
    private int symbol;

    public TreeNode(int symbol){
        this.symbol = symbol;
    }

    public TreeNode(TreeNode left, TreeNode right){
        this.right = right;
        this.left = left;
    }

    public boolean isLeaf(){
        return (!(this.left == null) && !(this.right == null) && (int.class.isInstance(this.symbol)));
    }

    public int getNumber(){ return number;}

    public int getSymbol(){
        return symbol;
    }

    public TreeNode getLeft() {
        return left;
    }

    public TreeNode getRight() {
        return right;
    }

    public void setLeft(TreeNode left) {
        this.left = left;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setRight(TreeNode right) {
        this.right = right;
    }

    public void setSymbol(int symbol) {
        this.symbol = symbol;
    }

    public boolean contains(int goal){
        if (this.symbol == goal){ return true; }
        if (this.isLeaf()){ return false; }
        return this.left.contains(goal) || this.right.contains(goal);
    }

    public int size(){
        if (this.isLeaf()){
            return 1;
        } else {
            return 1 + this.left.size() + this.right.size();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TreeNode treeNode)) return false;
        return number == treeNode.number && symbol == treeNode.symbol && Objects.equals(left, treeNode.left) && Objects.equals(right, treeNode.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right, number, symbol);
    }

    @Override
    public String toString(){
        if (this.left != null){
            return "TreeNode(%d, %s, %s)".formatted(this.symbol, this.left.toString(), this.right.toString());
        } else {
            return "TreeNode(%d)".formatted(this.symbol);
        }
    }
}
