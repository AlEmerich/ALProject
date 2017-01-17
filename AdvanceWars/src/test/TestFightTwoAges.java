/**
 * D. Auber & P. Narbel
 * Solution TD Architecture Logicielle 2016 Université Bordeaux.
 */
package test;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Before;
import org.junit.Test;

import soldier.ages.AgeFutureFactory;
import soldier.ages.AgeMiddleFactory;
import soldier.core.AgeAbstractFactory;
import soldier.core.Unit;
import soldier.core.UnitGroup;
import soldier.core.UnitVisitor;
import soldier.util.AddSimpleUnitObserver;
import soldier.util.DeadUnitCounterObserver;
import soldier.util.WeaponCounterVisitor;

public class TestFightTwoAges {

	AgeAbstractFactory age1, age2;
	Unit team1, team2; 
	
	static Unit createTeam(AgeAbstractFactory fact, String prefix)  {
		UnitGroup sg = new UnitGroup(prefix + "Animals");
		UnitGroup bl  = new UnitGroup(prefix + "Worms");
		bl.addUnit(fact.infantryUnit(prefix + "nicky"));
		bl.addUnit(fact.infantryUnit(prefix + "tomy"));
		sg.addUnit(bl);
		bl.addEquipment(fact.attackWeapon());
		bl.addEquipment(fact.defenseWeapon());
		bl.addEquipment(fact.attackWeapon());
		
		UnitGroup pig = new UnitGroup(prefix + "Pigs");
		pig.addUnit(fact.riderUnit(prefix + "jenny"));
		pig.addUnit(fact.riderUnit(prefix + "kenny"));
		sg.addUnit(pig);
		pig.addEquipment(fact.defenseWeapon());
		pig.addEquipment(fact.defenseWeapon());
		pig.addEquipment(fact.attackWeapon());
		return sg;
	}
	
	@Before
	public void setUp() throws Exception {	
 		 age1 = new AgeMiddleFactory();
		 age2 = new AgeFutureFactory();
		 team1 = createTeam(age1, "Team1::"); 
		 team2 = createTeam(age2, "Team2::"); 
	}
	
	@Test
	public void createTeam1Test() {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(stream));
		createTeam(age1, "Team1::");
		String expectedOutput = "Impossible to add Sword to Team1::nicky\r\n"
				+ "Impossible to add Sword to Team1::tomy\r\n"
				+ "Impossible to add Shield to Team1::jenny\r\n"
				+ "Impossible to add Shield to Team1::kenny\r\n";
		assertEquals(expectedOutput, stream.toString());
	}

	@Test
	public void createTeam2Test() {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(stream));
		createTeam(age2, "Team2::");
		String expectedOutput = "Impossible to add Shield to Team2::jenny\r\n"
				+ "Impossible to add Shield to Team2::kenny\r\n"
				+ "Impossible to add Nerf to Team2::jenny\r\n"
				+ "Impossible to add Nerf to Team2::kenny\r\n";
		assertEquals(expectedOutput, stream.toString());
	}
 
	@Test
	public void WeaponCounterTest() {
		UnitVisitor visitor = new AddSimpleUnitObserver(new DeadUnitCounterObserver());
		team1.accept(visitor);
		team2.accept(visitor);

		WeaponCounterVisitor c1 = new WeaponCounterVisitor();
		team1.accept(c1);
//		System.out.println(team1.getName() + " has got " + c1.attWeapon + 
//		" attack weapons and "  + c1.defWeapon + " defense weapon");
        assertEquals(c1.attWeapon, 4);
        assertEquals(c1.defWeapon, 4);

        c1.clear();
		team2.accept(c1);
//		System.out.println(team2.getName() + " has got " + c1.attWeapon + 
//		" attack weapons and "  + c1.defWeapon + " defense weapon");
        assertEquals(c1.attWeapon, 4);
        assertEquals(c1.defWeapon, 4);
	}		

    @Test
    public void fightTest() {
    	System.out.println("IN TEST");
		int round = 0;
		float st1 = 0, st2 = 0; 
		while(team1.alive() && team2.alive()) {
			round++;
			st1 = team1.strike();
			team2.parry(st1);
			st2 = team2.strike();
			team1.parry(st2);
			System.out.println("Round  #" + round);
			System.out.println(team1.getName() + " attack with force : " + st1);
			System.out.println(team2.getName() + " attack with force : " + st2);
		}
		assertEquals(round, 2);
		assertEquals(st1, 222.0, 0.00001);	
		assertEquals(st2, 0.0, 0.00001);	
        assertEquals(team1.alive(), true);
        assertEquals(team2.alive(), false);        
//	    System.out.println("The end ... " + (team1.alive() ? team1.getName() : 
//      team2.getName()) + " won." );
	}

}
