#!/bin/zsh
set -euo pipefail

# Build the project (ensures target jar and libs exist)
mvn -q -DskipTests package

# Output file (JSON Lines)
mkdir -p results
OUTFILE="results/tune.jsonl"
: > "${OUTFILE}"

# Parameter grid for Simulated Annealing (wider, granular)
T0S=(100 200 250 300 350 400 450 500 600)
ALPHAS=(0.99990 0.99992 0.99994 0.99995 0.99996 0.99997 0.99998)

# Number of reruns per configuration (can override with env RERUNS)
RERUNS=${RERUNS:-2}

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
