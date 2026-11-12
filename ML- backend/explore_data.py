import pandas as pd

# Load the downloaded dataset
data = pd.read_csv("campus_placement_dataset.csv")

# 1. Dataset dimensions
print("Dataset Shape:")
print(data.shape)

# 2. Column information
print("\nDataset Information:")
print(data.info())

# 3. Check missing values
print("\nMissing Values:")
print(data.isnull().sum())

# 4. Check placement distribution
print("\nPlacement Distribution:")
print(data["PlacementStatus"].value_counts())

# 5. Statistical summary
print("\nStatistical Summary:")
print(data.describe())




# Inspect categorical columns
categorical_columns = [
    "ExtracurricularActivities",
    "PlacementTraining",
    "PlacementStatus"
]

for column in categorical_columns:
    print(f"\n{column} values:")
    print(data[column].value_counts())

    print("Unique values:", data[column].unique())



print("\nComplete Feature Ranges:")
print(data.describe().T)
