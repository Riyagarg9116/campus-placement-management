import pandas as pd

from sklearn.preprocessing import StandardScaler
from sklearn.linear_model import LogisticRegression
from sklearn.metrics import (
    accuracy_score,
    confusion_matrix,
    classification_report
)

# 1. Load datasets
train_data = pd.read_csv("placement_train.csv")
val_data = pd.read_csv("placement_validation.csv")

# 2. Encode categorical values
mapping = {"Yes": 1, "No": 0}
target_mapping = {"Placed": 1, "NotPlaced": 0}

for data in [train_data, val_data]:

    data["ExtracurricularActivities"] = (
        data["ExtracurricularActivities"].map(mapping)
    )

    data["PlacementTraining"] = (
        data["PlacementTraining"].map(mapping)
    )

    data["PlacementStatus"] = (
        data["PlacementStatus"].map(target_mapping)
    )

# 3. Separate features and target
X_train = train_data.drop("PlacementStatus", axis=1)
y_train = train_data["PlacementStatus"]

X_val = val_data.drop("PlacementStatus", axis=1)
y_val = val_data["PlacementStatus"]

# 4. Feature scaling
scaler = StandardScaler()

X_train_scaled = scaler.fit_transform(X_train)
X_val_scaled = scaler.transform(X_val)

# 5. Create Logistic Regression model
model = LogisticRegression(max_iter=1000)

# 6. Train the model
model.fit(X_train_scaled, y_train)

print("Model training completed!")

# 7. Make predictions
y_pred = model.predict(X_val_scaled)

# 8. Evaluate the model
accuracy = accuracy_score(y_val, y_pred)

print("\nValidation Accuracy:", accuracy * 100, "%")

print("\nConfusion Matrix:")
print(confusion_matrix(y_val, y_pred))

print("\nClassification Report:")
print(classification_report(
    y_val,
    y_pred,
    target_names=["NotPlaced", "Placed"]
))


import joblib

joblib.dump(model, "placement_model.pkl")
joblib.dump(scaler, "placement_scaler.pkl")

print("\nModel and scaler saved successfully!")
