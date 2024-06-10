package algorithm;

import representation.Couple;
import representation.PentominosBoard;
import representation.Population;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class implements a genetic algorithm to solve a Pentominos problem.
 *
 * @author Documented by Hugo Gilbert, a large part of the code
 * was written by David Eck and Julien Lesca
 */
public class GeneticAlgorithm {
    public Population population;
    public int populationSize;
    public PentominosBoard board;

    public GeneticAlgorithm(PentominosBoard game, int k) {
        this.population = new Population(game);
        for (int i = 0; i < 2 * k; i++) {
            PentominosBoard copy = game.copy();
            while (copy.nbrPlaced < 12) {
                copy.putPiece((int) (63 * Math.random()), (int) (8 * Math.random()), (int) (8 * Math.random()));
            }
            this.population.add(copy);
        }
        this.populationSize = 2 * k;
        this.board = game;
    }

    public ArrayList<Couple> selection() {
        ArrayList<Couple> parents = new ArrayList<>();

        RandomSelector selector = new RandomSelector();

        this.population.iterator().forEachRemaining(individual -> selector.add(individual.nbrFilledPlaces()));

        for (int i = 0; i < this.populationSize / 2; i++) {
            PentominosBoard father = this.population.get(selector.randomChoice());
            PentominosBoard mother = father;
            do {
                mother = this.population.get(selector.randomChoice());
            } while (mother.samePositions(father));
            parents.add(new Couple(mother, father));
        }

        return parents;
    }

    public Population crossover(ArrayList<Couple> parents) {
        Population res = new Population(this.board);

        for (int i = 0; i < populationSize / 2; i++) {
            PentominosBoard child1 = new PentominosBoard();
            PentominosBoard child2 = new PentominosBoard();

            for (Integer piece : List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)) {
                if (Math.random() < 0.5) {
                    child1.putPiece(parents.get(i).father.orientation(piece), parents.get(i).father.position(piece));
                    child2.putPiece(parents.get(i).mother.orientation(piece), parents.get(i).mother.position(piece));
                } else {
                    child1.putPiece(parents.get(i).mother.orientation(piece), parents.get(i).mother.position(piece));
                    child2.putPiece(parents.get(i).father.orientation(piece), parents.get(i).father.position(piece));
                }
            }

            res.add(child1);
            res.add(child2);
        }

        return res;
    }

    public PentominosBoard solve(double mutationRate, double elitistRate) {
        int cpt = 0;

        while (!(this.population.getBest().nbrFilledPlaces() == 64 || cpt > 1000)) {
            ArrayList<Couple> parents = selection();
            Population newPopulation = crossover(parents);

            for (int i = 0; i < this.populationSize; i++) {
                if (Math.random() < mutationRate) {
                    newPopulation.mutation(i);
                }
            }

            Iterator<PentominosBoard> it = population.iterator();
            int elite = 0;
            while (it.hasNext() && elite <= elitistRate * this.populationSize) {
                newPopulation.replace(it.next());
                elite++;
            }

            this.population = newPopulation;

            cpt++;
        }

        return this.population.getBest();
    }
}
