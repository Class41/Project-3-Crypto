import java.io.File;

public class FileObject {

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    File file;

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    int length;

    public FileObject(File file, int length)
    {
        this.file = file;
        this.length = length;
    }
}
