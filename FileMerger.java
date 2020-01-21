// This code produces a LoanData.txt file containing information from a given acquisition file and performance file.

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

// Code that merges two data files into a single loan data file. Adjust file names as needed.

public class FileMerger {
	
	public static void main(String[] args) throws IOException { 
        // PrintWriter object for LoanData.txt 
        PrintWriter pw = new PrintWriter("LoanData.txt"); 
          
        // BufferedReader objects for merged text files. 
        BufferedReader br1 = new BufferedReader(new FileReader("./src/AcqFile.txt")); 
        BufferedReader br2 = new BufferedReader(new FileReader("./src/newPerfFile.txt")); 
        
        String line1 = br1.readLine(); 
        String line2 = br2.readLine(); 
          
        // Loop to copy lines of  AcqFile.txt and newPerfFile.txt into LoanData.txt. 
        while (line1 != null || line2 != null) { 
        	
            if(line1 != null) { 
                pw.println(line1); 
                line1 = br1.readLine(); 
            } 
              
            if(line2 != null) { 
                pw.println(line2); 
                line2 = br2.readLine(); 
            } 
        } 
      
        pw.flush(); 
          
        // Close resources. 
        br1.close(); 
        br2.close(); 
        pw.close(); 
          
        System.out.println("Merged AcqFile.txt and newPerfFile.txt into LoanData.txt"); 
    }
}
