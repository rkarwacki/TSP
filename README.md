# TSP
A program to solve and visualise the Travelling Salesman Problem.

Requirements
- Java 21 (JDK 21)
- Maven 3.8+ (or newer)

How to build
- Run: mvn clean package

How to run (GUI)
- Run: java -jar target/TSP-1.0-SNAPSHOT.jar

Notes
- The entry point is pl.radoslawkarwacki.tsp.Main. It launches a Swing UI with a configuration panel where you can choose the algorithm (Annealing or 2-opt), number of cities, random seed, trials, temperature settings, chart toggle, delay, frames between, and canvas size.
- Click Start to solve and visualise the TSP. The optional chart appears when enabled.

Headless mode (for automation/agents)
- Run headless with JSON config and get compact JSON on stdout:
  java -jar target/TSP-1.0-SNAPSHOT.jar --headless --config run.json
- Optionally write the same JSON to a file:
  java -jar target/TSP-1.0-SNAPSHOT.jar --headless --config run.json --output result.json

JSON config (solver fields only; missing keys use defaults)
{
  "annealing": true,
  "numberOfCities": 350,
  "numberOfTrials": 50000,
  "randomSeed": 124531,
  "initialTemperature": 500,
  "minimalTemperature": 0.00001,
  "coolingCoefficient": 0.99995,
  "rangeX": 1600,
  "rangeY": 850
}

CLI overrides (applied after JSON)
--annealing true|false
--cities N
--trials N
--seed N
--initial-temperature N
--minimal-temperature N
--cooling-coefficient N
--range-x N
--range-y N

Output JSON shape (example)
{
  "durationMs": 8421,
  "algorithm": "Annealing",
  "finalCost": 12847.32,
  "stopReason": "Reached minimal temperature",
  "totalFrames": 4821,
  "finalTemperature": 0.000009,
  "stepsLowered": 4821,
  "inputs": {
    "annealing": true,
    "numberOfCities": 350,
    "numberOfTrials": 50000,
    "randomSeed": 124531,
    "initialTemperature": 500,
    "minimalTemperature": 0.00001,
    "coolingCoefficient": 0.99995,
    "rangeX": 1600,
    "rangeY": 850
  }
}
