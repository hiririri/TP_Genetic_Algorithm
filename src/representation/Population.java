package representation;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.TreeSet;

import algorithm.RandomSelector;


/**
 * This class represents a population of individuals,
 *  e.g., PentominosBoards in the genetic algorithm. 
 * @author Documented by Hugo Gilbert, a large part of the code 
 * was written by David Eck and Julien Lesca
 *
 */
public class Population {
	private ArrayList<PentominosBoard> population;
	private TreeSet<PentominosBoard> orderedPopulation;
	private PentominosBoard originalBoard;
	
	/** 
	 * Constructor building an instance of Population from a PentominosBoard
	 * @param original, a PentominosBoard
	 */
	public Population(PentominosBoard original) {
		this.population = new ArrayList<PentominosBoard>();
		this.orderedPopulation = new TreeSet<PentominosBoard>();
		this.originalBoard = original.copy();
	}
	
	/**
	 * Adds a PentominosBoard to the population.
	 * @param board, the PentominosBoard to be added.
	 */
	public void add(PentominosBoard board) {
		if(this.orderedPopulation.contains(board)) {
			PentominosBoard copy = this.originalBoard.copy();
			while(copy.nbrPlaced < 12) {
				copy.putPiece((int)(63 * Math.random()), (int)(8 * Math.random()), (int)(8 * Math.random()));
			}
			this.population.add(copy);
			this.orderedPopulation.add(copy);
		}
		else {
			PentominosBoard copy = board.copy();
			this.population.add(copy);
			this.orderedPopulation.add(copy);
		}
	}
	
	/**
	 * Returns the size of the population.
	 * @return the size of the population.
	 */
	public int size() {
		return this.population.size();
	}
	
	/**
	 * This methods returns the ith individual of the population.
	 * @param i, the index of the desired element.
	 * @return the ith individual of the population.
	 */
	public PentominosBoard get(int i) {
		return this.population.get(i).copy();
	}
	
	/**
	 * This method provides an iterator on the Population of individuals.
	 * This iterator makes it possible to iterate over individuals in 
	 * descending order of fitness.
	 * @return an iterator on the Population of individuals.
	 */
	public Iterator<PentominosBoard> iterator() {
		return this.orderedPopulation.descendingIterator();
	}
	
	/**
	 * Replaces the least fit individual of the population by a given individual.
	 * @param newBoard the board to be added to the population.
	 */
	public void replace(PentominosBoard newBoard) {
		PentominosBoard oldBoard = this.orderedPopulation.first();
		this.orderedPopulation.remove(oldBoard);
		this.population.remove(oldBoard);
		this.add(newBoard);
	}
	
	/**
	 * This method performs a mutation some individual in the population.  
	 * This individual is designated by her index.
	 * @param index of the individual that is mutated. 
	 */
	public void mutation(int index) {
		PentominosBoard game = this.population.get(index);
		this.orderedPopulation.remove(game);
		game = game.copy();
		RandomSelector r = new RandomSelector();
		for(int i = 1; i < 13; i++) {
			r.add(game.nbrConflict(i));
		}
		int col = r.randomChoice();
		int position= 0;
		switch(col) {
			case 1: position = (int)(2 * Math.random()); break;
			case 2: position = 2; break;
			case 3: position = 3 + (int)(4 * Math.random()); break;
			case 4: position = 7 + (int)(4 * Math.random()); break;
			case 5: position = 11 + (int)(5 * Math.random()); break;
			case 6: position = 15 + (int)(4 * Math.random()); break;
			case 7: position = 19 + (int)(4 * Math.random()); break;
			case 8: position = 23 + (int)(8 * Math.random()); break;
			case 9: position = 31 + (int)(8 * Math.random()); break;
			case 10: position = 39 + (int)(8 * Math.random()); break;
			case 11: position = 47 + (int)(8 * Math.random()); break;
			case 12: position = 55 + (int)(8 * Math.random());
		}
		TreeMap<Integer,ArrayList<Integer>> listValue = new TreeMap<Integer,ArrayList<Integer>>();
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				PentominosBoard copy = game.copy();
				copy.putPiece(position, i, j);
				int value = copy.nbrConflict(PentominosBoard.color(position));
				if(listValue.containsKey(Integer.valueOf(value)))
					listValue.get(Integer.valueOf(value)).add(Integer.valueOf(i * 8 + j));
				else {
					ArrayList<Integer> newList = new ArrayList<Integer>();
					newList.add(Integer.valueOf(i * 8 + j));
					listValue.put(Integer.valueOf(value), newList);
				}
			}
		}
		int j = 0;
		for(int i = 1; i < listValue.size(); i++) {
			if(Math.random() < 0.5)
				j = i;
		}
		Iterator<Integer> iter = listValue.descendingKeySet().iterator();
		for(int i = 0; i < j; i++) {
			iter.next();
		}
		ArrayList<Integer> listRand = listValue.get(iter.next());
		Integer pos = listRand.get((int)(listRand.size() * Math.random()));
		if(listValue.descendingKeySet().last() == 0)
			pos = listValue.get(listValue.descendingKeySet().last()).get((int)(listValue.get(listValue.descendingKeySet().last()).size() * Math.random()));
		game.putPiece(position, pos / 8, pos % 8);
		this.population.set(index, game);
		this.orderedPopulation.add(game);
	}
	
	/**
	 * Provides the best individual in the population.
	 * @return the best individual (PentominosBoard) in the population.
	 */
	public PentominosBoard getBest() {
		Iterator<PentominosBoard> it = this.iterator();
		return it.next();
	}
	
}
