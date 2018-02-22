// Operators used, as defined by the specs

public class Operators {
    public static final int w = 32;


    public static int ch(int x, int y, int z) {
        return (x & y) ^ ((~x) & z);
    }

    public static int maj(int x, int y, int z) {
        return (x & y) ^ (x & z) ^ (y & z);
    }

    public static int SIGMA0(int x) {
        return Integer.rotateRight(x, 2) ^ Integer.rotateRight(x, 13) ^ Integer.rotateRight(x, 22);
    }

    public static int SIGMA1(int x) {
        return Integer.rotateRight(x, 6) ^ Integer.rotateRight(x, 11) ^ Integer.rotateRight(x, 25);
    }

    public static int sigma0(int x) {
        return Integer.rotateRight(x, 7) ^ Integer.rotateRight(x, 18) ^ (x >>> 3) ;
    }

    public static int sigma1(int x) {
        return Integer.rotateRight(x, 17) ^ Integer.rotateRight(x, 19) ^ (x >>> 10);
    }

    // Gives us k in bytes (minus the byte needed to encode 0b10000)
    public static int kByteLength(int msgLength) {
        final int blockBytes = 64;

        int k = (blockBytes - (msgLength + 1 + 8)) % blockBytes;
        k = k < 0 ? blockBytes + k :  k;

        return k;
    }
}
