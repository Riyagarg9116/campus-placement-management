import pandas as pd
import joblib

from sklearn.metrics import (
    accuracy_score,
    confusion_matrix,
    classification_report
)

# Load test dataset
test_data = pd.read_csv("placement_test.csv")

# Encode categorical values
mapping = {"Yes": 1, "No": 0}

test_data["ExtracurricularActivities"] = test_data["ExtracurricularActivities"].map(mapping)
test_data["PlacementTraining"] = test_data["PlacementTraining"].map(mapping)

test_data["PlacementStatus"] = test_data["PlacementStatus"].map({
    "Placed": 1,
    "NotPlaced": 0
})

# Separate features and target
X_test = test_data.drop("PlacementStatus", axis=1)
y_test = test_data["PlacementStatus"]

# Load saved models and scalers
lr_model = joblib.load("placement_model.pkl")
lr_scaler = joblib.load("placement_scaler.pkl")

rf_model = joblib.load("random_forest_model.pkl")
rf_scaler = joblib.load("random_forest_scaler.pkl")

# Scale test data
X_test_lr = lr_scaler.transform(X_test)
X_test_rf = rf_scaler.transform(X_test)

# Make predictions
lr_pred = lr_model.predict(X_test_lr)
rf_pred = rf_model.predict(X_test_rf)

# Evaluate Logistic Regression
print("\n========== LOGISTIC REGRESSION ==========")

print("Test Accuracy:", accuracy_score(y_test, lr_pred) * 100, "%")

print("\nConfusion Matrix:")
print(confusion_matrix(y_test, lr_pred))

print("\nClassification Report:")
print(classification_report(
    y_test, lr_pred,
    target_names=["NotPlaced", "Placed"]
))

# Evaluate Random Forest
print("\n========== RANDOM FOREST ==========")

print("Test Accuracy:", accuracy_score(y_test, rf_pred) * 100, "%")

print("\nConfusion Matrix:")
print(confusion_matrix(y_test, rf_pred))

print("\nClassification Report:")
print(classification_report(
    y_test, rf_pred,
    target_names=["NotPlaced", "Placed"]
))