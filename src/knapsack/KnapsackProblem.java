package knapsack;

import java.util.Arrays;
import java.util.Random;

public class KnapsackProblem {
    private int numberOfItems;
    private int[] utilities;
    private int[][] costs;
    private int[] budgets;

    public KnapsackProblem(int numberOfItems, int[] utilities, int[][] costs, int[] budgets) {
        this.numberOfItems = numberOfItems;
        this.utilities = utilities;
        this.costs = costs;
        this.budgets = budgets;
    }

    public int getNumberOfItems() {
        return numberOfItems;
    }

    public int getUtility(int index) {
        return utilities[index];
    }

    public int[] getCosts(int index) {
        return costs[index];
    }

    public int[] getBudgets() {
        return budgets;
    }

    public int getMaxUtility() {
        return Arrays.stream(utilities).sum();
    }

    public KnapsackSolution generateRandomSolution() {
        KnapsackSolution solution = new KnapsackSolution(this);
        Random random = new Random();

        for (int i = 0; i < numberOfItems; i++) {
            solution.setItem(i, random.nextBoolean());
        }

        return solution;
    }
}