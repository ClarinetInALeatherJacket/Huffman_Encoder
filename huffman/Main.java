package huffman;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;


/**
 */

class Main{

    private static int getMode(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter 1 if you would like to compress a file, or 2 if you would like to decompress a file:");
        String mode = scanner.nextLine();
        if (Objects.equals(mode, "1")){
            return 1;
        } else if (Objects.equals(mode, "2")){
            return 2;
        } else {
            System.out.println("Please enter a valid input.");
            return getMode();
        }
    }

    private static String getFile(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the name of the file:");
        String filename = scanner.nextLine();
        try{
            File file = new File(filename);
            Scanner tester = new Scanner(file);
        } catch (FileNotFoundException e){
            System.out.println("Invalid file name, please try again.");
            getFile();
        }
        return filename;
    }
    public static void main(String[] args) throws IOException {
        int mode = getMode();
        if (mode == 1) {
            File input = new File(getFile());
            byte[] data = new byte[0];

            try {
                data = Files.readAllBytes(input.toPath());
            } catch (IOException ignored) {} //file path already tested in getFile method, so this exception can be ignored

            HashMap<Byte, Integer> freqDict = new HashMap<>();

            for(byte info: data){
                if (freqDict.containsKey(info)){
                    freqDict.put(info, freqDict.get(info)+1);
                } else {
                    freqDict.put(info, 1);
                }
            }

            byte[] keyset = new byte[freqDict.size()];
            int counter = 0;
            for (byte key: freqDict.keySet()){
                keyset[counter] = key;
                counter ++;
            }

            TreeBuilder treeBuilder = new TreeBuilder(freqDict);
            Compressor compressor = new Compressor(treeBuilder.getTree(), data, keyset);
            TreeCompressor treeCompressor = new TreeCompressor(treeBuilder.getTree());

            File output = new File(input.getName() + ".huf");
            int fileNum = 2;
            while (!output.createNewFile()){
                output = new File(input.getName() + ".huff(" + fileNum + ")");
                fileNum += 1;
            }
            Files.write(Path.of(output.getPath()), ByteBuffer.allocate(4).putInt(treeBuilder.getTree().size()).array());//treeBuilder.getTree().size()
            Files.write(Path.of(output.getPath()), treeCompressor.getCompressedTree());
            Files.write(Path.of(output.getPath()), ByteBuffer.allocate(4).putInt(data.length).array());//data.length
            Files.write(Path.of(output.getPath()), compressor.getCompressedData());


        } else {
            File input = new File(getFile());
            byte[] data = new byte[0];

            try {
                data = Files.readAllBytes(input.toPath());
            } catch (IOException ignored) {} //file path already tested in getFile method, so this exception can be ignored

            int num_nodes = ByteBuffer.wrap(Arrays.copyOfRange(data,0,4)).getInt();
            byte[] treeData = Arrays.copyOfRange(data, 5, num_nodes);
            int size = ByteBuffer.wrap(Arrays.copyOfRange(data, num_nodes+1,num_nodes+5)).getInt();
            byte[] fileData = Arrays.copyOfRange(data,num_nodes+6, data.length);

            TreeDecompressor treeDecompressor = new TreeDecompressor();
            Decompressor decompressor = new Decompressor();

        }
    }
}