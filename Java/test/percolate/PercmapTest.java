package percolate;

import static org.junit.Assert.*;

import org.junit.Test;

public class PercmapTest {
	
	private final int seed = 1564;
	private final int [][] percMap = {
			{0, 0, 0, 0, 0, 0},
			{0, 1, 0, 1, 1, 0},
			{0, 0, 1, 1, 0, 0},
			{0, 1, 1, 1, 0, 0},
			{0, 0, 1, 0, 1, 0},
			{0, 0, 0, 0, 0, 0}
	};
	private final int [][] expectedPercMapAfterInit = {
			{0, 0, 0, 0, 0, 0},
			{0, 1, 0, 2, 3, 0},
			{0, 0, 4, 5, 0, 0},
			{0, 6, 7, 8, 0, 0},
			{0, 0, 9, 0, 10, 0},
			{0, 0, 0, 0, 0, 0}
	};
	private final int [][] expectedPercMapAfterOneUpdate = {
			{0, 0, 0, 0, 0, 0},
			{0, 1, 0, 5, 3, 0},
			{0, 0, 7, 8, 0, 0},
			{0, 7, 9, 8, 0, 0},
			{0, 0, 9, 0, 10, 0},
			{0, 0, 0, 0, 0, 0}
	};
	
	@Test
	public void testFill(){
		int size = 1000;
		Percmap percmap = new Percmap(size, seed);
		float rho = 0.4f;
		percmap.fill(rho);
		int sum = 0;
		int [][] map = percmap.map;
		for (int i = 1; i < map.length-1; i++){
			for (int j = 1; j < map.length-1; j++){
				sum += map[i][j];
			}
		}
		float density = 1 - (float) sum / (float) (size * size);
		assertEquals("Generated map with unexpected filled densities.",
				rho, density, 0.001);
	}
	
	@Test
	public void testInit(){
		
		Percmap percmap = new Percmap(4, seed);
		percmap.map = percMap;
		percmap.init();
		
		assertArrayEquals("Return unexpected map.", 
				expectedPercMapAfterInit, percmap.map);
	}
	
	@Test
	public void testUpdate(){
		Percmap percmap = new Percmap(4, seed);
		percmap.map = expectedPercMapAfterInit;
		int expectedNumOfChanges = 5; 
		int numOfChanges = percmap.update();
		assertArrayEquals("Return unexpected map.",
				expectedPercMapAfterOneUpdate, percmap.map);
		assertEquals("Return unexpected number of updates",
				expectedNumOfChanges, numOfChanges);
	}
	
	@Test
	public void testTestPercolate(){
		int map [][] = {
				{0, 0, 0, 0, 0, 0},
				{0, 1, 0, 9, 9, 0},
				{0, 0, 9, 9, 0, 0},
				{0, 9, 9, 9, 0, 0},
				{0, 0, 9, 0, 10, 0},
				{0, 0, 0, 0, 0, 0}
		};
		Percmap percmap = new Percmap(4, seed);
		percmap.map = map;
		assertTrue("Return wrong value for test.",
				percmap.test());
	}
	
	@Test
	public void testTestNotPercolate(){
		int map [][] = {
				{0, 0, 0, 0, 0},
				{0, 3, 3, 0, 0},
				{0, 0, 3, 0, 0},
				{0, 4, 0, 5, 0},
				{0, 0, 0, 0, 0}
		};
		Percmap percmap = new Percmap(3, seed);
		percmap.map = map;
		assertFalse("Return wrong value for test.",
				percmap.test());
	}
}
