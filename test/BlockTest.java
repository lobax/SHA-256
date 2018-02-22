import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import static org.junit.jupiter.api.Assertions.*;

class BlockTest {

    @Test
    void blockGenerationTest() {
        byte[] msg = (
                "fölölölölölöl"
                + "fölölölölölöl"
                + "fölölölölölöl"
                + "fölölölölölöl"
        ).getBytes();

        // Pad the message
        byte[] paddedMsg = SHA256.pad(msg);

        // Convert to integer array
        IntBuffer intBuf =
                ByteBuffer.wrap(paddedMsg)
                        .order(ByteOrder.BIG_ENDIAN)
                        .asIntBuffer();
        int[] array = new int[intBuf.remaining()];
        intBuf.get(array);

        // Parse the padded message into block
        Block[] msgBlocks = Block.parse(array);

        // Retreive the blocks:
        int[] output = new int[array.length];
        int n = 0;
        int count = 0;
        for (int i = 0; i < output.length; i++) {
            if (count >= 16) {
                n++;
                count = 0;
            }
            output[i] = msgBlocks[n].getBlock()[count];
            count++;
        }

        assertArrayEquals(array, output);

        // Expected result
    }

}