Sure! Let's walk through the **complete execution flow** of your Spring Boot + JWT application — **step by step**, with
which file is involved at each stage.

---

## ✅ FLOW OF APPLICATION (Request → Response)

We'll break it into **two scenarios**:

---

### **1. LOGIN FLOW (generate token)**

**Client calls: `POST /auth/login`**

---

#### 🔸 Step 1: Request hits `AuthController`

📄 `AuthController.java`

```java

@PostMapping("/login")
public ResponseEntity<Map<String, String>> login(@RequestBody AuthRequest request)
```

* Request body is deserialized into `AuthRequest`
* Controller calls `AuthenticationManager.authenticate(...)` to verify username/password

---

#### 🔸 Step 2: Authenticate user

📄 `AuthenticationManager` + `UserDetailsService` from `UserConfig.java`

* `UserDetailsService` loads the user (in-memory or from DB)
* Password is checked using `PasswordEncoder`

---

#### 🔸 Step 3: If valid, generate JWT

📄 `JwtService.java`

```java
String token = jwtService.generateToken(user);
```

* A signed JWT is generated using:

    * `subject = username`
    * `issuedAt`
    * `expiration`
    * `secret key + signature`

---

#### 🔸 Step 4: Token sent in response

📄 `AuthController.java`

* Response:

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

---

### ✅ Client stores this token and uses it in future API calls.

---

---

### **2. PROTECTED API FLOW (like `GET /user/profile`)**

---

#### 🔸 Step 1: Request enters application

* User sends request to `GET /user/profile`
* With Header: `Authorization: Bearer <JWT>`

---

#### 🔸 Step 2: Hits `JwtAuthFilter` first

📄 `JwtAuthFilter.java`
(`OncePerRequestFilter` added in `SecurityConfig.java`)

```java

@Override
protected void doFilterInternal(HttpServletRequest request, ...)
```

* Checks for `Authorization` header
* Extracts token from header
* Calls `jwtService.extractUsername(token)`

---

#### 🔸 Step 3: Validate token

📄 `JwtService.java`

* Parses token
* Checks:

    * Token signature is valid
    * Not expired
    * Matches user

---

#### 🔸 Step 4: Set Authentication Context

📄 `JwtAuthFilter.java`

```java
UsernamePasswordAuthenticationToken authToken =
        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
SecurityContextHolder.

getContext().

setAuthentication(authToken);
```

Now Spring Security knows: ✅ User is authenticated.

---

#### 🔸 Step 5: Request reaches actual controller

📄 Example: `UserController.java`

```java

@GetMapping("/user/profile")
public ResponseEntity<User> getProfile() {
    // This method is now allowed
}
```

* Only now, the controller gets executed.

---

#### 🔸 Step 6: Response goes back

* Controller returns JSON/data as normal
* Token is not reissued here; client keeps using existing one

---

## 🔁 Summary of Component Order

### 🔐 Login Flow (`/auth/login`)

```
Controller:     AuthController
   ↓
Bean:           AuthenticationManager
   ↓
Service:        JwtService (generate token)
   ↓
Return:         Token JSON
```

---

### 🔒 Secure API Flow (e.g., `/user/profile`)

```
Filter:         JwtAuthFilter
   ↓
Service:        JwtService (validate token)
   ↓
Bean:           UserDetailsService (load user)
   ↓
Spring Security sets context
   ↓
Controller:     Your actual business controller
```

---

### 🧠 KEY FILES RECAP

| File                           | Purpose                                        |
|--------------------------------|------------------------------------------------|
| `SecurityConfig.java`          | Declares filter chain and route protection     |
| `JwtService.java`              | Token generation, parsing, validation          |
| `JwtAuthFilter.java`           | Intercepts and validates token in all requests |
| `AuthController.java`          | Handles login endpoint                         |
| `UserConfig.java`              | In-memory user + authentication manager        |
| `UserController.java` (custom) | Your actual business API                       |

---

Would you like me to draw this in a visual diagram as well?

Also, I can show the same flow with **DB-backed user authentication** or **role-based security** if you want.
