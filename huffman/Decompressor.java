package huffman;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Decompressor {


    private byte[] decompressedData;

    public Decompressor(byte[] data, TreeNode huffmanTree, int size){
        ArrayList<Byte> intSymbols = huffmanTree.getSymbols();
        byte[] symbols = new byte[intSymbols.size()];
        for (int counter = 0; counter < intSymbols.size(); counter ++){
            symbols[counter] = intSymbols.get(counter);
        }
        HashMap<String, Byte> codes = getCodes(huffmanTree, symbols);
        StringBuilder bitlist = new StringBuilder();
        StringBuilder testBitList = new StringBuilder();
        for (Byte x:data){
            testBitList.append(byteToString(x));
        }
        int minCodeLength = codes.keySet().stream().mapToInt(String::length).min().orElse(1);
        int maxCodeLength = codes.keySet().stream().mapToInt(String::length).max().orElse(10000);
        ArrayList<Byte> decompressedDataTemp = new ArrayList<>();

        for (Byte info: data){
            for (char i: byteToString(info).toCharArray()) {
                bitlist.append(i);
                if (bitlist.toString().length() >= minCodeLength && codes.containsKey(bitlist.toString())) {
                    decompressedDataTemp.add(codes.get(bitlist.toString()));
                    bitlist.delete(0, bitlist.toString().length());
                } else if (bitlist.toString().length() > maxCodeLength) {
                    System.out.println("Failure: couldn't find code in tree");
                }
            }
        }

        decompressedData = new byte[decompressedDataTemp.size()];
        for (int counter = 0; counter < decompressedDataTemp.size(); counter++){
            decompressedData[counter] = decompressedDataTemp.get(counter);
        }

        decompressedData = Arrays.copyOfRange(decompressedData, 0, size);
    }



    private HashMap<String, Byte> getCodes(TreeNode huffmanTree, byte[] keyset){
        HashMap<String, Byte> out = new HashMap<>();
        for (Byte key: keyset){
            out.put(this.generateCode(huffmanTree, key), key);
        }
        return out;
    }

    private String generateCode(TreeNode huffmanTree, byte symbol){
        StringBuilder out = new StringBuilder();
        while (!huffmanTree.isLeaf()){
            if (huffmanTree.getLeft().contains(symbol)){
                out.append("0");
                huffmanTree = huffmanTree.getLeft();
            } else {
                out.append("1");
                huffmanTree = huffmanTree.getRight();
            }
        }
        return out.toString();
    }

    private String byteToString(byte b) {
        byte[] masks = { -128, 64, 32, 16, 8, 4, 2, 1 };
        StringBuilder builder = new StringBuilder();
        for (byte m : masks) {
            if ((b & m) == m) {
                builder.append('1');
            } else {
                builder.append('0');
            }
        }
        return builder.toString();
    }

    public byte[] getDecompressedData() {
        return decompressedData;
    }
}
