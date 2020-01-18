import java.io.*;
import java.util.*;

// The following code works to compile loans into pools, which are used to create securities.

public class PoolMaker {

	private Scanner loanFile, perfFile, acqFile;
	// private ArrayList<String[]> performanceData = new ArrayList<String[]>();
	// private ArrayList<String[]> acquisitionData = new ArrayList<String[]>();
	private ArrayList<String[]> loanData = new ArrayList<String[]>();

	public void openFile() {
		try {
			perfFile = new Scanner(new File("./src/newPerfFile.txt"));
			acqFile = new Scanner(new File("./src/AcqFile.txt"));
			loanFile = new Scanner(new File("./src/LoanData.txt"));
		} catch (Exception e) {
			System.out.println("Could not find file!");
		}
	}

	// Conveniently pulls contents of parameter files and allocates the data values
	// it finds
	// into the indexes of an ArrayList as String arrays.
	public ArrayList<String[]> dataTerminal(ArrayList<String> fileContents, Scanner file) {

		// A convenient way to query data from the table is as an ArrayList of String
		// arrays.
		ArrayList<String[]> workableData = new ArrayList<String[]>();

		// Need to apply the escape character because '|' is a logical operator.
		String delimiter = "\\|";

		// Read file and gather contents into an ArrayList.
		while (file.hasNextLine()) {
			String currRow = file.nextLine();
			fileContents.add(currRow);
		}

		// Split elements of the list along a delimiter to access individual values
		// (columns).
		for (int i = 0; i < fileContents.size(); i++) {
			String[] splitRow = fileContents.get(i).split(delimiter);
			workableData.add(splitRow);
		}

		return workableData;
	}

	public void readFile() throws IOException {
		
		// Let performance hold the contents of perfFile.
		// ArrayList<String> performance = new ArrayList<String>();
		// Let acquisitions hold the contents of acqFile.
		// ArrayList<String> acquisitions = new ArrayList<String>();
		// Let loans hold the contents of both perfFile and acqFile.
		ArrayList<String> loans = new ArrayList<String>();

		// Pass contents of each file into the dataTerminal to be processed into
		// workable data.
		// performanceData = dataTerminal(performance, perfFile);
		// acquisitionData = dataTerminal(acquisitions, acqFile);
		loanData = dataTerminal(loans, loanFile);
		getSize(loanData);

		loanData = poolFourCriteria(loanData);
		getSize(loanData);
		
		for (String[] row : loanData) {
			System.out.println("test");
			for (int i = 0; i < row.length; i++) {
				System.out.print(row[i] + " | ");
			}
			System.out.println();
		}
		
		/*
		// Write newPerfFile based on shortened transactionsList.
		FileWriter writer = new FileWriter("poolOneFile.txt");

		for (String[] row : loanData) {
			for (int i = 0; i < row.length; i++) {
				writer.write(row[i] + System.lineSeparator());
			}
		}

		writer.close();
		*/
		
	}

	public static void getSize(ArrayList<String[]> data) {
		System.out.println("Your data contains this many elements: " + data.size()/2);
	}

	public static ArrayList<String[]> poolOneCriteria(ArrayList<String[]> loanData) {

		loanData = dwellingTypeChecker(loanData, "SF");
		loanData = loanTermChecker(loanData, "30");
		loanData = LTVChecker(loanData, "85");
		loanData = DTIChecker(loanData, "40");
		
		getSize(loanData);
		
		loanData = UPBRangeChecker(loanData, "15000000", "40000000");

		return loanData;
	}

	public static ArrayList<String[]> poolTwoCriteria(ArrayList<String[]> loanData) {

		loanData = dwellingTypeChecker(loanData, "SF");
		loanData = loanTermChecker(loanData, "30");
		loanData = LTVChecker(loanData, "80");
		loanData = DTIChecker(loanData, "45");
		
		getSize(loanData);
		
		loanData = UPBRangeChecker(loanData, "20000000", "50000000");


		return loanData;
	}

	public static ArrayList<String[]> poolThreeCriteria(ArrayList<String[]> loanData) {

		loanData = dwellingTypeChecker(loanData, "SF");
		loanData = loanTermChecker(loanData, "15");
		loanData = LTVChecker(loanData, "86");
		loanData = DTIChecker(loanData, "50");
		
		getSize(loanData);
		
		loanData = UPBRangeChecker(loanData, "10000000", "30000000");

		return loanData;
	}

	public static ArrayList<String[]> poolFourCriteria(ArrayList<String[]> loanData) {

		loanData = dwellingTypeChecker(loanData, "CO");
		loanData = loanTermChecker(loanData, "15");
		loanData = LTVChecker(loanData, "80");
		loanData = DTIChecker(loanData, "35");
		
		getSize(loanData);

		loanData = UPBRangeChecker(loanData, "12000000", "25000000");

		return loanData;
	}

	public static ArrayList<String[]> poolFiveCriteria(ArrayList<String[]> loanData) {

		loanData = dwellingTypeChecker(loanData, "SF");
		loanData = loanTermChecker(loanData, "30");
		loanData = LTVChecker(loanData, "87");
		loanData = DTIChecker(loanData, "44");

		getSize(loanData);

		loanData = UPBRangeChecker(loanData, "15000000", "40000000");

		return loanData;
	}

	public static ArrayList<String[]> poolSixCriteria(ArrayList<String[]> loanData) {

		loanData = dwellingTypeChecker(loanData, "CO");
		loanData = loanTermChecker(loanData, "30");
		loanData = LTVChecker(loanData, "75");
		loanData = DTIChecker(loanData, "43");
		
		getSize(loanData);

		loanData = UPBRangeChecker(loanData, "12000000", "30000000");

		return loanData;
	}

	// Checks if dwelling type matches. Takes an ArrayList of String arrays as input
	// data, and a dwelling ID string value to compare against dwelling type of loan.
	public static ArrayList<String[]> dwellingTypeChecker(ArrayList<String[]> loanData, String dwellingID) {

		ArrayList<String[]> cleared = new ArrayList<String[]>();

		// Column of data table for type.
		int propertyTypeCol = 7;

		// ID code of dwelling type.
		String ID = dwellingID;

		// Check each loan for the code in type column.
		for (int i = 0; i < loanData.size(); i += 2) {

			// If it matches, add it to a cleared list to be passed back.
			if (loanData.get(i)[propertyTypeCol].compareTo(ID) == 0) {

				// When adding, add the index of queried acqFile content as well as
				// the corresponding newPerfFile content to transfer complete loan data.
				cleared.add(loanData.get(i));
				cleared.add(loanData.get(i + 1));
			}
		}

		return cleared;
	}

	// Checks if UPB is within range. Takes an ArrayList of String arrays as input
	// data, and minimum/maximum string values to create a range.
	public static ArrayList<String[]> UPBRangeChecker(ArrayList<String[]> loanData,
			String min, String max) {

		ArrayList<String[]> cleared = new ArrayList<String[]>();

		// Column of data table for type.
		int currentActualUPBCol = 2;

		// Minimum and maximum values for range calculation.
		int lowerBound = truncate(min);
		int upperBound = truncate(max);

		// Check each loan for the code in type column.
		for (int i = 1; i < loanData.size(); i += 2) {

			// If it matches, add it to a cleared list to be passed back.
			if (truncate(loanData.get(i)[currentActualUPBCol]) >= lowerBound
					&& truncate(loanData.get(i)[currentActualUPBCol]) <= upperBound) {

				// When adding, add the index of queried newPerfFile content as well as
				// the corresponding acqFile content to transfer complete loan data.

				cleared.add(loanData.get(i - 1));
				cleared.add(loanData.get(i));
			}
		}

		return cleared;
	}
	
	// The following method truncates a String representation of a float and returns an Integer.
	public static int truncate(String s) {

		String backToString = "";
		int result = 0;
		char[] arr = new char[s.length()];

		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) != '.') {
				arr[i] = s.charAt(i);
			} else {
				break;
			}
		}

		backToString = new String(arr);
		result = Integer.valueOf(backToString.trim());
		return result;
	}

	// Checks if loan term matches pool criteria. Takes an ArrayList of String
	// arrays as input data, and a duration String value (in years) to compare against loan
	// term.
	public static ArrayList<String[]> loanTermChecker(ArrayList<String[]> loanData, String years) {

		ArrayList<String[]> cleared = new ArrayList<String[]>();

		// Column of data table for type.
		int loanTermCol = 4;

		// Converts loan term parameter value from years into months.
		int durationInMonths = Integer.parseInt(years) * 12;

		// Check each loan for the code in type column.
		for (int i = 0; i < loanData.size(); i += 2) {

			// If it matches, add it to a cleared list to be passed back.
			if (Integer.parseInt(loanData.get(i)[loanTermCol]) == (durationInMonths)) {

				// When adding, add the index of queried acqFile content as well as
				// the corresponding newPerfFile content to transfer complete loan data.

				cleared.add(loanData.get(i));
				cleared.add(loanData.get(i + 1));
			}
		}

		return cleared;
	}

	public static ArrayList<String[]> LTVChecker(ArrayList<String[]> loanData, String LTV) {

		ArrayList<String[]> cleared = new ArrayList<String[]>();

		// Column of data table for type.
		int loanTermCol = 5;

		// Maximum LTV.
		int maxLTV = Integer.parseInt(LTV);

		// Check each loan for the code in type column.
		for (int i = 0; i < loanData.size(); i += 2) {

			// If it matches, add it to a cleared list to be passed back.
			if (Integer.parseInt(loanData.get(i)[loanTermCol]) <= (maxLTV)) {

				// When adding, add the index of queried acqFile content as well as
				// the corresponding newPerfFile content to transfer complete loan data.

				cleared.add(loanData.get(i));
				cleared.add(loanData.get(i + 1));
			}
		}

		return cleared;
	}

	public static ArrayList<String[]> DTIChecker(ArrayList<String[]> loanData, String DTI) {

		ArrayList<String[]> cleared = new ArrayList<String[]>();

		// Column of data table for type.
		int loanTermCol = 6;

		// Maximum DTI.
		int maxDTI = Integer.parseInt(DTI);

		// Check each loan for the code in type column.
		for (int i = 0; i < loanData.size(); i += 2) {

			// If it matches, add it to a cleared list to be passed back.
			if (loanData.get(i)[loanTermCol].compareTo("") != 0 &&
					Integer.parseInt(loanData.get(i)[loanTermCol]) <= (maxDTI)) {

				// When adding, add the index of queried acqFile content as well as
				// the corresponding newPerfFile content to transfer complete loan data.

				cleared.add(loanData.get(i));
				cleared.add(loanData.get(i + 1));
			}
		}

		return cleared;
	}

	public void closeFile() {
		perfFile.close();
		acqFile.close();
		loanFile.close();
	}
}
