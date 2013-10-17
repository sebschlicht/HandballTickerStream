package de.jablab.HandballTickerStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class TeamRoleTest {

	private static final String INVALID_TEAM_ROLE = "norole";

	private TeamRole role;

	private void loadTeamRole(final TeamRole role) {
		this.role = TeamRole.parseString(role.toString());
		assertNotNull(this.role);
		assertEquals(role, this.role);
	}

	@Test
	public void testRoleInvalid() {
		this.role = TeamRole.parseString(INVALID_TEAM_ROLE);
		assertNull(this.role);
	}

	@Test
	public void testHome() {
		this.loadTeamRole(TeamRole.HOME);
	}

	@Test
	public void testGuest() {
		this.loadTeamRole(TeamRole.GUEST);
	}

}