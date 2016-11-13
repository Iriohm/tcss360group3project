package testing;

import org.junit.Before;
import org.junit.Test;

import junit.framework.*;
import model.Bidder;
import model.Item;

public class BidderTest extends TestCase {

	/** An Item object that will be used to test the Bidder class. */
    private Item myTestItem;
    
    private Bidder myTestBidder;
    
    /**
     * Initializes the myTestTruck object before every test.
     */
    @Before
    public void setUp() {
    	//(String theID, String theName, String theDescription, double theMinimumBid,
		//		int theQuantity, String theCondition) {
    	myTestItem = new Item("theID", "Beanie Baby", "Brand-new Beanie Baby", 5.00, 1, "New in box");
    	myTestBidder = new Bidder("bid4lyfe", null);
    }

    /**
     * Test method for {@link model.Truck#canPass(model.Terrain, model.Light)}.
     */
    @Test
    public void testCanPass() {
    	//Placing a valid bid on myTestItem...ALLOWED
    	assertTrue(myTestBidder.placeBid(myTestItem, 6.00));
    	
    	//Placing another bid on myTestItem...NOT ALLOWED
    	assertFalse(myTestBidder.placeBid(myTestItem, 7.00));
    }
}
