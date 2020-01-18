import java.util.*;

// The following is an incomplete implementation for a loan criteria checking interface.

public interface PoolChecker {

	public static ArrayList<String[]> dwellingTypeChecker1(ArrayList<String[]> loanData) {
		
		ArrayList<String[]> cleared = new ArrayList<String[]>();
		
		// Column of data table for property type.
		int propertyTypeCol = 7;
		
		for (int i = 0; i < loanData.size(); i+=2) {
			if (loanData.get(i)[propertyTypeCol].compareTo("SF") == 0) {
				cleared.add(loanData.get(i));
			}
		}
		
		return cleared;
	}
	
}

