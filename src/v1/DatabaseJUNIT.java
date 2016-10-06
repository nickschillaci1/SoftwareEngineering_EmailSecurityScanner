/*import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;*/
import java.util.ArrayList;

/**
 * The test that check whether the file is written correctly occured before encryption was added.
 * NOTE: this will be converted to a proper junit test at a later time
 * @author Brandon Dixon
 */

public class DatabaseJUNIT {

    public static void main(String[] args) {
	System.out.println("Testing Single Term: ");
	addSingleTerm();
	System.out.println("\nTesting Multiple Terms: ");
	addMultipleTerms();
    }

    //@Test
    public static void addSingleTerm() {
	String term = "nuclear";

	Database dbTest = new Database();
	dbTest.removeAllTerms();

	try {
	    dbTest.addTerm(term);
	} catch (DatabaseAddTermException e) {
	    System.out.println("Error in add; database does not already have term "+term);
	    System.out.println(e);
	}
	//assertTrue(dbTest.hasTerm(term));
	System.out.println("Check to see if the term is added: "+dbTest.hasTerm(term));
    }

    //@Test
    public static void addMultipleTerms() {
	ArrayList<String> terms = new ArrayList<String>();
	terms.add("nuclear");
	terms.add("maintenance");
	terms.add("brandon");
	
	Database dbTest = new Database();
	dbTest.removeAllTerms();

	try {
	    dbTest.addTerm(terms) ;
	} catch (DatabaseAddTermException e) {
	    System.out.println("Error in add multiple test.");
	    System.out.println(e);
	}

	//assert true for all words
	System.out.println("Nuclear is added: "+dbTest.hasTerm("nuclear"));
	System.out.println("Maintenance is added: "+dbTest.hasTerm("maintenance"));
	System.out.println("Brandon is added: "+dbTest.hasTerm("brandon"));
    }

}
