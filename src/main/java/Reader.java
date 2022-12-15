
public class Reader implements Runnable {

    private final int id;
    private final Resource resource;

    public Reader(final int id, final Resource resource) {
        this.id = id;
        this.resource = resource;
    }

    @Override
    public void run() {
        try {
            //System.out.printf("Reader %d starting\n", id);

            resource.read(id);

            //System.out.printf("Reader %d is done\n", id);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}