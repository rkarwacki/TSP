import json
import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns

# 1. Parse JSON data from log file
data = []
with open('../results/tune.jsonl', 'r') as f:
    for line in f:
        if line.strip():
            record = json.loads(line)
            # Flatten inputs dict
            inputs = record.pop('inputs')
            record.update(inputs)
            data.append(record)

df = pd.DataFrame(data)

# 2. Plot 1: Heatmap of Initial Temperature vs Cooling Coefficient
plt.figure(figsize=(10, 6))
# Pivot to create a matrix of average final costs
pivot_df = df.pivot_table(index='initialTemperature', 
                          columns='coolingCoefficient', 
                          values='finalCost', 
                          aggfunc='mean')

sns.heatmap(pivot_df, annot=True, fmt=".1f", cmap="viridis_r", cbar_kws={'label': 'Final Cost'})
plt.title('Effect of Initial Conditions on Final TSP Cost (Lower is Better)')
plt.xlabel('Cooling Coefficient (Alpha)')
plt.ylabel('Initial Temperature')
plt.tight_layout()
plt.show()

# 3. Plot 2: Duration vs Final Cost Scatter Chart
plt.figure(figsize=(8, 5))
sns.scatterplot(data=df, x='durationMs', y='finalCost', hue='coolingCoefficient', palette='coolwarm', size='initialTemperature', sizes=(40, 200))
plt.title('Computation Time vs Solution Quality')
plt.xlabel('Duration (ms)')
plt.ylabel('Final Path Cost')
plt.legend(bbox_to_anchor=(1.05, 1), loc='upper left')
plt.tight_layout()
plt.show()