package knapsack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomSelector {
    private List<Integer> fitnesses;
    private Random random;

    public RandomSelector() {
        this.fitnesses = new ArrayList<>();
        this.random = new Random();
    }

    public void add(int fitness) {
        fitnesses.add(fitness);
    }

    public int randomChoice() {
        int totalFitness = fitnesses.stream().mapToInt(Integer::intValue).sum();
        int choice = random.nextInt(totalFitness);
        int sum = 0;
        for (int i = 0; i < fitnesses.size(); i++) {
            sum += fitnesses.get(i);
            if (sum > choice) {
                return i;
            }
        }
        return fitnesses.size() - 1;
    }
}
