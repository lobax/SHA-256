import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

public class Block {
    private int[] arr;

    private Block(int[] input) {
        this.arr = input;
    }

    /**
     * Parses a padded msg into 512 bit blocks
     * @return A block array
     */
    public static Block[] parse(int[] msg) {
        // Init our block array
        int numBlocks = msg.length / 16;
        Block[] blocks = new Block[numBlocks];

        // Populate our block array
        for (int i = 0; i < numBlocks; i++) {
            int[] block = new int[16];
            int k = i * 16;
            for (int j = 0; j < 16; j++) {
                block[j] = msg[k++];
            }
            blocks[i] = new Block(block);
        }
        return blocks;
    }

    public int[] getBlock() {
        return arr;
    }
}
