
public class Writer implements Runnable {

    private final int id;
    private final Resource resource;

    public Writer(final int id, final Resource resource) {
        this.id = id;
        this.resource = resource;
    }

    @Override
    public void run() {
        try {
            //System.out.printf("Writer %d starting\n", id);

            resource.write(id, String.format("Content #%d", id));

            //System.out.printf("Writer %d is done\n", id);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}