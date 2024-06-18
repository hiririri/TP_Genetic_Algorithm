package knapsack;

import java.util.ArrayList;

public class GeneticAlgorithm {
    public Population population;
    public int populationSize;
    public KnapsackProblem problem;

    public GeneticAlgorithm(KnapsackProblem problem, int k) {
        this.problem = problem;
        this.population = new Population(problem);
        this.populationSize = 2 * k;

        for (int i = 0; i < 2 * k; i++) {
            KnapsackSolution solution = problem.generateRandomSolution();
            if (solution.fitness() == 0)
                i--;
            else
                this.population.add(solution);
        }
    }

    public ArrayList<Couple> selection() {
        ArrayList<Couple> parents = new ArrayList<>();
        RandomSelector selector = new RandomSelector();

        this.population.getSolutions().forEach(solution -> selector.add(solution.fitness()));

        for (int i = 0; i < this.populationSize / 2; i++) {
            KnapsackSolution father = this.population.get(selector.randomChoice());
            KnapsackSolution mother;
            do {
                mother = this.population.get(selector.randomChoice());
            } while (mother.equals(father));
            parents.add(new Couple(mother, father));
        }

        return parents;
    }

    public Population crossover(ArrayList<Couple> parents) {
        Population newPopulation = new Population(this.problem);

        for (int i = 0; i < populationSize / 2; i++) {
            KnapsackSolution child1 = new KnapsackSolution(problem);
            KnapsackSolution child2 = new KnapsackSolution(problem);

            for (int j = 0; j < problem.getNumberOfItems(); j++) {
                if (Math.random() < 0.5) {
                    child1.setItem(j, parents.get(i).father.getItem(j));
                    child2.setItem(j, parents.get(i).mother.getItem(j));
                } else {
                    child1.setItem(j, parents.get(i).mother.getItem(j));
                    child2.setItem(j, parents.get(i).father.getItem(j));
                }
            }

            newPopulation.add(child1);
            newPopulation.add(child2);
        }

        return newPopulation;
    }

    public KnapsackSolution solve(double mutationRate, double elitistRate) {
        int generation = 0;

        while (!(this.population.getBest().isOptimal() || generation > 1000)) {
            ArrayList<Couple> parents = selection();
            Population newPopulation = crossover(parents);

            for (int i = 0; i < this.populationSize; i++) {
                if (Math.random() < mutationRate) {
                    newPopulation.mutate(i);
                    newPopulation.get(i).repair();
                }
            }

            int eliteCount = 0;
            Iterable<KnapsackSolution> it = population.getSolutions();
            while (it.iterator().hasNext() && eliteCount <= elitistRate * this.populationSize) {
                newPopulation.replace(it.iterator().next());
                eliteCount++;
            }

            this.population = newPopulation;
            generation++;
        }

        return this.population.getBest();
    }

    public static void main(String[] args) {
        int numberOfItems = 6;
        int[] utilities = {15, 10, 20, 25, 30, 35};
        int[][] costs = {
                {1, 2},
                {2, 3},
                {3, 4},
                {4, 5},
                {5, 6},
                {6, 7}
        };
        int[] budgets = {10, 15};

        KnapsackProblem problem = new KnapsackProblem(numberOfItems, utilities, costs, budgets);
        GeneticAlgorithm ga = new GeneticAlgorithm(problem, 50);

        System.out.println("Initial population:");
        for (KnapsackSolution solution : ga.population.getSolutions()) {
            System.out.println(solution);
        }

        KnapsackSolution bestSolution = ga.solve(0.05, 0.2);
        System.out.println("Best solution found:");
        System.out.println(bestSolution);
    }
}
