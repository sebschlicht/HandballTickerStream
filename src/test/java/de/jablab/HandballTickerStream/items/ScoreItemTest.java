package de.jablab.HandballTickerStream.items;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.BeforeClass;
import org.junit.Test;

import de.jablab.HandballTickerStream.HandballTickerStream;
import de.jablab.HandballTickerStream.Player;
import de.jablab.HandballTickerStream.Score;
import de.jablab.HandballTickerStream.StreamItemTest;
import de.jablab.HandballTickerStream.TeamRole;
import de.jablab.HandballTickerStream.exceptions.StreamItemFormatException;

public class ScoreItemTest extends StreamItemTest {

	private static final Score VALID_SCORE = new Score(21, 22);
	private static final TeamRole VALID_TEAM_ROLE = TeamRole.HOME;
	private static final ScoringType VALID_SCORING_TYPE = ScoringType.NORMAL;
	private static final Player VALID_PLAYER = new Player(12, 1, "Mr. T",
			TeamRole.HOME);

	private static ScoreItem SOURCE_SCORE_ITEM;

	private ScoreItem item;

	@BeforeClass
	public static void beforeClass() {
		StreamItemTest.beforeClass();

		SOURCE_SCORE_ITEM = new ScoreItem(VALID_PUBLISHED, VALID_TIME,
				VALID_MESSAGE, VALID_SCORE, VALID_TEAM_ROLE,
				VALID_SCORING_TYPE, VALID_PLAYER);
		SOURCE_ITEM = SOURCE_SCORE_ITEM;
	}

	@Override
	protected void loadItem(final boolean error) {
		try {
			this.item = ScoreItem.parseJSON(this.sourceObject.toJSONString());
		} catch (final StreamItemFormatException e) {
			this.setErrTrace(e.getMessage());
		}

		if (error) {
			assertNull(this.item);
		} else {
			assertNotNull(this.item);
		}
	}

	@Test
	public void testValidItem() {
		this.loadItem(false);
		this.checkStreamItem(this.item);

		assertEquals(SOURCE_SCORE_ITEM.getScore(), this.item.getScore());
		assertEquals(SOURCE_SCORE_ITEM.getTeam(), this.item.getTeam());
		assertEquals(SOURCE_SCORE_ITEM.getScoringType(),
				this.item.getScoringType());
		assertEquals(SOURCE_SCORE_ITEM.getPlayer(), this.item.getPlayer());
	}

	@Test
	public void testScoreMissing() {
		this.putToItemObject(
				HandballTickerStream.StreamItem.ScoreItem.KEY_SCORE, null);
		this.loadItem(true);
		this.checkForMissingField(HandballTickerStream.StreamItem.ScoreItem.KEY_SCORE);
	}

	@Test
	public void testScoreMalformed() {
		this.putToItemObject(
				HandballTickerStream.StreamItem.ScoreItem.KEY_SCORE,
				MALFORMED_JSON_OBJECT);
		this.loadItem(true);
		this.checkForMalformedField(HandballTickerStream.StreamItem.ScoreItem.KEY_SCORE);
	}

	@Test
	public void testTeamMissing() {
		this.putToItemObject(
				HandballTickerStream.StreamItem.ScoreItem.KEY_TEAM_ROLE, null);
		this.loadItem(true);
		this.checkForMissingField(HandballTickerStream.StreamItem.ScoreItem.KEY_TEAM_ROLE);
	}

	@Test
	public void testScoringTypeMissing() {
		this.putToItemObject(
				HandballTickerStream.StreamItem.ScoreItem.KEY_TYPE, null);
		this.loadItem(false);
		assertNull(this.item.getScoringType());
	}

	@Test
	public void testPlayerMissing() {
		this.putToItemObject(
				HandballTickerStream.StreamItem.ScoreItem.KEY_PLAYER, null);
		this.loadItem(false);
		assertNull(this.item.getPlayer());
	}

	@Test
	public void testPlayerMalformed() {
		this.putToItemObject(
				HandballTickerStream.StreamItem.ScoreItem.KEY_PLAYER,
				MALFORMED_JSON_OBJECT);
		this.loadItem(true);
		this.checkForMalformedField(HandballTickerStream.StreamItem.ScoreItem.KEY_PLAYER);
	}

}