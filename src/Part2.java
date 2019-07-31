import java.util.BitSet;

public class Part2 implements PartInterface {

    BitSet bits = new BitSet(256);

    public Part2()
    {
        bits.set(0, 50, false);
        System.out.println(bits.toString());
    }

    @Override
    public void Execute() {

    }

    @Override
    public void printResult() {

    }
}
