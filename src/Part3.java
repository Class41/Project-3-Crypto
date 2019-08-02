import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class Part3 implements PartInterface {

    private List<ReturnValue> resValSet = new ArrayList<>();

    class ReturnValue {

        ReturnValue() {
            weight = 21470000;
        }

        String input;

        String getOutput() {
            return output;
        }

        void setOutput(String output) {
            this.output = output;
        }

        String output;

        int getWeight() {
            return weight;
        }

        void setWeight(int weight) {
            this.weight = weight;
        }

        private int weight;

        String getInput() {
            return input;
        }

        void setInput(String input) {
            this.input = input;
        }
    }

    Part3() {
        System.out.println("\n** Part 3 START **");
        Execute();
        PrintResult();
        System.out.println("\n** Part 3 END **");
    }

    private String AttemptHash(String data, MessageDigest messageDigest) {
        byte[] hashResult;
        String hashResultString = "";
        try {
            hashResult = messageDigest.digest(data.getBytes());
            hashResultString = new String(hashResult);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return hashResultString;
    }

    private ReturnValue GetLowestHashValue(long timeInSecondsSet) {
        ReturnValue returnVal = new ReturnValue();
        returnVal.setWeight(0);
        MessageDigest md = null;

        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }

        ReturnValue returnValTemp = null;
        for (int i = 0; i < 10; i++) {
            System.out.println(String.format("Starting iteration %d of %d of length %d seconds", i, 10, timeInSecondsSet));
            String output;
            returnValTemp = new ReturnValue();
            long currentTime = System.nanoTime();

            while (System.nanoTime() < currentTime + (timeInSecondsSet * 1e9)) {
                String input = GenerateStringRand();
                returnVal.setInput(input);
                output = AttemptHash(input, md);

                if (returnValTemp.getWeight() > CalculateStringWeight(output)) {
                    returnVal.setOutput(output);
                    returnValTemp.setWeight(CalculateStringWeight(output));
                }
            }
        }

        returnVal.setWeight(returnVal.getWeight() + returnValTemp.getWeight());
        returnVal.setWeight(returnVal.getWeight() / 10);
        return returnVal;
    }

    private int CalculateStringWeight(String text) {
        int total = 0;

        for (char item : text.toCharArray()) {
            total += item;
        }

        return total;
    }

    private static char[] allEnglishCharacters;

    private String GenerateStringRand() {
        StringBuilder val = new StringBuilder();
        SecureRandom srand = new SecureRandom();
        for (int i = 0; i < 64; i++) {
            val.append(allEnglishCharacters[srand.nextInt(25)]);
        }
        return val.toString();
    }

    @Override
    public void Execute() {
        allEnglishCharacters = new char['z' - 'a' + 1]; //https://stackoverflow.com/questions/8710719/generating-an-alphabetic-sequence-in-java YOOOINK
        for (char i = 'A'; i <= 'Z'; i++) allEnglishCharacters[i - 'A'] = i;

        resValSet.add(GetLowestHashValue(10));
        resValSet.add(GetLowestHashValue(20));
        resValSet.add(GetLowestHashValue(30));
    }

    @Override
    public void PrintResult() {
        for (ReturnValue resVal : resValSet) {
            System.out.println(String.format("Input String: %s-> Hash String: %s->\tWeight: %d", resVal.getInput(), resVal.getOutput(), resVal.getWeight()));
        }
    }
}
