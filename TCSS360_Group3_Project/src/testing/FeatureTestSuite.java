package testing;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
  AuctionTest.class,
  BidderTest.class,
  BidTest.class,
  CalendarTest.class,
  ItemTest.class,
  MainTest.class,
  NPContactTest.class,
  StaffTest.class

  
})

public class FeatureTestSuite {
  // the class remains empty,
  // used only as a holder for the above annotations
}
