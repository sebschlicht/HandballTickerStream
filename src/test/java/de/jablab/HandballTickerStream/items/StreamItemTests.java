package de.jablab.HandballTickerStream.items;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ PhaseEndItemTest.class, PhaseEndSubTypeTest.class,
		ScoreItemTest.class, ScoringTypeTest.class, TextItemTest.class })
public class StreamItemTests {

}