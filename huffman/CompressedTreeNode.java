package huffman;

public class CompressedTreeNode {

    private byte lType;
    private byte rType;
    private byte lData;
    private byte rData;

    public CompressedTreeNode(byte lType, byte lData, byte rType, byte rData){
        this.lData = lData;
        this.lType = lType;
        this.rData = rData;
        this.rType = rType;
    }

    public byte getlData() {
        return lData;
    }

    public byte getlType() {
        return lType;
    }

    public byte getrData() {
        return rData;
    }

    public byte getrType() {
        return rType;
    }

}
