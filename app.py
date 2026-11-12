
from flask import Flask, request, jsonify
from flask_cors import CORS
import pandas as pd
import joblib

app = Flask(__name__)
CORS(app)

# Load trained model and scaler
model = joblib.load("placement_model.pkl")
scaler = joblib.load("placement_scaler.pkl")


# Home route
@app.route("/", methods=["GET"])
def home():
    return jsonify({
        "message": "Campus Placement Prediction API is running!"
    })


# Prediction route
@app.route("/predict", methods=["POST"])
def predict():

    try:
        # Receive student data
        data = request.get_json()

        # Required features
        features = [
            "CGPA",
            "Internships",
            "Projects",
            "Workshops/Certifications",
            "AptitudeTestScore",
            "SoftSkillsRating",
            "ExtracurricularActivities",
            "PlacementTraining",
            "SSC_Marks",
            "HSC_Marks"
        ]

        # Check missing fields
        if not isinstance(data, dict):
            return jsonify({"error": "Send valid JSON data"}), 400

        missing = [f for f in features if f not in data]

        if missing:
            return jsonify({
                "error": "Missing fields",
                "fields": missing
            }), 400

        # Convert input into DataFrame
        student_data = pd.DataFrame(
            [[data[f] for f in features]],
            columns=features
        )

        # Convert Yes/No into 1/0
        for col in ["ExtracurricularActivities", "PlacementTraining"]:

            value = str(student_data.loc[0, col]).strip().lower()

            if value not in ["yes", "no"]:
                return jsonify({
                    "error": f"{col} must be Yes or No"
                }), 400

            student_data[col] = 1 if value == "yes" else 0

        # Convert numerical values
        for col in features[:6] + ["SSC_Marks", "HSC_Marks"]:
            student_data[col] = pd.to_numeric(
                student_data[col], errors="raise"
            )

        if not student_data.notna().all().all():
            return jsonify({"error": "Values cannot be empty"}), 400

        # Check dataset ranges
        limits = {
            "CGPA": (0, 10),
            "Internships": (0, 50),
            "Projects": (0, 50),
            "Workshops/Certifications": (0, 50),
            "AptitudeTestScore": (0, 100),
            "SoftSkillsRating": (0, 10),
            "SSC_Marks": (0, 100),
            "HSC_Marks": (0, 100)
        }

        for col, (minimum, maximum) in limits.items():
            value = student_data.loc[0, col]

            if not minimum <= value <= maximum:
                return jsonify({
                    "error": f"{col} must be between {minimum} and {maximum}"
                }), 400

        # Scale input
        student_scaled = scaler.transform(student_data)

        # Predict placement
        prediction = model.predict(student_scaled)[0]
        probability = model.predict_proba(student_scaled)[0][1]

        status = "Placed" if prediction == 1 else "NotPlaced"

        return jsonify({
            "prediction": status,
            "placement_probability": round(float(probability) * 100, 2)
        })

    except (ValueError, TypeError):
        return jsonify({"error": "Please enter valid input values"}), 400

    except Exception as e:
        return jsonify({"error": str(e)}), 500


# Start server
if __name__ == "__main__":
    app.run(debug=True, port=5000)


probability = model.predict_proba(student_scaled)[0][1]