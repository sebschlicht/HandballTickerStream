package de.jablab.HandballTickerStream.items;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ PhaseEndItemTest.class, PhaseEndSubTypeTest.class, TimeoutPhaseEndItemTest.class,
		TextItemTest.class, ScoreItemTest.class, ScoringTypeTest.class,
		DisciplineTest.class, FoulItemTest.class })
public class StreamItemTests {

}