package acceptanceTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import testing.FeatureTestSuite;


@RunWith(Suite.class)
@SuiteClasses({ AllAcceptanceTestsSuite.class ,FeatureTestSuite.class})

/**
 * this is a suite of all test
 *
 * @author David Nowlin
 * @version December 2016
 */
public class AllTestsSuite {

}
