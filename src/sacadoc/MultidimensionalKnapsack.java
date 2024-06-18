package sacadoc;

import java.util.Arrays;
import java.util.Random;

public class MultidimensionalKnapsack {

    static class Item {
        int utility;
        int[] costs;

        Item(int utility, int[] costs) {
            this.utility = utility;
            this.costs = costs;
        }
    }

    static class Solution {
        int[] selection;
        int totalUtility;

        Solution(int n) {
            selection = new int[n];
            totalUtility = 0;
        }
    }

    public static void main(String[] args) {
        // Define items
        Item[] items = {
                new Item(25, new int[]{5, 7, 6}),
                new Item(15, new int[]{3, 4, 2}),
                new Item(10, new int[]{2, 3, 5}),
                new Item(20, new int[]{6, 5, 4}),
                new Item(30, new int[]{8, 6, 7}),
                new Item(35, new int[]{7, 8, 9}),
                new Item(40, new int[]{9, 10, 3}),
                new Item(50, new int[]{4, 2, 1})
        };

        // Define budgets
        int[] budgets = {15, 25, 20};

        // Number of generations
        int generations = 100;
        // Population size
        int populationSize = 20;

        // Run genetic algorithm
        Solution bestSolution = geneticAlgorithm(items, budgets, generations, populationSize);

        // Print best solution
        System.out.println("Best solution utility: " + bestSolution.totalUtility);
        System.out.println("Selection: " + Arrays.toString(bestSolution.selection));
    }

    public static Solution geneticAlgorithm(Item[] items, int[] budgets, int generations, int populationSize) {
        Random random = new Random();
        int n = items.length;
        Solution[] population = new Solution[populationSize];

        // Initialize population with random solutions
        for (int i = 0; i < populationSize; i++) {
            population[i] = generateRandomSolution(n, random);
            evaluateSolution(population[i], items);
            repairSolution(population[i], items, budgets);
        }

        Solution bestSolution = findBestSolution(population);

        for (int gen = 0; gen < generations; gen++) {
            Solution[] newPopulation = new Solution[populationSize];

            // Create new population
            for (int i = 0; i < populationSize; i++) {
                // Selection (tournament)
                Solution parent1 = tournamentSelection(population, random);
                Solution parent2 = tournamentSelection(population, random);

                // Crossover
                Solution child = crossover(parent1, parent2, random);

                // Mutation
                mutate(child, random);

                // Repair child
                repairSolution(child, items, budgets);

                // Evaluate and repair child
                evaluateSolution(child, items);

                newPopulation[i] = child;
            }

            population = newPopulation;

            Solution currentBest = findBestSolution(population);
            if (currentBest.totalUtility > bestSolution.totalUtility) {
                bestSolution = currentBest;
            }
        }

        return bestSolution;
    }

    public static Solution generateRandomSolution(int n, Random random) {
        Solution solution = new Solution(n);
        for (int i = 0; i < n; i++) {
            solution.selection[i] = random.nextInt(2);
        }
        return solution;
    }

    public static void evaluateSolution(Solution solution, Item[] items) {
        solution.totalUtility = 0;
        for (int i = 0; i < items.length; i++) {
            if (solution.selection[i] == 1) {
                solution.totalUtility += items[i].utility;
            }
        }
    }

    public static void repairSolution(Solution solution, Item[] items, int[] budgets) {
        int[] currentCosts = new int[budgets.length];
        Arrays.sort(items, (a, b) -> Integer.compare(b.utility, a.utility));

        // Calculate initial costs
        for (int i = 0; i < items.length; i++) {
            if (solution.selection[i] == 1) {
                for (int j = 0; j < budgets.length; j++) {
                    currentCosts[j] += items[i].costs[j];
                }
            }
        }

        // Step 1: Fix over-budget selections
        for (int i = items.length - 1; i >= 0; i--) {
            for (int j = 0; j < budgets.length; j++) {
                if (solution.selection[i] == 1 && currentCosts[j] > budgets[j]) {
                    solution.selection[i] = 0;
                    for (int k = 0; k < budgets.length; k++) {
                        currentCosts[k] -= items[i].costs[k];
                    }
                }
            }
        }

        // Step 2: Add back items if possible
        for (int i = 0; i < items.length; i++) {
            boolean canBeAdded = true;
            for (int j = 0; j < budgets.length; j++) {
                if (currentCosts[j] + items[i].costs[j] > budgets[j]) {
                    canBeAdded = false;
                    break;
                }
            }
            if (canBeAdded && solution.selection[i] == 0) {
                solution.selection[i] = 1;
                for (int j = 0; j < budgets.length; j++) {
                    currentCosts[j] += items[i].costs[j];
                }
            }
        }
    }

    public static Solution findBestSolution(Solution[] population) {
        Solution best = population[0];
        for (Solution sol : population) {
            if (sol.totalUtility > best.totalUtility) {
                best = sol;
            }
        }
        return best;
    }

    public static Solution tournamentSelection(Solution[] population, Random random) {
        int tournamentSize = 3;
        Solution best = population[random.nextInt(population.length)];
        for (int i = 1; i < tournamentSize; i++) {
            Solution competitor = population[random.nextInt(population.length)];
            if (competitor.totalUtility > best.totalUtility) {
                best = competitor;
            }
        }
        return best;
    }

    public static Solution crossover(Solution parent1, Solution parent2, Random random) {
        int n = parent1.selection.length;
        Solution child = new Solution(n);
        for (int i = 0; i < n; i++) {
            child.selection[i] = random.nextBoolean() ? parent1.selection[i] : parent2.selection[i];
        }
        return child;
    }

    public static void mutate(Solution solution, Random random) {
        int mutationPoint = random.nextInt(solution.selection.length);
        solution.selection[mutationPoint] = 1 - solution.selection[mutationPoint];
        // Repair the solution after mutation
        repairSolution(solution, new Item[] {new Item(10, new int[] {2, 3}), new Item(5, new int[] {1, 2}), new Item(15, new int[] {4, 1})}, new int[] {5, 4});
    }
}