# Why DTOs when we already have Entities?

On the surface, it looks redundant — why not just send Entity objects in REST responses? But there are good reasons to keep DTOs:
    
    •	Security / Privacy: Entities often have sensitive fields (e.g., passwordHash, email) that we don’t want exposed via APIs. DTOs let us control exactly what’s returned. 
    •	API Shaping: Frontend requirements ≠ database schema. DTOs allow us to create lightweight, frontend-friendly objects (e.g., flatten nested objects, combine fields like fullName, etc.).
    •	Decoupling: Entities evolve with DB schema changes. DTOs let us change persistence without breaking APIs.
    •	Validation: DTOs are the perfect place for input validation (@NotBlank, @Email, etc.) without polluting Entities.
    •	Performance: Sometimes we fetch a subset of fields (projections). DTOs allow queries that directly populate DTOs (via JPQL / Spring Data).

Think of it like this:

    •	Entities → persistence model (for DB).
    •	DTOs → contract model (for API).
