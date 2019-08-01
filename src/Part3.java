import sun.plugin2.message.Message;

import java.security.MessageDigest;
import java.security.SecureRandom;

public class Part3 implements PartInterface {

    public ReturnValue resval;

    class ReturnValue {

        public ReturnValue()
        {
            output = "";
        }

        public String input;

        public String getOutput() {
            return output;
        }

        public ReturnValue setOutput(String output) {
            this.output = output;
            return this;
        }

        public String output;

        public int getWeight() {
            return weight;
        }

        public ReturnValue setWeight(int weight) {
            this.weight = weight;
            return this;
        }

        public int weight;

        public String getInput() {
            return input;
        }

        public ReturnValue setInput(String input) {
            this.input = input;
            return this;
        }
    }

    public Part3() {
        System.out.println("\n** Part 3 START **");
        Execute();
        PrintResult();
        System.out.println("\n** Part 3 END **");
    }

    public String AttemptHash(String data, MessageDigest messageDigest) {
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

    public ReturnValue GetLowestHashValue() {
        ReturnValue returnVal = new ReturnValue();
        MessageDigest md = null;

        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }

        long currentTime = System.nanoTime();
        String output = "";

        while (currentTime > System.nanoTime() + 10e9) {
            String input = GenerateStringRand();
            returnVal.setInput(input);
            output = AttemptHash(input, md);

            if(CalculateStringWeight(returnVal.getOutput()) > CalculateStringWeight(output))
                returnVal.setOutput(output);
        }

        return returnVal;
    }

    private int CalculateStringWeight(String text) {
        int total = 0;

        for ( char item: text.toCharArray()) {
            total += item;
        }

        return total;
    }

    static char[] allEnglishCharacters;

    public String GenerateStringRand() {
        String val = "";
        SecureRandom srand = new SecureRandom();
        for (int i = 0; i < 64; i++) {
            val += allEnglishCharacters[srand.nextInt(25)];
        }
        return val;
    }

    @Override
    public void Execute() {
        allEnglishCharacters = new char['z' - 'a' + 1]; //https://stackoverflow.com/questions/8710719/generating-an-alphabetic-sequence-in-java YOOOINK
        for (char i = 'A'; i <= 'Z'; i++) allEnglishCharacters[i - 'A'] = i;

        resval = GetLowestHashValue();
    }

    @Override
    public void PrintResult() {
        System.out.println(String.format("Input String: %s-> Hash String: %s->\tWeight: %d", resval.getInput(), resval.getOutput(), resval.getWeight()));
    }
}
