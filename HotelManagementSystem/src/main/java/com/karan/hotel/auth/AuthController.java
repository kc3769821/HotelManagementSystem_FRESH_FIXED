package com.karan.hotel.auth;

import com.karan.hotel.entity.User;
import com.karan.hotel.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController @RequestMapping("/api/auth")
public class AuthController {
    private final UserRepository users;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    public AuthController(UserRepository users){this.users=users;}

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Map<String,String> p, HttpSession session){
        String name=p.getOrDefault("fullName","").trim(), email=p.getOrDefault("email","").trim().toLowerCase(), password=p.getOrDefault("password","");
        if(name.isBlank() || email.isBlank() || password.length()<6) return ResponseEntity.badRequest().body(Map.of("error","Name, valid email and password (minimum 6 characters) are required."));
        if(users.existsByEmailIgnoreCase(email)) return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error","An account with this email already exists."));
        User u=new User(); u.setFullName(name); u.setEmail(email); u.setPhone(p.get("phone")); u.setPasswordHash(encoder.encode(password));
        users.save(u); session.setAttribute("USER_ID",u.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message","Account created successfully","user",u));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String,String> p, HttpSession session){
        String email=p.getOrDefault("email","").trim().toLowerCase(), password=p.getOrDefault("password","");
        return users.findByEmailIgnoreCase(email).filter(u->encoder.matches(password,u.getPasswordHash())).map(u->{session.setAttribute("USER_ID",u.getId()); return ResponseEntity.ok(Map.of("message","Login successful","user",u));}).orElseGet(()->ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error","Invalid email or password.")));
    }

    @GetMapping("/me") public ResponseEntity<?> me(HttpSession session){
        Object id=session.getAttribute("USER_ID"); if(id==null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("authenticated",false));
        return users.findById((Long)id).map(u->ResponseEntity.ok(Map.of("authenticated",true,"user",u))).orElseGet(()->ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("authenticated",false)));
    }
    @PostMapping("/logout") public Map<String,String> logout(HttpSession session){session.invalidate(); return Map.of("message","Logged out successfully");}
}
