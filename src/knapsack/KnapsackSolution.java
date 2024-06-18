package knapsack;

import java.util.Arrays;
import java.util.Random;

public class KnapsackSolution {
    private KnapsackProblem problem;
    private boolean[] items;


    public KnapsackSolution(KnapsackProblem problem) {
        this.problem = problem;
        this.items = new boolean[problem.getNumberOfItems()];
    }

    public void setItem(int index, boolean value) {
        items[index] = value;
    }

    public boolean getItem(int index) {
        return items[index];
    }

    public int fitness() {
        int utilitySum = 0;
        int[] totalCosts = new int[problem.getBudgets().length];

        for (int i = 0; i < items.length; i++) {
            if (items[i]) {
                utilitySum += problem.getUtility(i);
                int[] itemCosts = problem.getCosts(i);
                for (int j = 0; j < itemCosts.length; j++) {
                    totalCosts[j] += itemCosts[j];
                }
            }
        }

        for (int i = 0; i < totalCosts.length; i++) {
            if (totalCosts[i] > problem.getBudgets()[i]) {
                return 0; // If any budget constraint is violated, the solution is not feasible
            }
        }

        return utilitySum;
    }

    public boolean isOptimal() {
        return fitness() == problem.getMaxUtility();
    }

    public boolean equals(KnapsackSolution other) {
        for (int i = 0; i < items.length; i++) {
            if (this.items[i] != other.items[i]) {
                return false;
            }
        }
        return true;
    }

    public void mutate() {
        Random random = new Random();
        int index = random.nextInt(items.length);
        items[index] = !items[index];
    }

    public void repair() {
        // Step 1: Order items by descending utility
        Integer[] indices = new Integer[items.length];
        for (int i = 0; i < items.length; i++) {
            indices[i] = i;
        }
        Arrays.sort(indices, (a, b) -> Integer.compare(problem.getUtility(b), problem.getUtility(a)));

        // Step 2: Remove items that exceed the budget
        int[] totalCosts = new int[problem.getBudgets().length];
        for (int i = 0; i < items.length; i++) {
            if (items[i]) {
                int[] itemCosts = problem.getCosts(i);
                for (int j = 0; j < itemCosts.length; j++) {
                    totalCosts[j] += itemCosts[j];
                }
            }
        }

        for (int l = items.length - 1; l >= 0; l--) {
            int i = indices[l];
            if (items[i]) {
                boolean exceedsBudget = false;
                int[] itemCosts = problem.getCosts(i);
                for (int j = 0; j < itemCosts.length; j++) {
                    if (totalCosts[j] > problem.getBudgets()[j]) {
                        exceedsBudget = true;
                        break;
                    }
                }
                if (exceedsBudget) {
                    items[i] = false;
                    for (int j = 0; j < itemCosts.length; j++) {
                        totalCosts[j] -= itemCosts[j];
                    }
                }
            }
        }

        // Step 3: Add items back if they fit within the budget
        for (int l = 0; l < items.length; l++) {
            int i = indices[l];
            if (!items[i]) {
                boolean fitsBudget = true;
                int[] itemCosts = problem.getCosts(i);
                for (int j = 0; j < itemCosts.length; j++) {
                    if (totalCosts[j] + itemCosts[j] > problem.getBudgets()[j]) {
                        fitsBudget = false;
                        break;
                    }
                }
                if (fitsBudget) {
                    items[i] = true;
                    for (int j = 0; j < itemCosts.length; j++) {
                        totalCosts[j] += itemCosts[j];
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (boolean item : items) {
            sb.append(item ? '1' : '0');
        }
        sb.append(" : ").append(fitness());
        return sb.toString();
    }
}
