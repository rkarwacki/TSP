# TSP
A program to solve and visualise the Travelling Salesman Problem.

Requirements
- Java 21 (JDK 21)
- Maven 3.8+ (or newer)

How to build
- Run: mvn clean package

How to run
- Run: java -jar target/TSP-1.0-SNAPSHOT.jar

Notes
- The entry point is pl.radoslawkarwacki.tsp.Main. It launches a Swing UI with a configuration panel where you can choose the algorithm (Annealing or 2-opt), number of cities, random seed, trials, temperature settings, chart toggle, delay, frames between, and canvas size.
- Click Start to solve and visualise the TSP. The optional chart appears when enabled.
