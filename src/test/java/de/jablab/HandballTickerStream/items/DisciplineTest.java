package de.jablab.HandballTickerStream.items;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class DisciplineTest {

	private static final String INVALID_DISCIPLINE = "nodiscipline";

	private Discipline discipline;

	private void loadDiscipline(final Discipline discipline) {
		this.discipline = Discipline.parseString(discipline.toString());
		assertNotNull(this.discipline);
		assertEquals(discipline, this.discipline);
	}

	@Test
	public void testDisciplineInvalid() {
		this.discipline = Discipline.parseString(INVALID_DISCIPLINE);
		assertNull(this.discipline);
	}

	@Test
	public void testTime() {
		this.loadDiscipline(Discipline.TIME);
	}

	@Test
	public void testPenalty() {
		this.loadDiscipline(Discipline.PENALTY);
	}

	@Test
	public void testYellow() {
		this.loadDiscipline(Discipline.YELLOW);
	}

	@Test
	public void testRed() {
		this.loadDiscipline(Discipline.RED);
	}

}