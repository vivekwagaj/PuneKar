# Agent Constitution for PuneKar Project

This document outlines the rules, guidelines, and discrete prompts for AI agents to follow while contributing to the PuneKar project. The goal is to ensure consistency, clarity, and adherence to the project's vision and principles.

---

## General Guidelines

1. **Adhere to the Vision**: Ensure all contributions align with the project's vision of enabling citizens to report, prioritize, verify, and track ward-level public issues.
2. **Follow the MVP Scope**: Prioritize tasks that fall under the Minimal Viable Product (MVP) scope before moving to nice-to-have features.
3. **Maintain Transparency**: Document all changes, decisions, and contributions clearly in the repository.
4. **Ensure Data Integrity**: Follow the database schema and business rules strictly to maintain data consistency and integrity.
5. **Respect Privacy**: Adhere to the data privacy and legal guidelines outlined in the main README.
6. **Test Thoroughly**: Write unit and integration tests for all features. Ensure manual end-to-end testing for critical flows.

---

## Discrete Prompts for AI Agents

### 1. User Authentication
- **Task**: Implement Google-based user authentication using Firebase Auth.
- **Endpoints**:
  - `POST /api/auth/verify`: Accept Firebase ID token, verify it, and return the app user record.
- **Database**:
  - Ensure the `users` table includes `firebase_uid`, `email`, `display_name`, and `photo_url`.
- **Validation**:
  - Verify Firebase tokens using the Firebase Admin SDK.
  - Create a new user record if `firebase_uid` does not exist.

### 2. Issue Submission
- **Task**: Allow users to submit issues with title, category, description, photo, and location.
- **Endpoints**:
  - `POST /api/issues`: Accept issue details and save them to the database.
- **Database**:
  - Use the `issues` table schema provided in the README.
- **Validation**:
  - Ensure all required fields are provided.
  - Validate photo uploads and location data.

### 3. View Issues
- **Task**: Implement endpoints to list and view issue details.
- **Endpoints**:
  - `GET /api/issues`: List issues with sorting options (e.g., top, new).
  - `GET /api/issues/{id}`: Fetch issue details by ID.
- **Database**:
  - Query the `issues` table with filters and sorting.
- **Validation**:
  - Ensure only issues from the current ward are returned.

### 4. Upvote Issues
- **Task**: Enable users to upvote issues to prioritize them.
- **Endpoints**:
  - `POST /api/issues/{id}/vote`: Add an upvote.
  - `DELETE /api/issues/{id}/vote`: Remove an upvote.
- **Database**:
  - Use the `votes` table schema provided in the README.
- **Validation**:
  - Enforce one vote per user per issue using a unique constraint.

### 5. Verification
- **Task**: Allow users to upload "after" photos and verification notes.
- **Endpoints**:
  - `POST /api/issues/{id}/verify`: Upload verification details.
- **Database**:
  - Use the `verifications` table schema provided in the README.
- **Validation**:
  - Ensure photos are uploaded and at least 2 verifications are required to mark an issue as resolved.

### 6. Dashboard
- **Task**: Create a simple dashboard to display counts and top categories.
- **Endpoints**:
  - `GET /api/dashboard/summary`: Return counts of reported, resolved, and pending issues.
- **Database**:
  - Aggregate data from the `issues` table.

---

## Development Workflow

1. **Branching**:
   - Use `feature/<name>` branches for new features.
   - Merge into `dev` branch after review.
2. **Testing**:
   - Write unit tests for all services.
   - Use embedded Postgres or Testcontainers for integration tests.
   - Perform manual end-to-end testing with Postman and Expo client.
3. **Deployment**:
   - Deploy backend to Render or Google Cloud Run.
   - Deploy frontend to Expo or Vercel.

---

## Contribution Rules

1. **Code Quality**:
   - Follow Java and React Native best practices.
   - Ensure code is clean, modular, and well-documented.
2. **Commit Messages**:
   - Use descriptive commit messages (e.g., `Add user authentication endpoint`).
3. **Pull Requests**:
   - Include a clear description of changes.
   - Reference related issues or tasks.

---

## Future Enhancements

- **Phase 2**:
  - Implement polls, automated escalations, crowdfunding, and heatmap views.
- **Phase 3**:
  - Add trust systems, anonymous usernames, and PMC API integrations.

---

## Contacts

- Use the main README for additional context and guidelines.
- Reach out to contributors for questions or clarifications.