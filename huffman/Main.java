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
        System.out.println(Arrays.toString(new File("./huffman").listFiles()));
        System.out.println("Please enter the name of the file:");
        String filename = scanner.nextLine();
        try{
            File file = new File(filename);
            Scanner tester = new Scanner(file);
        } catch (FileNotFoundException e){
            System.out.println("Invalid file name, please try again.");
            return getFile();
        }
        return filename;
    }
    public static void main(String[] args) throws IOException {
        int mode = getMode();
        File input = new File(getFile());
        byte[] data = Files.readAllBytes(input.toPath());
        if (mode == 1) {

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
                output = new File(input.getName() + ".huf(" + fileNum + ")");
                fileNum += 1;
            }
            ArrayList<Byte> fileWriter = new ArrayList<>();
            byte[] temp1 = ByteBuffer.allocate(4).putInt(treeBuilder.getTree().getNumber()+1).array();
            for (Byte info: temp1){
                fileWriter.add(info);
            }
            for (Byte info: treeCompressor.getCompressedTree()){
                fileWriter.add(info);
            }
            byte[] temp2 = ByteBuffer.allocate(4).putInt(data.length).array();
            for (Byte info: temp2){
                fileWriter.add(info);
            }
            for (Byte info: compressor.getCompressedData()){
                fileWriter.add(info);
            }

            byte[] finalFileWriter = new byte[fileWriter.size()];

            for (int i = 0; i < fileWriter.size(); i++){
                finalFileWriter[i] = fileWriter.get(i);
            }
            
            Files.write(Path.of(output.getPath()), finalFileWriter);

        } else {

            int num_nodes = ByteBuffer.wrap(Arrays.copyOfRange(data,0,4)).getInt();
            byte[] treeData = Arrays.copyOfRange(data, 4, num_nodes*4 +4);
            int size = ByteBuffer.wrap(Arrays.copyOfRange(data, num_nodes*4 +4,num_nodes*4 +8)).getInt();
            byte[] fileData = Arrays.copyOfRange(data,num_nodes*4 +8, data.length);

            TreeDecompressor treeDecompressor = new TreeDecompressor(treeData, num_nodes);
            Decompressor decompressor = new Decompressor(fileData, treeDecompressor.getTree(), size);

            File output = new File(input.getName() + ".orig");
            int fileNum = 2;
            while (!output.createNewFile()){
                output = new File(input.getName() + ".orig(" + fileNum + ")");
                fileNum += 1;
            }

            Files.write(Path.of(output.getPath()), decompressor.getDecompressedData());

        }
    }
}