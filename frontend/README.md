# CampusConnect — Charcoal, Off-White & Burnt Orange

A redesigned Campus Placement Management System frontend.

## Visual direction
A mature enterprise-SaaS look: charcoal navigation, warm off-white workspace, restrained burnt-orange accents, fine borders, compact typography and minimal shadows.

## Run
1. Extract the ZIP.
2. Open the folder in VS Code.
3. Start `index.html` using Live Server.

## Included pages
Admin: Overview, Students, Companies & Jobs, Applications, Interviews, Placement Reports, ML Prediction.
Student: My Dashboard, Explore Jobs, My Applications, Interview Schedule, My Profile, ML Prediction.

## Integration notes
- Demo records are currently stored in browser memory; Java backend persistence/authentication is not connected yet.
- ML Prediction calls `http://127.0.0.1:5000/predict`. Verify the payload field names against your Flask API and enable CORS if needed.
- Connect page forms and tables to the Java backend APIs when those endpoints are ready.


## Role-specific demo views
- **Placement Admin:** institution-wide student directory, company/job management, all applications, interview management, placement reports, and ML assessment preview.
- **Student:** personal dashboard, currently open jobs, own applications, own interview schedule, own profile, and own ML assessment. The sample student preview is Aarav Sharma.
- The role selector is a UI preview only. It is not authentication. Enforce role checks and student ownership in the Java backend before using real student data.
- CGPA input in the ML form accepts values from 0 to 10.
