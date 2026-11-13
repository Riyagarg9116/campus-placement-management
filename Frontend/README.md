# CampusConnect Frontend (Authentication + Java API integration)

This folder contains the updated charcoal / off-white / burnt-orange frontend.

## What is connected
- Student and Admin sign-in screens call `POST /api/auth/login`.
- Student account creation calls `POST /api/auth/register` (the Java API assigns the STUDENT role).
- Admin self-registration is not offered. Admin accounts must be provisioned by the institution.
- The role returned by the Java API is checked before the workspace is opened. The role switcher/demo role selector has been removed.
- JWT is kept in `sessionStorage` and sent as a Bearer token for protected API requests.
- Dashboard summary cards call `/api/dashboard/student` or `/api/dashboard/admin`.
- Student profile creation/update calls `/api/students/profile`.
- Student ML prediction calls the Java endpoint `/api/ml/predict`; Java then calls the Flask ML service. The frontend no longer calls Flask directly.

## Run
1. Start the Spring Boot backend on `http://localhost:8080`.
2. Configure the backend database connection in its Spring Boot `application.properties` and make sure the database server is running. The repository includes the MySQL JDBC connector, but the frontend ZIP does not create or configure a database.
3. Start the Flask ML service on the address configured by the Java `MlPredictionService` (the repository currently points to `http://localhost:5000/predict`) when using ML prediction.
4. Open `index.html` with VS Code Live Server.
5. Use the Student tab to register a student account. Admin login requires an institution-created ADMIN account.

## API base URL
The frontend defaults to `http://localhost:8080`. To change it in the browser console, run:

```js
localStorage.setItem('campusApiBase', 'http://localhost:8080');
```

Then refresh the page.

## Important integration notes
- The backend's `/api/auth/register` creates the user account only. After signing in, a student should open **My Profile** and submit registration number, branch, CGPA, active backlogs and graduation year to create the required Student profile.
- Dashboard summary cards and authentication/profile/ML requests use the Java API. Other legacy list and management screens in the supplied frontend still contain preview/demo records and their create/update actions are not yet wired to the Java CRUD endpoints. Do not treat those preview rows as live database records.
- `sessionStorage` is suitable for a local academic project prototype; production deployments should use HTTPS and review token storage and server-side security.
