package com.reesecake.simple_hsk.security;

import com.reesecake.simple_hsk.security.alternative.AppUser;
import com.reesecake.simple_hsk.security.alternative.AppUserRepository;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.rmi.ServerException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Controller
@RequestMapping("/auth")
public class SecurityController {

    @Autowired
    private AppUserRepository appUserRepository;
    private Logger logger = Logger.getLogger( getClass().getName() );

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/register")
    public String register() {
        return "login";
    }

    @PostMapping(path = "/register/process",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody JSONObject newUser) {
        logger.info( "Processing registration form for: " + newUser );
        AppUser user;
        try {
            user = appUserRepository.save(new AppUser(newUser.getAsString("username"), newUser.getAsString("password"), "ROLE_STUDENT"));
        } catch (final DataIntegrityViolationException e) {
            // https://stackoverflow.com/questions/65862179/check-for-duplicate-usernames-prevents-updating-user
            // https://stackoverflow.com/questions/40765010/how-to-return-json-with-multiple-properties-by-using-responseentity-in-spring-re
            // https://stackoverflow.com/questions/56049532/this-email-already-exists-validation

            Map<String, String> msgBody = new HashMap<>();
            msgBody.put("error", "Already exists");

            return new ResponseEntity<>(msgBody, HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

//    https://stackoverflow.com/questions/32244745/how-to-add-new-user-to-spring-security-at-runtime
//
//    @PostMapping( "/register/processRegistrationForm" )
//    public String processRegistrationForm(@Valid @ModelAttribute( "user" ) com.exmaple.spring_demo.entity.User user, BindingResult theBindingResult, Model theModel )
//    {
//
//        String userName = user.getUsername();
//
//        logger.info( "Processing registration form for: " + userName );
//
//        // form validation
//        if ( theBindingResult.hasErrors() )
//        {
//
//            theModel.addAttribute( "user", new com.exmaple.spring_demo.entity.User() );
//            theModel.addAttribute( "registrationError", "User name/password can not be empty." );
//
//            logger.warning( "User name/password can not be empty." );
//
//            return "security/user/registration-form";
//        }
//
//        // check the database if user already exists
//        boolean userExists = doesUserExist( userName );
//
//        if ( userExists )
//        {
//            theModel.addAttribute( "user", new com.exmaple.spring_demo.entity.User() );
//            theModel.addAttribute( "registrationError", "User name already exists." );
//
//            logger.warning( "User name already exists." );
//
//            return "security/user/registration-form";
//        }
//
//        //
//        // whew ... we passed all of the validation checks!
//        // let's get down to business!!!
//        //
//
//        // encrypt the password
//        String encodedPassword = passwordEncoder.encode( user.getPassword() );
//
//        // prepend the encoding algorithm id
//        encodedPassword = "{bcrypt}" + encodedPassword;
//
//        // give user default role of "ROLE_USER"
//        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList( "ROLE_USER" );
//
//        // create user object (from Spring Security framework)
//        User tempUser = new User( userName, encodedPassword, authorities );
//
//        // save user in the database
//        userDetailsManager.createUser( tempUser );
//
//        logger.info( "Successfully created user: " + userName );
//
//        return "security/user/registration-confirmation";
//    }
}
