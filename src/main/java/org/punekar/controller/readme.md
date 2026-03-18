# 📖 Controllers README — PuneKar Backend (Spring Boot)

# 1. AuthController

Handles Firebase token verification and mapping users into our DB.
 
    •	POST /api/auth/verify
    •	Request: { "idToken": "<firebase_id_token>" }
    •	Response: UserDTO (with app user ID, displayName, email, ward, createdAt)
    •	Notes: Creates a new user if not already present.

⸻

# 2. UserController

Handles profile fetch and minimal updates.

    •	GET /api/users/me
    •	Auth: required
    •	Response: UserDTO
    =================================================
    •	PATCH /api/users/me
    •	Body: { "displayName": "optional", "ward": "optional" }
    •	Response: updated UserDTO

⸻

# 3. IssueController

Core of the app: report + view ward issues.

    •	GET /api/issues
    •	Params: ward, sort=top|new, category?, status?
    •	Response: List<IssueDTO>
    •	POST /api/issues
    •	Auth: required
    •	Body:

            {
            "title": "Huge pothole near X",
            "description": "Dangerous at night",
            "category": "pothole",
            "latitude": 18.5601,
            "longitude": 73.9042,
            "images": ["base64 or presigned URLs"]
            }
	•	Response: IssueDTO
    =================================================
	•	GET /api/issues/{id}
	•	Response: IssueDetailDTO (issue + images + comments + votes)
	•	PATCH /api/issues/{id}
	•	Auth: moderator/admin only
	•	Body: { "status": "IN_PROGRESS|RESOLVED|REJECTED" }
	•	Response: updated IssueDTO

⸻

# 4. VoteController

Covers both issue votes and comment/reply votes.

    •	POST /api/issues/{id}/vote
    •	Auth: required
    •	Body: { "type": "UP|DOWN" }
    •	Response: { "votes": { "up": 12, "down": 2 } }
    =================================================
    •	DELETE /api/issues/{id}/vote
    •	Removes user vote
    =================================================
    •	POST /api/comments/{id}/vote
    •	Same schema as issue vote
    •	Response: { "votes": { "up": 5, "down": 1 } }

⸻

# 5. CommentController

       •	POST /api/issues/{id}/comments
       •	Auth: required
       •	Body: { "content": "This is bad!", "parentCommentId": null }
       •	Response: CommentDTO
       =================================================
       •	GET /api/issues/{id}/comments
       •	Response: list of comments + replies (nested)
       =================================================
       •	DELETE /api/comments/{id}
       •	Auth: author or moderator

⸻

# 6. VerificationController
   
    •	POST /api/issues/{id}/verifications
    •	Auth: required
    •	Body: { "note": "Looks fixed now", "imageUrl": "..." }
    •	Response: VerificationDTO
    •	Rules: Only 1 verification per user per issue.

⸻

# 7. DashboardController (optional MVP but useful)
   
    1.	GET /api/dashboard/summary
    •	Response:

                {
                "totalIssues": 100,
                "resolved": 40,
                "pending": 60,
                "topCategories": ["pothole", "garbage"]
                }
    ====================================================

	2.	GET /api/dashboard/exports/issues.csv
	•	Public CSV export

⸻

🔑 Notes on Auth & Roles

    •	All POST/PATCH/DELETE require auth.
    •	Moderation endpoints (status changes, delete) restricted to ward moderators.
    •	Basic role model: USER, MODERATOR, ADMIN.


⸻
