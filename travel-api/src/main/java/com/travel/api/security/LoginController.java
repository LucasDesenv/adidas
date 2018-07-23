package com.travel.api.security;

import io.jsonwebtoken.Jwts;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
/**
 * Created by lusouza on 22/07/18.
 */
@RestController
@RequestMapping(value= "/authorization", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
public class LoginController {

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public LoginResponseDTO efetuarLogin(@RequestBody CredentialDTO credential) {
        User user = findUserByCredential(credential);
        String token = createToken(user);
        return new LoginResponseDTO(token);
    }

    private String createToken(User user) {
        Map<String, Object> claims = new HashMap<String, Object>();
        claims.put("systemUser", user);
        return TokenUtils.createToken(Jwts.claims(claims));
    }

    private User findUserByCredential(CredentialDTO credential) {
        return new User(1L, "admin");
    }
}