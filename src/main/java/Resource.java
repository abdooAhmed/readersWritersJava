import java.util.concurrent.Semaphore;
import java.io.*;

import javax.sound.midi.spi.MidiFileWriter; 
public class Resource {

    private String content;
    private final Lightswitch readSwitch = new Lightswitch();
    private final Semaphore roomEmpty = new Semaphore(1);
    private final Semaphore turnstile = new Semaphore(1);
    private final FileWriter File;

    public Resource(String initContent,FileWriter file) {
        this.content = initContent;
        File = file;
    }

    public String write(int id, String data) throws InterruptedException {
        turnstile.acquire();
        roomEmpty.acquire();
        try {
            // critical section
            //System.out.printf("--Writer %d entered the room\n", id);

            //Thread.sleep(100);
            content = data;
            //System.out.printf("--Writer %d wrote '%s'\n", id, content);
			try {
				//System.out.println("--Writer %d wrote '%s'\n");
				BufferedWriter out = new BufferedWriter(
		                new FileWriter("file.txt", true));
		 
		            // Writing on output stream
		            out.write(content+"\n");
		            // Closing the connection
		            out.close();
			    } catch (IOException e) {
			      System.out.println("An error occurred.");
			      e.printStackTrace();
			    }
            return content;
        } finally {
            turnstile.release();
            roomEmpty.release();
            //System.out.printf("--Writer %d left the room\n", id);
        }
    }

    public String read(int id) throws InterruptedException, IOException {
        turnstile.acquire();
        turnstile.release();
        readSwitch.lock(roomEmpty);
        try {
            // critical section
            //System.out.printf("--Reader %d entered the room\n", id);

            //Thread.sleep(100);
            //System.out.println(content);
            //System.out.printf("--Reader %d read '%s'\n", id, content);
        	File file = new File("file.txt");
        	BufferedReader reader = new BufferedReader(new FileReader(file));
        	String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

        } finally {
            readSwitch.unlock(roomEmpty);
            //System.out.printf("--Reader %d left the room\n", id);
        }
        return content;
    }
}