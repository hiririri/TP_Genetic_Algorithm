package knapsack;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

public class Population {
    private List<KnapsackSolution> solutions;
    private KnapsackProblem problem;

    public Population(KnapsackProblem problem) {
        this.problem = problem;
        this.solutions = new ArrayList<>();
    }

    public void add(KnapsackSolution solution) {
        solutions.add(solution);
    }

    public KnapsackSolution getBest() {
        return solutions.stream().max(Comparator.comparingInt(KnapsackSolution::fitness)).orElse(null);
    }

    public void mutate(int index) {
        solutions.get(index).mutate();
    }

    public void replace(KnapsackSolution solution) {
        KnapsackSolution worstSolution = solutions.stream().min(Comparator.comparingInt(KnapsackSolution::fitness)).orElse(null);
        if (worstSolution != null) {
            solutions.remove(worstSolution);
            solutions.add(solution);
        }
    }

    public List<KnapsackSolution> getSolutions() {
        return solutions;
    }

    public KnapsackSolution get(int i) {
        return solutions.get(i);
    }
}
