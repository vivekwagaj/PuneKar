## PuneKar (Viman Nagar) — README

Lightweight civic app to report, prioritise, verify and track ward-level public issues.

Pilot: Viman Nagar (Pune). Mobile-first (PWA / React Native later).

Focus: visible wins, accountability, transparency.

⸻

# Vision

Enable citizens to report problems (potholes, garbage, streetlights, encroachments), vote on what matters, and verify real resolution with photo-evidence — creating a public, auditable backlog that drives civic pressure and community fixes.

⸻

# Principles / Non-negotiables
	•	Local first — one ward (Viman Nagar) pilot.
	•	Data-first accountability — every action is auditable.
	•	Transparency — open-source code + public data exports.
	•	Non-violent, lawful — no illegal direct actions; community fixes must follow safety & permissions.
	•	Anti-hijack design — decentralised ward chapters, term limits, open governance (later).

⸻

# MVP — Minimal Scope (must-have)

(Ship this first; nothing else)
1.	User auth (Google via Firebase Auth) — frictionless signup.
2.	Submit Issue — title, category, description, 1 photo, location (pin or GPS).
3.	View Issues (ward feed) — list & detail page, status label.
4.	Upvote (priority) — one vote per user per issue; ranking by (votes + recency).
5.	Basic Verification — ability to upload “after” photo and mark as community-verified.
6.	Simple dashboard — counts: reported / resolved / pending + top categories.I a

⸻

# Nice-to-have (Phase 2)
	•	Polls (ward priority polls).
	•	Automated escalations (email/Twitter template generation).
	•	Crowdfunding widget + transparent receipts.
	•	Map heatmap view.
	•	Per-ward cloning template.
	•	Gamification (badges).
    •	Notifications (email/push).


# Nice-to-have (Phase 3)
    •	Trust system → prevent spam/trolls.
    •	Anonymous usernames (while backend keeps verified real identity).
    •	Integration with PMC APIs (if access is allowed).
    •	Public dashboards for transparency (ward comparison, issue heatmaps).

⸻

# Tech Stack (recommended for MVP)
	•	Frontend: React Native (Expo) or React PWA (you chose mobile-first; Expo recommended for fast dev + web preview).
	•	Backend: Spring Boot (Java) — REST API.
	•	Database: PostgreSQL (Neon or Supabase free tier).
	•	Auth: Firebase Auth (Google sign-in / phone OTP later).
	•	Media: Supabase Storage or Cloudinary free tier for images.
	•	Hosting (free-tier options):
	•	Backend: Render or Google Cloud Run (always-free quotas).
	•	DB: Supabase / Neon (free Postgres).
	•	Frontend: Vercel / Expo host / PWA on Vercel.
	•	Dev tools: Flyway or Liquibase for migrations, Postman for API testing.

⸻

# High-level architecture (summary)
	•	React Native frontend ↔ Spring Boot API (HTTPS)
	•	Spring Boot connects to Postgres for structured data
	•	Images stored in Supabase Storage / Cloudinary; URLs saved in DB
	•	Firebase Auth handles OAuth, backend verifies tokens and maps firebase_uid to local users table

⸻

# Auth flow (Google sign-in + Postgres mapping)
	1.	Frontend uses Firebase Auth to sign-in the user (Google).
	2.	Frontend sends Firebase ID Token to backend (Authorization: Bearer <token>).
	3.	Backend verifies ID Token (Firebase Admin SDK) → extracts firebase_uid, email, name, photo_url.
	4.	Backend looks up users table by firebase_uid. If not present, create row. Return app JWT or session cookie (optional) or rely on Firebase token for auth on each request.

User table MUST include firebase_uid column to map the external identity.

⸻

# Database schema (Postgres) — minimal

-- users
CREATE TABLE users (
id SERIAL PRIMARY KEY,
firebase_uid VARCHAR(128) UNIQUE NOT NULL,
email VARCHAR(255) NOT NULL,
display_name VARCHAR(255),
photo_url TEXT,
ward VARCHAR(100) DEFAULT 'Viman Nagar',
created_at TIMESTAMP DEFAULT now()
);

-- issues
CREATE TABLE issues (
id SERIAL PRIMARY KEY,
title VARCHAR(255) NOT NULL,
description TEXT,
category VARCHAR(100), -- e.g. pothole, garbage, streetlight, encroachment
latitude DOUBLE PRECISION,
longitude DOUBLE PRECISION,
image_url TEXT,
status VARCHAR(50) DEFAULT 'reported', -- reported, in_progress, resolved, rejected
created_by INT REFERENCES users(id),
created_at TIMESTAMP DEFAULT now(),
resolved_at TIMESTAMP NULL
);

-- votes
CREATE TABLE votes (
id SERIAL PRIMARY KEY,
issue_id INT REFERENCES issues(id),
user_id INT REFERENCES users(id),
created_at TIMESTAMP DEFAULT now(),
UNIQUE(issue_id, user_id)
);

-- verifications (before/after)
CREATE TABLE verifications (
id SERIAL PRIMARY KEY,
issue_id INT REFERENCES issues(id),
user_id INT REFERENCES users(id),
photo_url TEXT,
note TEXT,
created_at TIMESTAMP DEFAULT now()
);

Add migrations with Flyway/Liquibase.

⸻

# API design (minimal endpoints)

All API endpoints assume Bearer token verified (Firebase or app JWT) and ward = Viman Nagar for pilot.

Auth
•	POST /api/auth/verify — accepts Firebase ID token, returns app user record (and app JWT optionally)

Users
•	GET /api/users/me — get profile

Issues
•	GET /api/issues?ward=VimanNagar&sort=top|new — list issues
•	POST /api/issues — create issue {title, description, category, lat, lng, image}
•	GET /api/issues/{id} — get issue detail
•	PATCH /api/issues/{id} — change status (admin/mod only) or add resolution note
•	POST /api/issues/{id}/verify — upload after-photo + verification note

Votes
•	POST /api/issues/{id}/vote — body: {type: 'up'} (idempotent)
•	DELETE /api/issues/{id}/vote — remove vote

Admin / Dashboard
•	GET /api/dashboard/summary — counts by category, top issues
•	GET /api/exports/issues.csv — open data export (CSV)

⸻

# Business rules & constraints
	•	One vote per user per issue (enforced by DB unique constraint).
	•	Only verified users can submit issues (no anonymous initial MVP).
	•	Photos required for verification. At least 2 community verifications to mark “community-verified resolved” (configurable).
	•	Rate limit: max 10 issue submissions / day per user (configurable).
	•	Duplicate detection: naive duplicate check by spatial + title similarity (later add fuzzy matching).

⸻

# Anti-abuse / Moderation
	•	Basic spam prevention: CAPTCHA or email/phone verification optional later.
	•	Moderation roles: moderator (ward admin) can mark invalid/rejected.
	•	Audit logs for all status changes (who, when).
	•	Privacy: do not publish personal email/phone in public exports. Personal info only for backend mapping.

⸻

# Data privacy & legal
	•	Store minimal personal data: firebase_uid, email, display_name.
	•	Provide opt-out / deletion endpoint (DELETE /api/users/me) which anonymises posts (keeps integrity of public dataset while removing PII).
	•	Keep an immutable audit trail for transparency — record actions, but redact PII on deletion.

⸻

# Hosting & free-tier recommendations (MVP)
	•	Postgres: Supabase free tier or Neon free.
	•	Images: Supabase Storage or Cloudinary free tier.
	•	Backend: Render (free) or Google Cloud Run always-free. Use Docker image for Spring Boot (Cloud Run works great).
	•	Frontend: Expo (for RN) and Vercel (for PWA if you add one).

⸻

# Local dev setup (quick)
	1.	Install Java11/17, Maven/Gradle, Node 16+, Postgres locally (or use Supabase).
	2.	Create .env with:

SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/punekar
SPRING_DATASOURCE_USERNAME=...
SPRING_DATASOURCE_PASSWORD=...
FIREBASE_PROJECT_ID=...
FIREBASE_PRIVATE_KEY=...
STORAGE_PROVIDER=SUPABASE|CLOUDINARY
SUPABASE_URL=...
SUPABASE_KEY=...

	3.	Run DB migrations (Flyway).
	4.	Start Spring Boot: ./mvnw spring-boot:run or ./gradlew bootRun.
	5.	Use Postman to hit POST /api/auth/verify with Firebase token or temporarily enable test user flow.

⸻

# Frontend (mobile-first)
	•	Build in React Native (Expo). During dev, preview on phone (Expo Go) or browser (Expo web) — great for testing.
	•	Start with screens:
	•	Login (Google)
	•	Home (priority feed)
	•	Submit Issue
	•	Issue Detail (vote, verify)
	•	Minimal Dashboard (counts)

⸻

# Testing strategy
	•	Unit tests for services (Spring Boot).
	•	Integration tests with embedded Postgres (or Testcontainers).
	•	Manual end-to-end with Postman and Expo client.
	•	Small user test in Viman Nagar WhatsApp groups before public launch.

⸻

# Metrics / KPIs (what to measure)
	•	Number of issues reported (per week).
	•	% issues verified / resolved.
	•	Avg resolution time (community or PMC).
	•	Active users per week (5–10% of ward population as success metric early).
	•	Crowdfunding / volunteer hours (if you add DIY fix later).

⸻

# Soft launch plan (Viman Nagar)
	1.	Get 10–20 seed users (local RWAs, friends, college groups).
	2.	Use the app to crowdsource 1 small “We Fixed It” project (e.g., patch 1 pothole) — document costs & photos publicly.
	3.	Post results on r/pune + local WhatsApp + Instagram memes.
	4.	Iterate UI and fix friction.

⸻

# Governance & charter (brief)
	•	Publish a short “PuneKar Charter” on repo (terms: 2-term limit for ward admins, open data, no-party leadership rule for core admin roles).
	•	Enforce transparency of funds and moderation actions.

⸻

# Scalability / future architecture notes
	•	Start monolith Spring Boot. When load grows split services:
	•	Auth Service (stateless)
	•	Issue Service (main CRUD)
	•	Vote Service (high-write; consider Redis cache + periodic aggregation)
	•	Notification Service (async, with message queue: Kafka/RabbitMQ)
	•	Add Redis for caching and rate-limiting. Use PostGIS if you need advanced geospatial queries.

⸻

# Contribution / solo workflow
	•	You’re starting solo — keep branches: feature/<name> and dev.
	•	Keep issues in GitHub to track MVP tasks.
	•	Minimum dev time per week: 5–10 hrs — aim for an MVP in 4–8 weeks without burning out.

⸻

# Example issues for initial dataset
	•	Pothole: title: “Huge pothole on 1st Main near X cafe”, category: “pothole”, lat/lng, image.
	•	Garbage: “Garbage heap behind Metro stop, 3 days”.
	•	Streetlight: “Light at X lane not working since 10 days”.

⸻

# Checklist to start coding (copy & paste)
	•	Create repo and add this README.
	•	Initialize Spring Boot project (web, security, data-jpa, postgresql).
	•	Configure Flyway migrations with the schema above.
	•	Wire Firebase Admin SDK and implement POST /api/auth/verify.
	•	Implement POST /api/issues and GET /api/issues.
	•	Implement vote endpoint with DB unique constraint.
	•	Add image upload to Supabase/Cloudinary.
	•	Deploy backend to Render or Cloud Run (dev branch).
	•	Build simple Expo frontend with login, listing, submit forms.
	•	Soft-launch to 10 users and iterate.

⸻

# Contacts / Credits
	•	Use this README as living doc. Keep the repo public. Credit contributors in CONTRIBUTORS.md.

⸻
