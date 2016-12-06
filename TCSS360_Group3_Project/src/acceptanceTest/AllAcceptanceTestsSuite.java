package acceptanceTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ BidderBidItemAccptanceTests.class, BidderCancelMyBidAcceptanceTests.class,
		NonProfitAddInventoryItemAcceptanceTests.class, NonProfitCancelAuctionRequestAcceptanceTests.class,
		NonProfitRemoveInventoryItemAcceptanceTests.class, NonProfitSubmitAuctionRequstAcceptanceTests.class,
		StaffChangeMaximumNumberAuctionsAcceptanceTests.class, StaffViewUpcomingAuctionsAcceptanceTests.class })
public class AllAcceptanceTestsSuite {

}
