import java.io.*;
import java.util.*;

// This code parses the initial performance file for desired data entries.

public class PerfFileReader {
	
	private Scanner file;
	
	public void openFile() {
		try {
			file = new Scanner (new File("./src/PerfFile.txt"));
		} catch (Exception e){
			System.out.println("Could not find file!");
		}
	}
	
	public void readFile() throws IOException {
		
		ArrayList<String> transactionsList = new ArrayList<String>();
		
		while (file.hasNextLine()) {
			String currRow = file.nextLine();
			transactionsList.add(currRow);
		}
		
		// Size of List: 7,064,088
		
		transactionsList = findLatestTransactions(transactionsList);
		// Size of List: 426,205 (cut 6,637,883 elements)

		transactionsList = removeInvalidZBC(transactionsList);
		// Size of List: 391,626 (cut 34,579 elements)
		
		// Write newPerfFile based on shortened transactionsList.
		FileWriter writer = new FileWriter("newPerfFile.txt"); 
		
		for(String str: transactionsList) {
		  writer.write(str + System.lineSeparator());
		}
		writer.close();
	}
	
	// Finds latest transactions by first identifying where loan IDs change, and
	// adds transactions before the ID changes to a latestTransactionsList.
	public ArrayList<String> findLatestTransactions(ArrayList<String> transactions) {
		
		// First step is to compile the loan IDs into an ArrayList.
		ArrayList<String> loanIDList = new ArrayList<String>();
		ArrayList<String> latestTransactionsList = new ArrayList<String>();
		
		// Need to apply the escape character because '|' is a logical operator.
		String delimiter = "\\|";
		
		// Strings used to iterate through the ID list.
		String currID = "";
		String prevID = "";
		
		// Use split to isolate IDs and add them to ID list.
		for (int i = 0; i < transactions.size(); i++) {
			String[] splitRow = transactions.get(i).split(delimiter);
			loanIDList.add(splitRow[0]);
		}
		
		// Iterate through the loanID list and find where the IDs change.
		for(int i = 0; i < loanIDList.size(); i++) {
			currID = loanIDList.get(i);
			
			// Avoid null-pointer exception.
			if (i > 0) {
				prevID = loanIDList.get(i-1);
			}
			
			// If the current ID is different from the previous ID, the previous
			// ID is classified as a 'latest transaction' and is added to a list.
			if (currID.compareTo(prevID) != 0 && prevID.compareTo("") != 0) {
				
				// Note we are calling the parameter list, not the ID list, because
				// we are interested in the other columns of this row.
				latestTransactionsList.add(transactions.get(i-1));
			}
		}
	
		return latestTransactionsList;
	}
	
	// Removes transactions with invalid zero-balance-codes by returning a list with rows 
	// that only contain three columns.
	public ArrayList<String> removeInvalidZBC(ArrayList<String> transactions) {
		
		// The returnable list of valid transactions.
		ArrayList<String> validTransactionsList = new ArrayList<String>();

		// Need to apply the escape character because '|' is a logical operator.
		String delimiter = "\\|";
		
		// Iterates through list of transactions.
		for (int i = 0; i < transactions.size(); i++) {
			
			// Splits the row into its columns.
			String[] splitRow = transactions.get(i).split(delimiter);
			
			// If the row has more than three columns leave it out of the
			// valid transactions list, because it has a non-blank ZBC.
			if (splitRow.length <= 3) {
				validTransactionsList.add(transactions.get(i));
			}
		}
		
		return validTransactionsList;
	}
	
	public void closeFile() {
		file.close();
	}
}
