import java.util.*;

/**
 * Created by radek on 19.06.15.
 */
class Solver {
    //list of all points to visit
    private static ArrayList<Point> points = new ArrayList<>();

    //adjacency matrix
    private ArrayList<ArrayList<Double>> adjMatrix = new ArrayList<>();

    //found solution
    public static ArrayList<Point> solution = new ArrayList<>();

    //visited points
    private ArrayList<Integer> visitedPoints = new ArrayList<>();

    //used for visualisation
    private static Solution finalSolution = new Solution();
    public static int iterations;
    Solver(int noOfPoints,Random r){
        clear();
        for (int i=0;i<noOfPoints;i++) {
            addPoint(new Point(r));
        }
        /*for (int i=0;i<12;i++)
            for (int j=0;j<12;j++)
                addPoint(new Point(i*40+30,j*40+30));*/
        fillAdjacencyMatrix();
    }

    public void clear() {
        points.clear();
        solution.clear();
        visitedPoints.clear();
        adjMatrix.clear();
        finalSolution.clear();
    }

    public void addPoint(Point p) {
        points.add(p);
    }

    public static ArrayList<Point> getPoints() {
        ArrayList<Point> listOfPoints;
        listOfPoints = Solver.points;
        return listOfPoints;
    }

    private void selectRandomTour(){
        ArrayList<Point> randomTour;
        randomTour = points;
        Collections.shuffle(randomTour);
        solution = randomTour;
    }

    private ArrayList<Point> swapTwoPoints(ArrayList<Point> points){
        Random random = new Random();
        ArrayList<Point> copy = new ArrayList<>(points);
        int noOfPoints = points.size();
        int point_1 = random.nextInt(noOfPoints);
        int point_2 = random.nextInt(noOfPoints);
        Collections.swap(copy,point_1,point_2);
        return copy;
    }

    public static double getTotalTourCost(ArrayList<Point> points){
        double cost=0;
        int i;
        if (points.size()>2) {
            for (i = 0; i < points.size() - 1; i++) {
                cost += points.get(i).calculateDistanceToPoint(points.get(i + 1));
            }
            cost += points.get(i).calculateDistanceToPoint(points.get(0));
        }
        return cost;
    }

    public ArrayList<Point> swapTwoEdges (ArrayList<Point> points){
        int range1=0, range2=0, min, max;
        int noOfPoints = points.size();
        Random random = new Random();
        ArrayList<Point> firstSub;
        ArrayList<Point> middleSub;
        ArrayList<Point> lastSub;
        ArrayList<Point> copy = new ArrayList<>(points);

        while (Math.abs(range1-range2)<2){
            range1=random.nextInt(noOfPoints);
            range2=random.nextInt(noOfPoints);
        }
        min = Math.min(range1,range2);
        max = Math.max(range1,range2);

        firstSub = new ArrayList<Point>(copy.subList(0,min));
        middleSub = new ArrayList<Point>(copy.subList(min,max));
        Collections.reverse(middleSub);
        lastSub = new ArrayList<Point>(copy.subList(max,noOfPoints));
        ArrayList<Point> proposedSolution = new ArrayList<Point>();
        proposedSolution.addAll(firstSub);
        proposedSolution.addAll(middleSub);
        proposedSolution.addAll(lastSub);
        return proposedSolution;
    }
/*
    public static double getTourCostAtStep(int stepNumber){
        double cost=0;
        if(stepNumber>0){
            SolutionStep route = finalSolution.playbackSolution.get(stepNumber);
            int i = 0;
            while (i!=stepNumber){
                cost+=route.step.get(i).calculateDistanceToPoint(route.step.get(i+1));
                i++;
            }
            if(stepNumber+1 == Solver.points.size()) {
                cost += route.step.get(i).calculateDistanceToPoint(route.step.get(0));
            }
        }
        return cost;
    }
*/
    public void solveUsing2Opt(){
        System.out.println();
        System.out.println("<------- 2OPT ------>");

        Solution sol = new Solution();
        int numberOfFailed = 0;
        int numberOfTrials = 10000;
        selectRandomTour();
        double currentBest = getTotalTourCost(solution);
        ArrayList<Point> dataSet = solution;
        SolutionStep ss = new SolutionStep(dataSet);
        sol.addStep(ss);
        int noOfPoints = Solver.points.size();
        System.out.println(noOfPoints);

        while(numberOfFailed < numberOfTrials ){
            ArrayList<Point> proposedSolution = new ArrayList<Point>();
            proposedSolution = swapTwoEdges(solution);
            if (getTotalTourCost(proposedSolution)<currentBest){
                numberOfFailed = 0;
                solution=proposedSolution;
                SolutionStep step = new SolutionStep(proposedSolution);
                sol.addStep(step);
                currentBest = getTotalTourCost(proposedSolution);
                //System.out.println((int)currentBest);
            }
            else{
                numberOfFailed++;

            }
        }
        System.out.println((int)currentBest);
    }

    public void solveUsingSimulatedAnnealing(double starting_temp, double t_min, double lambda, int trials, int recordFramesWithStep){
        double temperature=starting_temp;
        double temperature_min=t_min;
        double deltaDist;

        Solution sol = new Solution();
        int iterationsWithoutImprovement = 0;
        ArrayList<Point> prev_solution;
        ArrayList<Point> new_solution = null;
        ArrayList<Point> current_solution;
        selectRandomTour();
        int i=0;
        prev_solution = new ArrayList<>(solution);
        while(temperature>temperature_min && iterationsWithoutImprovement < trials){
            i++;
            current_solution = new ArrayList<>(prev_solution);
            new_solution=new ArrayList<>(swapTwoEdges(prev_solution));
            deltaDist = getTotalTourCost(new_solution) - getTotalTourCost(prev_solution);
            if (deltaDist < 0 || (deltaDist > 0 && Math.exp(-deltaDist/temperature)>Math.random())){
                iterationsWithoutImprovement = 0;
                prev_solution=new ArrayList<>(new_solution);
                /*if(i%1==0) {
                    SolutionStep step = new SolutionStep(new_solution);
                    sol.addStep(step);
                }*/
            }
            else {
                new_solution = current_solution;
                iterationsWithoutImprovement++;
            }
            temperature=lambda*temperature;
            if(i%recordFramesWithStep==0) {
                SolutionStep step = new SolutionStep(new_solution);
                sol.addStep(step);
            }
        }
        SolutionStep step = new SolutionStep(new_solution);
        sol.addStep(step);
        System.out.println(i+","+getTotalTourCost(new_solution));
        iterations = i;
    }

    public void fillAdjacencyMatrix() {
            for (int ix = 0; ix < points.size(); ix++) {
                ArrayList<Double> temp = new ArrayList<>();
                for (int iy = 0; iy < points.size(); iy++) {
                    if (ix == iy) {
                        temp.add(0.0);
                    } else {
                        temp.add(points.get(ix).calculateDistanceToPoint(points.get(iy)));
                    }
                }
                adjMatrix.add(temp);
            }
        }

    private int getIndexOfMin(ArrayList<Double> arr) {
        Double min = Double.MAX_VALUE;
        int index = -2;
        for (int i = 0; i < arr.size(); i++) {
            Double val = arr.get(i);
            if (!(val == -1.0) && !visitedPoints.contains(i) && val < min) {
                min = val;
                index = i;
            }
        }
        return index;
    }

    public void solveUsingNN(int startingPoint) {
        int noOfVisited = 0;

        //find nearest point from the starting one
        int nearest = getIndexOfMin(adjMatrix.get(startingPoint));
        Solution sol = new Solution();

        //until we've visited all points
        while (noOfVisited!=points.size()) {
            //get next nearest point and add it to visited
            nearest = getIndexOfMin(adjMatrix.get(nearest));
            visitedPoints.add(nearest);

            //add this point to solution
            Point newPoint = points.get(nearest);
            solution.add(newPoint);

            //create a new frame for animation, containing all previous steps and recently added one
            SolutionStep ss = new SolutionStep();
            for (Point aSolution : solution) {
                Point p = new Point(aSolution.getX(), aSolution.getY());
                ss.addPoint(p);
            }
            sol.addStep(ss);
            noOfVisited++;
        }
        finalSolution=sol;
        System.out.println(getTotalTourCost(solution));
    }
}

