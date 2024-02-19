package com.quokkatech.foodtruckmanagement.api.controllers;

import com.quokkatech.foodtruckmanagement.api.dto.UserSessionDTO;
import com.quokkatech.foodtruckmanagement.application.exceptions.UsernameDoesNotExistException;
import com.quokkatech.foodtruckmanagement.application.services.UserSessionService;
import com.quokkatech.foodtruckmanagement.domain.entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/userSessions")
public class UserSessionController {

    private static final Logger logger = LoggerFactory.getLogger(UserSessionController.class);
    private final UserSessionService userSessionService;

    public UserSessionController(UserSessionService userSessionService){
        this.userSessionService=userSessionService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> findByUserId(@PathVariable Long userId) {
        logger.info("UserController.findById - Finding user with ID: {}", userId);
        Optional<User> userOptional = userSessionService.findById(userId);

        return userOptional
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("username/{username}")
    public ResponseEntity<User> findByUsername(@PathVariable ("username") String username){
        logger.info("UserController.findByUsername - Finding user with username: {}", username);
        Optional<User> userOptional = userSessionService.findByUsername(username);
        return userOptional
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public ResponseEntity<UserSessionDTO> createUserSession(@RequestBody UserSessionDTO userSessionDTO) {
        try {
            User user = new User();
            user.setUsername(userSessionDTO.getUsername());
            user.setPassword(userSessionDTO.getPassword());
            user.setRole(userSessionDTO.getRole());
            user.setCompany(userSessionDTO.getCompany());

            var token = userSessionService.createUserSession(user);

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("Authentication", token);
            userSessionDTO.setPassword("**********");
            return ResponseEntity.status(HttpStatus.CREATED).headers(httpHeaders).body(userSessionDTO);
        } catch (UsernameDoesNotExistException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userSessionDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(userSessionDTO);
        }
    }
/*    @GetMapping()
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> userList = userService.findAllUsers();
        return ResponseEntity.ok(List.of(userList.toArray(new User[0])));
    }
*/

}