package acceptanceTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import testingDeliverable1.FeatureTestSuite;

@RunWith(Suite.class)
@SuiteClasses({ AllAcceptanceTestsSuite.class ,FeatureTestSuite.class})
public class AllTestsSuite {

}
