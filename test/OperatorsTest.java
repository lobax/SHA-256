
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OperatorsTest {


    @Test
    public void testK() {
        assertEquals(52, Operators.kByteLength(3));
    }

    @Test
    public void testEdgeK() {
        assertEquals(0, Operators.kByteLength(55));
    }
}