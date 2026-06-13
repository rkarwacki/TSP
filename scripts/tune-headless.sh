#!/bin/zsh
set -euo pipefail

# Build the project (ensures target jar and libs exist)
mvn -q -DskipTests package

mkdir -p results
: > results/index.tsv

# Parameter grid for Simulated Annealing (10 runs total)
T0S=(400 500 600 700 800)
ALPHAS=(0.99995 0.99997)

count=0
for t0 in "${T0S[@]}"; do
  for a in "${ALPHAS[@]}"; do
    count=$((count + 1))
    if [ $count -gt 10 ]; then
      break 2
    fi
    outfile="results/tune_T${t0}_a${a}.json"
    echo "Running: T0=${t0}, alpha=${a} -> ${outfile}"
    java -jar target/TSP-1.0-SNAPSHOT.jar --headless --config run.json \
      --initial-temperature ${t0} --cooling-coefficient ${a} --output "${outfile}"

    # Extract finalCost from JSON and index it
    cost=$(grep -o '"finalCost":[^,]*' "${outfile}" | head -1 | cut -d: -f2 | tr -d ' ')
    echo "${cost}\t${outfile}" >> results/index.tsv
  done
done

bestLine=$(sort -n results/index.tsv | head -1 || true)
if [ -z "${bestLine}" ]; then
  echo "No results found."
  exit 1
fi
bestCost="${bestLine%%$'\t'*}"
bestFile="${bestLine#*$'\t'}"

echo "Best configuration across ${count} runs:"
echo "  finalCost=${bestCost}"
echo "  resultFile=${bestFile}"
echo "Done."
