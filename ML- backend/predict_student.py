import joblib
import pandas as pd


def get_valid_input(message, min_value, max_value, data_type=float):

    while True:
        try:
            value = data_type(input(message))

            if min_value <= value <= max_value:
                return value

            print(f"Invalid input! Enter a value between {min_value} and {max_value}.")

        except ValueError:
            print("Please enter a valid number.")

# Load the trained model
model = joblib.load("placement_model.pkl")

# Load the scaler
scaler = joblib.load("placement_scaler.pkl")

print("Model and scaler loaded successfully!")


# Take validated student details

cgpa = get_valid_input("Enter CGPA: ", 0, 10)

internships = get_valid_input(
    "Enter number of internships: ", 0, 50, int
)

projects = get_valid_input(
    "Enter number of projects: ", 0, 50, int
)

certifications = get_valid_input(
    "Enter workshops/certifications: ", 0, 50, int
)

aptitude = get_valid_input(
    "Enter aptitude test score: ", 0, 100
)

soft_skills = get_valid_input(
    "Enter soft skills rating: ", 0, 10
)

ssc = get_valid_input("Enter SSC marks: ", 0, 100)
hsc = get_valid_input("Enter HSC marks: ", 0, 100)

extracurricular = input(
    "Extracurricular Activities (Yes/No): "
).strip().lower()

while extracurricular not in ["yes", "no"]:
    print("Please enter Yes or No.")
    extracurricular = input(
        "Extracurricular Activities (Yes/No): "
    ).strip().lower()

training = input(
    "Placement Training (Yes/No): "
).strip().lower()

while training not in ["yes", "no"]:
    print("Please enter Yes or No.")
    training = input(
        "Placement Training (Yes/No): "
    ).strip().lower()

extracurricular = 1 if extracurricular == "yes" else 0
training = 1 if training == "yes" else 0


student_data = pd.DataFrame([[
    cgpa, internships, projects, certifications,
    aptitude, soft_skills, extracurricular,
    training, ssc, hsc
]], columns=[
    "CGPA", "Internships", "Projects",
    "Workshops/Certifications", "AptitudeTestScore",
    "SoftSkillsRating", "ExtracurricularActivities",
    "PlacementTraining", "SSC_Marks", "HSC_Marks"
])

student_scaled = scaler.transform(student_data)

prediction = model.predict(student_scaled)
probability = model.predict_proba(student_scaled)

print("Standalone Probability:", probability)
print("Standalone Input Data:")
print(student_data)

if prediction[0] == 1:
    print("\nPrediction: PLACED")
else:
    print("\nPrediction: NOT PLACED")

print("Placement Probability:", round(probability[0][1] * 100, 2), "%")