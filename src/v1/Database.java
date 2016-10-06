import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * This class will maintain the database, handle database encryption, and handles modifications to the database.
 *
 * SO FAR: this file only creates the database locally in a non-encrypted file
 *
 * @author Brandon Dixon
 * @version 10/6/16
 **/
public class Database {

    private ArrayList<String> terms;
    private final String fileName = "info.info";
    private File textFile = new File(fileName);
    

    /**
     * This will initalize the database and load in terms if there are any to load
     */
    public Database() {
	terms = new ArrayList<String>();
	
	//Read the file in
	try {
	    FileReader fileReader = new FileReader(textFile);

	    BufferedReader bufferedReader = new BufferedReader(fileReader);
	    String tempLine = "";
	    
	    try {
	        while ((tempLine=bufferedReader.readLine())!=null) {
	            terms.add(tempLine);
	        }
	    
	        bufferedReader.close();
	        fileReader.close();
	    } catch (IOException q) {
		//nothing
	    }

        } catch (FileNotFoundException e) {
	    rewriteFile();
	}
	
    }


    /**
     * This will check to see if the database contains a certain term.
     * @param String term to check for
     * @return boolean of whether the database has the term or not
     */
    public boolean hasTerm(String term) {
	//root word manipulation will happen here - for now, use single line

	return terms.contains(term);
    }

    /**
     * This will add a term to the database
     * @param String term to add to the database
     * @exception DatabaseAddTermException if the word is already present in the database
     */
    public void addTerm(String term) throws DatabaseAddTermException {
	//manipulate to root word if necessary

	//throw an exception if the term is there already
	if (terms.contains(term)) {
	    throw new DatabaseAddTermException(term);
	}

	terms.add(term);

	//rewrite the file
	rewriteFile(); 
	
    }

    /**
     * This will add multiple terms to the database
     * @param ArrayList<Strimg> terms to add to the database
     * @exception DabaseAddTermException if one or more words is already present in the database
     */
    public void addTerm(ArrayList<String> termArray) throws DatabaseAddTermException {
	//manipulate root words as necessary
	ArrayList<String> conflicts = new ArrayList<String>();

	//add all of the 
	int length = termArray.size();
	for (int i=0; i<length; i++) {
	    String temp = termArray.get(i);
	    if (terms.contains(temp)) {
		conflicts.add(temp);
	    } else {
		terms.add(temp);
	    }
	}

	rewriteFile();

	if (conflicts.size()>0) {
	    throw new DatabaseAddTermException(conflicts);
	}

    }

    /**
     * This will remove a term from the database.
     * @param String term to be removed
     * @exception DatabaseRemoveTermException if the word is not present in the database
     * 
     */
    public void removeTerm(String term) throws DatabaseRemoveTermException {
	//manipulate the root word if neccesary

	//throws an exception if the term does not exist
	if (!terms.contains(term)) {
	    throw new DatabaseRemoveTermException(term);
	}
	
	terms.remove(term);

	//rewrite the file
	rewriteFile();
    }

    /**
     * This will remove one or more terms from the database.
     * @param ArrayList<String> terms to be removed
     * @exception DatabaseRemoveTermException if one or more words is not present in the database
     */
    public void removeTerm(ArrayList<String> termArray) throws DatabaseRemoveTermException {
	//manipulate the root word if neccessary

	ArrayList<String> error = new ArrayList<String>();
	//remove all of the terms
	int length = termArray.size();
	for (int i=0; i<length; i++) {
	    String temp = termArray.get(i);
	    if (!terms.contains(temp)) {
		error.add(temp);
	    } else {
		terms.remove(temp);
	    }
	}

	if (error.size()>0) {
	    throw new DatabaseRemoveTermException(error);
	}

    }

    public void removeAllTerms() {
	terms = new ArrayList<String>();

	rewriteFile();
    }


    /**
     * This is a private method that will totally rewrite the file with the contents of the ArrayList terms
     */
    private void rewriteFile() {
	try {
	    FileWriter fileWriter = new FileWriter(fileName,false);
	    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
	    
	    int numTerms = terms.size();
	    for (int i=0; i<numTerms; i++) {
		bufferedWriter.write(terms.get(i));
		bufferedWriter.newLine();
	    }
	    
	    bufferedWriter.close();
	    fileWriter.close();
	} catch (IOException e) {
	    //nothing yet
	}
    }
  
}