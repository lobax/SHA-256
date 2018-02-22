import org.junit.jupiter.api.Test;

import javax.xml.bind.DatatypeConverter;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import static org.junit.jupiter.api.Assertions.*;

class SHA256Test {

    @Test
    void paddingTest() {
        // init input
        byte[] input = {
                (byte) 'a',
                (byte) 'b',
                (byte) 'c'
        };

        // init expected output
        byte[] output = new byte[64];
        output[0] = (byte) 'a';
        output[1] = (byte) 'b';
        output[2] = (byte) 'c';
        output[3] = (byte) 0x80;
        output[output.length - 1] = (byte) 24;


        assertArrayEquals(output, SHA256.pad(input));
    }

    @Test
    void shaTest() {
        byte[] input = {(byte) 0xe5};
        int[] output = {
                0xab61ba11, 0xa38b007f, 0xf98baa3a, 0xb20e2a58,
                0x4e15269f, 0xd428db3c, 0x857e2a2d, 0x568b5725
        };
        SHA256 sha = new SHA256(input);
        int[] test = sha.getSHA();

        assertArrayEquals(output, sha.getSHA());

    }

    @Test
    void shaEmptyTest() {
        byte[] input = {};
        byte[] arr = DatatypeConverter.parseHexBinary(
                "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855");
        SHA256 sha = new SHA256(input);

        // Convert to integer array
        IntBuffer intBuf =
                ByteBuffer.wrap(arr)
                        .order(ByteOrder.BIG_ENDIAN)
                        .asIntBuffer();
        int[] expected = new int[intBuf.remaining()];
        intBuf.get(expected);

        assertArrayEquals(expected, sha.getSHA());

    }
}