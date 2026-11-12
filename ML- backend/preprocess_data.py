import pandas as pd
from sklearn.preprocessing import StandardScaler

# Load all three datasets
train_data = pd.read_csv("placement_train.csv")
val_data = pd.read_csv("placement_validation.csv")
test_data = pd.read_csv("placement_test.csv")

# Convert Yes/No into 1/0
mapping = {
    "Yes": 1,
    "No": 0
}

for data in [train_data, val_data, test_data]:

    data["ExtracurricularActivities"] = (
        data["ExtracurricularActivities"].map(mapping)
    )

    data["PlacementTraining"] = (
        data["PlacementTraining"].map(mapping)
    )

    data["PlacementStatus"] = (
        data["PlacementStatus"].map({
            "Placed": 1,
            "NotPlaced": 0
        })
    )

# Separate input features and target
X_train = train_data.drop("PlacementStatus", axis=1)
y_train = train_data["PlacementStatus"]

X_val = val_data.drop("PlacementStatus", axis=1)
y_val = val_data["PlacementStatus"]

X_test = test_data.drop("PlacementStatus", axis=1)
y_test = test_data["PlacementStatus"]

# Display results
print("Training Features:")
print(X_train.head())

print("\nTraining Target:")
print(y_train.head())

print("\nTraining Shape:", X_train.shape)
print("Validation Shape:", X_val.shape)
print("Testing Shape:", X_test.shape)



# Feature Scaling
scaler = StandardScaler()

# Fit on training data and transform
X_train_scaled = scaler.fit_transform(X_train)

# Only transform validation and test data
X_val_scaled = scaler.transform(X_val)
X_test_scaled = scaler.transform(X_test)

# Display results
print("\nBefore Scaling:")
print(X_train.head())

print("\nAfter Scaling:")
print(X_train_scaled[:5])

print("\nScaled Dataset Shapes:")
print("Training:", X_train_scaled.shape)
print("Validation:", X_val_scaled.shape)
print("Testing:", X_test_scaled.shape)
