# 📖 PuneKar Entities – README

# Overview

This document describes the core entities, their relationships, and key attributes for the PuneKar Ward Problem Tracking App MVP.
The schema is designed for PostgreSQL (RDBMS) with scalability and future extensibility in mind.

⸻

# 👤 User

Represents a citizen using the app.

    Fields:
    •	id (PK)
    •	fullName
    •	username (unique, public-facing; chosen by user)
    •	email (unique; used for login/verification)
    •	phoneNumber
    •	passwordHash (or external Firebase UID if OAuth is used)
    •	trustScore (numeric, for anti-troll mechanisms 🚧 future)
    •	createdAt
    •	updatedAt

Notes:
    
    •	Full name stored internally but not exposed publicly.
    •	Username enables a balance of anonymity + accountability.

⸻

# 🏙️ Ward

Represents a municipal ward/zone in Pune.

    Fields:
    •	id (PK)
    •	name
    •	zone (optional)
    •	createdAt

⸻

# 📌 Issue

Represents a civic problem reported by a user.

    Fields:
    •	id (PK)
    •	title
    •	description
    •	status (OPEN, IN_PROGRESS, RESOLVED, REJECTED)
    •	category (Roads, Garbage, Water, Streetlights, etc.)
    •	location (lat/long or textual address)
    •	wardId (FK → Ward)
    •	userId (FK → User)
    •	createdAt
    •	updatedAt

Notes:

    •	Issues can have multiple images (before/after evidence).
    •	Issues can receive votes and comments.

⸻

# 🖼️ IssueImage

Stores images related to issues.

    Fields:
    •	id (PK)
    •	issueId (FK → Issue)
    •	imageUrl
    •	imageType (BEFORE / AFTER)
    •	uploadedBy (FK → User)
    •	uploadedAt

⸻

# 👍👎 Vote

Tracks upvotes/downvotes for both issues and comments.

    Fields:
    •	id (PK)
    •	targetType (ISSUE / COMMENT)
    •	targetId (FK → Issue OR Comment)
    •	userId (FK → User)
    •	voteType (UPVOTE / DOWNVOTE)
    •	createdAt

Notes:

    •	Unique constraint on (userId, targetType, targetId) to prevent duplicate voting.

⸻

# 💬 Comment

Allows discussion on issues, including replies.

    Fields:
    •	id (PK)
    •	issueId (FK → Issue)
    •	userId (FK → User)
    •	parentCommentId (nullable, FK → Comment) → enables nested replies
    •	content
    •	createdAt

Notes:

    •	Comments can also be upvoted/downvoted.

⸻

# Verification
Tracks community votes to verify issue resolution.

    Fields:
    • id (PK)
    • issueId (FK → Issue)
    • userId (FK → User)
    • imageUrl (nullable, links to AFTER image in IssueImage)
    • note (TEXT, optional comment)
    • createdAt

Notes:

    • Requires 2+ verifications to mark Issue.status = RESOLVED (configurable).
    • Unique constraint on (issueId, userId) to prevent duplicate votes.

# 🔗 Relationships
    •	User → Issue (1:N)
    •	User → Comment (1:N)
    •	Issue → IssueImage (1:N)
    •	Issue → Comment (1:N, nested via parentCommentId)
    •	Vote → Issue / Comment (polymorphic reference)
