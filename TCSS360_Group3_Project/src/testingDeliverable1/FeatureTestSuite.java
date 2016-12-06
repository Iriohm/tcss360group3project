package testingDeliverable1;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
  AuctionTest.class,
  BidderTest.class,
  BidTest.class,
  CalendarTest.class,
  ItemTest.class,
  NPContactTest.class

  
})

/**
 * this will run all test
 * @author David Nowlin
 *
 */
public class FeatureTestSuite {
  // the class remains empty,
  // used only as a holder for the above annotations
}
