package com.example.shabushabu.controller;

import com.example.shabushabu.pojo.Account;
import com.example.shabushabu.repository.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AccountController {
    @Autowired
    private LoginService accountService;

    @RequestMapping(value = "/userLogin", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody MultiValueMap<String, String> formData) {
        MultiValueMap<String, String> sendBack = new LinkedMultiValueMap<>();
        Map<String, String> d = formData.toSingleValueMap();
        String username = d.get("username");
        String password = d.get("password");
        try {
            Account login = accountService.login(username,password);
            System.out.println(login);
            if (login == null) { // กรณีรหัสผ่านผิด ไม่มี user นี้ในระบบ (ค่า null)
                sendBack.add("status", "false");
            }else {
                sendBack.add("status", "true");
                sendBack.add("ses_id", login.get_id());
                sendBack.add("ses_user", login.getUsername());
                sendBack.add("ses_role", login.getRole());
                sendBack.add("tableNo",login.getTableNo()+"");
            }
        } catch (Exception e){
            throw e;
        }
        return ResponseEntity.ok(sendBack);
    }
}
