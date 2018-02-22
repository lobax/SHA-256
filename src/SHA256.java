import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

/**
 * A SHA-256 implementation in java
 *
 * Specs can be found here:
 * http://nvlpubs.nist.gov/nistpubs/FIPS/NIST.FIPS.180-4.pdf
 */

public class SHA256 {

    private static final int[] constants =
            {
                    0x428a2f98, 0x71374491, 0xb5c0fbcf, 0xe9b5dba5, 0x3956c25b, 0x59f111f1, 0x923f82a4, 0xab1c5ed5,
                    0xd807aa98, 0x12835b01, 0x243185be, 0x550c7dc3, 0x72be5d74, 0x80deb1fe, 0x9bdc06a7, 0xc19bf174,
                    0xe49b69c1, 0xefbe4786, 0x0fc19dc6, 0x240ca1cc, 0x2de92c6f, 0x4a7484aa, 0x5cb0a9dc, 0x76f988da,
                    0x983e5152, 0xa831c66d, 0xb00327c8, 0xbf597fc7, 0xc6e00bf3, 0xd5a79147, 0x06ca6351, 0x14292967,
                    0x27b70a85, 0x2e1b2138, 0x4d2c6dfc, 0x53380d13, 0x650a7354, 0x766a0abb, 0x81c2c92e, 0x92722c85,
                    0xa2bfe8a1, 0xa81a664b, 0xc24b8b70, 0xc76c51a3, 0xd192e819, 0xd6990624, 0xf40e3585, 0x106aa070,
                    0x19a4c116, 0x1e376c08, 0x2748774c, 0x34b0bcb5, 0x391c0cb3, 0x4ed8aa4a, 0x5b9cca4f, 0x682e6ff3,
                    0x748f82ee, 0x78a5636f, 0x84c87814, 0x8cc70208, 0x90befffa, 0xa4506ceb, 0xbef9a3f7, 0xc67178f2,
            };

    // Initial sha value, see specs
    private int[] sha =
            {
                    0x6a09e667, 0xbb67ae85, 0x3c6ef372, 0xa54ff53a,
                    0x510e527f, 0x9b05688c, 0x1f83d9ab, 0x5be0cd19
            };

    public SHA256(byte[] msg) {
        // Pad the message
        byte[] paddedMsg = pad(msg);

        // Convert to integer array
        IntBuffer intBuf =
                ByteBuffer.wrap(paddedMsg)
                        .order(ByteOrder.BIG_ENDIAN)
                        .asIntBuffer();
        int[] array = new int[intBuf.remaining()];
        intBuf.get(array);

        // Parse the padded message into block
        Block[] msgBlocks = Block.parse(array);

        // Compute the sha
        for (Block b : msgBlocks) {
            computeHash(b.getBlock());
        }

    }
    
    public int[] getSHA() { 
        return sha;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int integer : sha) {

            sb.append(String.format("%08x", integer));
        }

        return sb.toString();
    }
    private void computeHash(int[] block) {
        // Init the message schedule
        int[] W = new int[64];
        for (int t = 0; t < 16; t++) {
            W[t] = block[t];
        }
        for (int t = 16; t < 64; t++) {
            W[t] = Operators.sigma1(W[t-2]) +
                    W[t-7] +
                    Operators.sigma0(W[t-15]) +
                    W[t-16];

        }

        // Init the eight working variables
        int a = sha[0];
        int b = sha[1];
        int c = sha[2];
        int d = sha[3];
        int e = sha[4];
        int f = sha[5];
        int g = sha[6];
        int h = sha[7];

        // compute the working variables
        for (int t = 0; t < 64; t++) {
            int t1 = h + Operators.SIGMA1(e) + Operators.ch(e,f,g) + constants[t] + W[t];
            int t2 = Operators.SIGMA0(a) + Operators.maj(a,b,c);
            h = g;
            g = f;
            f = e;
            e = d + t1;
            d = c;
            c = b;
            b = a;
            a = t1 + t2;
        }

        // compute the next sha values
        sha[0] = a + sha[0];
        sha[1] = b + sha[1];
        sha[2] = c + sha[2];
        sha[3] = d + sha[3];
        sha[4] = e + sha[4];
        sha[5] = f + sha[5];
        sha[6] = g + sha[6];
        sha[7] = h + sha[7];
    }

    // Pad according to the specs
    public static byte[] pad(byte[] msg) {

        // Retrieve k (in bytes, minus the byte
        // needed for the the leadning one)
        int k = Operators.kByteLength(msg.length);
        int paddedLength = msg.length + k + 1 + 8;

        byte[] paddedMsg = new byte[paddedLength];

        // Copy the message into the new array
        int i = 0;
        for (byte b : msg) {
            paddedMsg[i] = b;
            i++;
        }

        // Add the byte 0b1000
        paddedMsg[i] = (byte) 0x80;

        // Values in Java arrays are automatically set to 0.
        // Therefore, we only need to set the size at the end
        byte[] bytes = ByteBuffer.allocate(8).putLong(((long) msg.length) * 8).array();

        int n = 1;
        for (int j = paddedLength -1; j > paddedLength - 8; j--) {

            paddedMsg[j] = bytes[8 - n];
            n++;
        }

        return paddedMsg;
    }



}
