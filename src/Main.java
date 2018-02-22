import javax.xml.bind.DatatypeConverter;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        while (s.hasNextLine()) {
            byte[] input = DatatypeConverter.parseHexBinary(s.nextLine());
            SHA256 sha = new SHA256(input);
            System.out.println(sha);
        }

    }
}
