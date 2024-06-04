package algorithm;
import java.util.ArrayList;
import java.util.Iterator;

import representation.Couple;
import representation.PentominosBoard;
import representation.Population;

/**
 * This class implements a genetic algorithm to solve a Pentominos problem.  
 * @author Documented by Hugo Gilbert, a large part of the code 
 * was written by David Eck and Julien Lesca
 *
 */
public class GeneticAlgorithm {
	public Population population;
	public int populationSize;
	public PentominosBoard board;
	
	public GeneticAlgorithm(PentominosBoard game, int k) {
		this.population = new Population(game);
		for(int i = 0; i < 2 * k; i++) {
			PentominosBoard copy = game.copy();
			while(copy.nbrPlaced < 12) {
				copy.putPiece((int)(63 * Math.random()), (int)(8 * Math.random()), (int)(8 * Math.random()));
			}
			this.population.add(copy);
		}
		this.populationSize = 2 * k;
		this.board = game;
	}
	
	public ArrayList<Couple> selection(){
		//TODO
		return null;
	}
	
	public Population crossover(ArrayList<Couple> parents){
		//TODO
		return null;
	}
	
	public PentominosBoard solve(double mutationRate, double elitistRate) {
		//TODO
		return null;
	}
}
