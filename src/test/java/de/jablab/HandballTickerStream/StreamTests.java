package de.jablab.HandballTickerStream;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ MatchPhaseTest.class, MatchTimeTest.class, PlayerTest.class,
		ScoreTest.class, StreamItemTypeTest.class, TeamRoleTest.class,
		TeamTest.class })
public class StreamTests {

}