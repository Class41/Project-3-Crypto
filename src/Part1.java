import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.security.MessageDigest;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Part1 implements PartInterface {

    List<FileObject> fobjs = new ArrayList<>();
    final int[] sizes = new int[]{1, 128, 256, 257, 400, 512, 1024, 4096};
    List <String> results = new ArrayList<>();


    public Part1() {
        System.out.println("\n** Part 1 START **");
        Execute();
        printResult();
        System.out.println("\n** Part 1 END **");
    }

    public void GenerateFiles() {
        File folderPath = new File("./input_files");
        folderPath.mkdirs();

        for (int size : sizes) {
            File file = new File("./input_files/" + size + ".dat");

            char[] data = new char[size];

            for (int i = 0; i < data.length; i++) {
                data[i] = 'z';
            }

            try {
                file.createNewFile();
                FileWriter dataWriter = new FileWriter(file);
                dataWriter.write(data);
                dataWriter.close();

                fobjs.add(new FileObject(file, size));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public long HashTimer(File fo, int size, String hashName) {
        File inputFile = fo;
        byte[] data = new byte[sizes[sizes.length - 1]];

        try {
            FileInputStream fin = new FileInputStream(inputFile);
            fin.read(data);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(32);
        }

        return HashForOne(data, hashName);
    }

    public long HashForOne(byte[] data, String hashName) {
        long endTime = System.nanoTime() + (long) 1e9;
        long count = 0;
        try {
            MessageDigest mdigest = MessageDigest.getInstance(hashName);

            while (System.nanoTime() < endTime) {
                mdigest.digest(data);
                count++;
            }
            System.out.print('.');
        } catch (Exception e) {
            e.printStackTrace();
        }

        return count;
    }



    public void TestRunner()
    {
        for (FileObject fileobj: fobjs) {
            long MD5 = HashTimer(fileobj.getFile(), fileobj.getLength(), "MD5");
            long SHA256 = HashTimer(fileobj.getFile(), fileobj.getLength(), "SHA-256");
            long ColTimeMD5 = MD5/;
            long ColTimeSha256;
            results.add(String.format("\nFor size: %8d SHA-256-> %6d\tMD5-> %6d\tDIFF(MD5-SHA256)-> %6d", fileobj.getLength(),SHA256, MD5, MD5 - SHA256));
        }
    }

    @Override
    public void Execute() {
        GenerateFiles();
        TestRunner();
    }

    @Override
    public void printResult() {
        for (String result : results) {
            System.out.println(result);
        }
    }
}
