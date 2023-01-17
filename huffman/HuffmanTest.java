package huffman;
import org.junit.jupiter.api.*;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class HuffmanTest {


    @Test
    void treeBuilderTest(){
        HashMap<Byte, Integer> freqDict = new HashMap<>();
        freqDict.put((byte) 64, 4);
        freqDict.put((byte) 65, 3);
        freqDict.put((byte) 66, 6);
        freqDict.put((byte) 67, 1);
        freqDict.put((byte) 68, 3);
        TreeBuilder treeBuilder = new TreeBuilder(freqDict);
        TreeNode out = treeBuilder.getTree();
        System.out.println(out.toString());
    }

    @Test
    void leafCheckerTest(){
        TreeNode x = new TreeNode((byte)44);
        Assertions.assertTrue(x.isLeaf());
        TreeNode y = new TreeNode(new TreeNode((byte) 34), new TreeNode((byte) 5));
        Assertions.assertFalse(y.isLeaf());
    }

}
