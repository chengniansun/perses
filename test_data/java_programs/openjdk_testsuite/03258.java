



import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.Reader;

public class ExceptionsInLambda {

    public static void main(Runnable p, File f) {
        main(() -> {
            StringBuilder sb = new StringBuilder();

            Reader in = new FileReader(f);
            int r;

            while ((r = in.read()) != (-1)) {
                sb.append((char) r);
            }
        }, f);

        doOpen(() -> new FileInputStream(f));
    }

    public static InputStream doOpen(Open open) {
        return open.open();
    }

    public interface Open {
        public InputStream open();
    }
}
