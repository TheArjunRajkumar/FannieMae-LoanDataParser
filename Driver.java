import java.io.IOException;

// This is a simple Driver for our file readers.

public class Driver {

	public static void main(String[] args) throws IOException {
		
		//PerfFileReader r = new PerfFileReader();
		PoolMaker r = new PoolMaker();
		r.openFile();
		r.readFile();
		r.closeFile();
	}
}
