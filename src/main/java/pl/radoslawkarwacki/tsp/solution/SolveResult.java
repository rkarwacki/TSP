package pl.radoslawkarwacki.tsp.solution;

import pl.radoslawkarwacki.tsp.model.SolutionHistory;

public class SolveResult {
    private final SolutionHistory history;
    private final RunStats stats;

    public SolveResult(SolutionHistory history, RunStats stats) {
        this.history = history;
        this.stats = stats;
    }

    public SolutionHistory getHistory() { return history; }
    public RunStats getStats() { return stats; }
}
