package huffman;

import java.util.ArrayList;
import java.util.Objects;

public class TreeNode {


    public TreeNode left;
    public TreeNode right;
    private int number;
    private byte symbol;

    public TreeNode(byte symbol){
        this.symbol = symbol;
    }

    public TreeNode(TreeNode left, TreeNode right){
        this.right = right;
        this.left = left;
    }

    public TreeNode(TreeNode tree, char side){
        if (side == 'r'){
            this.right = tree;
        } else {
            this.left = tree;
        }
    }

    public TreeNode (String in){}

    public boolean isLeaf(){
        return ((this.left == null) && (this.right == null));
    }

    public int getNumber(){ return number;}

    public byte getSymbol(){
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

    public void setSymbol(byte symbol) {
        this.symbol = symbol;
    }

    public boolean contains(byte goal){
        if (this.symbol == goal) return true;
        if (this.isLeaf()) return false;
        return this.left.contains(goal) || this.right.contains(goal);
    }

    public int size(){
        if (this.isLeaf()){
            return 1;
        } else {
            return 1 + this.left.size() + this.right.size();
        }
    }

    public ArrayList<Byte> getSymbols(){
        ArrayList<Byte> out = new ArrayList<Byte>();
        if (this.isLeaf()){
            out.add(this.symbol);
            return out;
        }
        out = this.left.getSymbols();
        out.addAll(this.right.getSymbols());
        return out;
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
