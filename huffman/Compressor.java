package huffman;

import java.util.ArrayList;
import java.util.HashMap;

public class Compressor {

    private final byte[] compressedData;

    public Compressor(TreeNode huffmanTree, byte[] data, byte[] keyset){
        HashMap<Byte, String> compressionCodes = getCodes(huffmanTree, keyset);
        StringBuilder bitString = new StringBuilder();
        ArrayList<Byte> convertedBytes = new ArrayList<>();
        int endpoint = 0;

        for (byte info: data){
            bitString.append(compressionCodes.get(info));
        }

        if (bitString.length() > 8){
            int quotient = bitString.toString().length() / 8;
            for (int counter = 0; counter < quotient; counter++){

                if (bitString.charAt(8*endpoint) == '1'){
                    convertedBytes.add((byte) (Integer.parseInt(bitString.substring(8*endpoint, 8*(endpoint+1)),2)-256));
                } else {
                    convertedBytes.add((byte) (Integer.parseInt(bitString.substring(8*endpoint, 8*(endpoint+1)),2)));
                }
                endpoint = counter+1;
            }
        }

        int bitStringLength = bitString.length();
        int remainder = bitStringLength % 8;
        while (remainder < 8){
            bitString.append("0");
            remainder ++;
        }

        if (bitString.charAt(8*endpoint) == '1'){
            convertedBytes.add((byte) (Integer.parseInt(bitString.substring(8*endpoint),2)-256));
        } else {
            convertedBytes.add((byte) (Integer.parseInt(bitString.substring(8*endpoint),2)));
        }


        byte[] out = new byte[convertedBytes.size()];
        for (int position = 0; position < out.length; position++){
            out[position] = convertedBytes.get(position);
        }

        this.compressedData = out;

    }

    /**
     * Private helper function which returns a hashmap, where the key is the original byte, and the value is the String of bits representing that byte
     * @param huffmanTree The root TreeNode of the tree that the hashmap of codes should be based on
     * @param keyset A byte[] of all bytes contained in the tree to be compressed
     * @return A Hashmap<Byte, String> where the key is the input byte and the value is the String of bits that represent it in compressed data
     */
    private HashMap<Byte, String> getCodes(TreeNode huffmanTree, byte[] keyset){
        HashMap<Byte, String> out = new HashMap<>();
        for (Byte key: keyset){
            out.put(key, this.generateCode(huffmanTree, key));
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


    public byte[] getCompressedData() {
        return compressedData;
    }
}
