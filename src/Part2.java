import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

public class Part2 implements PartInterface {

    private BigInteger bits = new BigInteger("0");
    private List<ResultObject> resultSet = null;

    public class ResultObject {
        private BigInteger value;

        BigInteger getValue() {
            return value;
        }

        ResultObject setValue(BigInteger value) {
            this.value = value;
            return this;
        }

        private String hash;

        String getHash() {
            return hash;
        }

        ResultObject setHash(String hash) {
            this.hash = hash;
            return this;
        }

        long getHashesGenerated() {
            return hashesGenerated;
        }

        ResultObject setHashesGenerated(long hashesGenerated) {
            this.hashesGenerated = hashesGenerated;
            return this;
        }

        private long hashesGenerated;

        long getTimePassedSinceStart() {
            return timePassedSinceStart;
        }

        ResultObject setTimePassedSinceStart(long timePassedSinceStart) {
            this.timePassedSinceStart = timePassedSinceStart;
            return this;
        }

        private long timePassedSinceStart;
    }

    public Part2() {
        System.out.println("\n** Part 2 START **");
        Execute();
        PrintResult();
        System.out.println("\n** Part 2 END **");
    }

    private void IncrementBinary() {
        bits = bits.add(BigInteger.ONE);
    }

    private String AttemptHash(String binaryString, MessageDigest messageDigest) {
        byte[] hashResult;
        String hashResultString = "";
        try {
            hashResult = messageDigest.digest(binaryString.getBytes());
            hashResultString = new String(hashResult);
            IncrementBinary();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return hashResultString;
    }

    private List<ResultObject> HashAndCheck(String matchString, int numMatches) {
        int matchCount = 1;
        int hashesGenerated = 0;
        long timeStarted = System.nanoTime();

        System.out.println("Working on generating hashes...please wait\n\nResults tally:\n");
        List<ResultObject> results = new ArrayList<ResultObject>();
        try {
            MessageDigest hashInstance = MessageDigest.getInstance("SHA-256");
            while (matchCount <= numMatches) {
                String result = AttemptHash(bits.toString(2), hashInstance);
                hashesGenerated++;

                if (result.startsWith(matchString.substring(0, matchCount))) {
                    System.out.print(".");
                    results.add(new ResultObject().setHash(result).setValue(bits).setHashesGenerated(hashesGenerated).setTimePassedSinceStart(System.nanoTime() - timeStarted));
                    matchCount++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return results;
    }

    @Override
    public void Execute() {
        resultSet = HashAndCheck("02151997", 3);
    }

    @Override
    public void PrintResult() {
        System.out.println("");
        for (ResultObject result : resultSet) {
            System.out.println(String.format("%s produces-> %s (after %d hashes, %d ns) %f ns/hash", result.getValue().toString(2),
                    result.getHash(), result.getHashesGenerated(), result.getTimePassedSinceStart(),
                    ((double)result.getTimePassedSinceStart() / (double)result.getHashesGenerated())));
        }
    }
}
