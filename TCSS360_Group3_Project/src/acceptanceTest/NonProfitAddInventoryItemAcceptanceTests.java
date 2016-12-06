package acceptanceTest;

import static org.junit.Assert.*;

import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;

import model.Auction;
import model.Calendar;
import model.Item;
import model.NPContact;

/**
 * JUnit Tests for the user stories NonProfitAddInventoryItem
 *
 * @author David Nowlin
 * @version December 2016
 */
public class NonProfitAddInventoryItemAcceptanceTests {


	private Calendar aCalender;
	private NPContact myTestContact;
	private Auction myTestAuction;
	/**
     * Initializes the myTestContact object before every test.
     */
    @Before
    public void setUp() {
    	aCalender = new Calendar();
    	myTestContact = new NPContact("sdf", aCalender);
    	GregorianCalendar futerday = (GregorianCalendar) GregorianCalendar.getInstance();
    	futerday.add(GregorianCalendar.DAY_OF_YEAR, 14);
    	myTestAuction = new Auction(futerday, "testAucion");
    	myTestContact.addAuction(myTestAuction, aCalender);
    	
    }
	
	//Business rule: only the contact person for this non-profit organization associated with this auction can add an inventory item for this auction.
	// is headed by the UI
    
	@Test
	public void testSameItemCannotEnteredTwiceInventoryNotInSyStem() {
		Item testItem = new Item("1", "testname", "afndob", "small", 5.00, 1, "good");
		assertTrue(myTestContact.addItem(myTestAuction, testItem));
	}
	
	@Test
	public void testSameItemCannotEnteredTwiceInventoryInSyStem() {
		Item testItem = new Item("1", "testname", "afndob", "small", 5.00, 1, "good");
		assertTrue(myTestContact.addItem(myTestAuction, testItem));
		assertFalse(myTestContact.addItem(myTestAuction, testItem));
	}
/*
 * Business rule: all required fields must be specified at the time an inventory item is added.
 *  These fields are: Item Name (string), Condition (one of: acceptable, good, very good, like new, new), 
 *  Size (one of: small (no dimension is greater than one foot), 
 *  medium (at least one dimension is greater than one foot but no dimension is greater than three feet), 
 *  large (at least one dimension is greater than three feet) and minimum acceptable bid (positive integer). 
 *  Optional (non-required) fields are: Donor Name (string), Item Description for Bidders (string), 
 *  and Comment for Auction Central staff (string).
 *  
 *  Is going to be hand by UI

 */
	
	
}
