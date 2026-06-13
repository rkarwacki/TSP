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
- The entry point is pl.radoslawkarwacki.tsp.Main. It launches a Swing UI to visualise the TSP solution and, with the current settings in Main.java, also shows a chart.
- You can tweak the algorithm and visualisation by editing constants in Main.java (e.g., ANNEALING, NUMBER_OF_CITIES, FRAMES_IN_BETWEEN, RANGE_X/Y).
