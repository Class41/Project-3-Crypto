import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

public class Part1 implements PartInterface {

    private List<FileObject> fobjs = new ArrayList<>();
    private final int[] sizes = new int[]{1, 16, 64, 257, 400, 512, 1024, 4096};
    private List <String> results = new ArrayList<>();


    public Part1() {
        System.out.println("\n** Part 1 START **");
        Execute();
        PrintResult();
        System.out.println("\n** Part 1 END **");
    }

    private void GenerateFiles() {
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

    private long HashTimer(File fo, int size, String hashName) {
        byte[] data = new byte[sizes[sizes.length - 1]];

        try {
            FileInputStream fin = new FileInputStream(fo);
            fin.read(data);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(32);
        }

        return HashForOne(data, hashName);
    }

    private long HashForOne(byte[] data, String hashName) {
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



    private final double AVG_HASHES_UNTIL_COLLISION_SHA_256 = Math.pow(2, 256/2);
    private final double AVG_HASHES_UNTIL_COLLISION_MD5 = Math.pow(2, 128/2);

    private void TestRunner()
    {
        for (FileObject fileobj: fobjs) {
            long MD5 = HashTimer(fileobj.getFile(), fileobj.getLength(), "MD5");
            long SHA256 = HashTimer(fileobj.getFile(), fileobj.getLength(), "SHA-256");

            long ColTimeMD5Secs = (long)(AVG_HASHES_UNTIL_COLLISION_MD5 /(double)MD5);
            long ColTimeSha256Secs = (long)(AVG_HASHES_UNTIL_COLLISION_SHA_256 /(double)SHA256);
            results.add(String.format("\nFor size: %8d SHA-256-> %6d\tMD5-> %6d\tDIFF(MD5-SHA256)-> " +
                    "%6d\tCOLL TIME IN SECS (SHA256, MD5)-> (%15d, %15d)",
                    fileobj.getLength(),SHA256, MD5, MD5 - SHA256, ColTimeSha256Secs, ColTimeMD5Secs));
        }

        results.add("\nCPU-related specs: Z7 MOBO, i7 4790k CPU, 16GB 1333Hz RAM");
    }

    @Override
    public void Execute() {
        GenerateFiles();
        TestRunner();
    }

    @Override
    public void PrintResult() {
        for (String result : results) {
            System.out.println(result);
        }
    }
}
