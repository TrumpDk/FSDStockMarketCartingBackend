package com.ibm.fsd.smc.ms.user.controller;

import com.ibm.fsd.smc.ms.user.domain.User;
import com.ibm.fsd.smc.ms.user.service.MailService;
import com.ibm.fsd.smc.ms.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用于管理Controller
 */
@CrossOrigin
@RestController
//@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userSvc;
    @Autowired
    private MailService mailSvc;

    @GetMapping("/users")
    public List<User> findAll() {
        return userSvc.findAll();
    }

    @GetMapping("/{login-user-name}")
    public User findUserByLoginUserName(@PathVariable("login-user-name") String loginUserName) {
        return userSvc.findByLoginName(loginUserName);
    }

    @PostMapping("/register")
    public ResponseEntity<Boolean> register(@RequestBody User user){
        Boolean result = Boolean.FALSE;
        log.info("user = " + user);
        if(userSvc.isLoginNameExist(user.getLoginName())){
            return new ResponseEntity("The User Login Name is already Exist!",HttpStatus.EXPECTATION_FAILED);
        }
        userSvc.save(user);
        try {
            mailSvc.sendSimpleMail(user.getEmail(),"Sign up SMC verify!","this is a verify mail from SMC sign up!");
        }catch (Exception e) {
            log.error(e.getMessage());
        }

        return new ResponseEntity(result, HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<Boolean> updateUser(@RequestBody User user){
        Boolean result = Boolean.FALSE;
        User dbUser = userSvc.findByLoginName(user.getLoginName());
        if(!ObjectUtils.isEmpty(dbUser)){
            user.setId(dbUser.getId());
            userSvc.save(user);
            result = Boolean.TRUE;
        }
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<User> Login(@RequestBody User user) {
        log.info("login user info ===",user);
        User result = null;
        User dbUser = userSvc.findByLoginName(user.getLoginName());
        if(!ObjectUtils.isEmpty(dbUser)){
            if(dbUser.getPassword().equals(user.getPassword())){
                result = dbUser;
            }
        }
        return new ResponseEntity(result, HttpStatus.OK);
    }
}
