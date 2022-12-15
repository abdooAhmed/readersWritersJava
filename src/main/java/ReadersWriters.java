import java.io.FileWriter; 
import java.io.IOException;
public class ReadersWriters {

    private final int nReaders;
    private final int nWriters;
    private final Resource resource;
    private final FileWriter myWriter;

    public ReadersWriters(int nReaders, int nWriters,FileWriter file) {
        this.nReaders = nReaders;
        this.nWriters = nWriters;
        this.resource = new Resource("file.txt",file);
        this.myWriter = file;
    }

    public void execute() {
        // launch the writers
        for (int i = 0; i < nWriters; i++) {
            new Thread(new Writer(i, resource)).start();
        }

        // launch the readers
        for (int i = 0; i < nReaders; i++) {
            new Thread(new Reader(i, resource)).start();
        }
    }
    
    public static void main(String[] args) {
        // get the number of readers and writers from arguments
        int nReaders = 2;
        int nWriters = 2;
        try {
            FileWriter file = new FileWriter("filename.txt");
            ReadersWriters rw = new ReadersWriters(nReaders, nWriters,file);
            rw.execute();
            rw.myWriter.close();
          } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
    }
}
