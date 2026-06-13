#!/bin/zsh
set -euo pipefail

# Build the project (ensures target jar and libs exist)
mvn -q -DskipTests package

# Output file (JSON Lines)
mkdir -p results
OUTFILE="results/tune.jsonl"
: > "${OUTFILE}"

# Parameter grid for Simulated Annealing (wider, granular)
T0S=(475 500 525 550 575 600)
ALPHAS=(0.94 0.95 0.96 0.97 0.98)

# Number of reruns per configuration (can override with env RERUNS)
RERUNS=${RERUNS:-1}

total=0
for t0 in "${T0S[@]}"; do
  for a in "${ALPHAS[@]}"; do
    for run in $(seq 1 ${RERUNS}); do
      total=$(( total + 1 ))
      echo "[$total] T0=${t0}, alpha=${a}, run=${run} -> appending to ${OUTFILE}"
      # Let the app write compact JSON to stdout; append to JSONL
      java -jar target/TSP-1.0-SNAPSHOT.jar --headless --config run.json \
        --initial-temperature ${t0} --cooling-coefficient ${a} >> "${OUTFILE}"
      echo "" >> "${OUTFILE}"  # ensure newline separation
    done
  done
done

echo "Completed ${total} headless runs. Results appended to ${OUTFILE}"
