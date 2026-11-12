import pandas as pd

# Load dataset
data = pd.read_csv("placement_data.csv")

# Display dataset
print(data)

# Display first five rows
print("\nFirst 5 Records:")
print(data.head())

# Dataset information
print("\nDataset Information:")
print(data.info())

# Check number of rows and columns
print("\nDataset Shape:")
print(data.shape)
