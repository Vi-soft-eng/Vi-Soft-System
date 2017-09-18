package com.visoft.controller;

import com.visoft.exception.RestException;
import com.visoft.google.auth.GoogleAuthHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.websocket.server.PathParam;


@Controller
@RequestMapping(value = "googleAuth")
public class GoogleAuthController extends ExceptionHandlerController {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    GoogleAuthHelper googleAuthHelper;

    @GetMapping("/OAuth2v1")
    public @ResponseBody
    ResponseEntity<String> auth(@PathParam (value = "code") String code) throws RestException {
        String googleUserInfo;
        try {
            googleUserInfo = googleAuthHelper.getUserInfoJson(code);
        } catch (Exception ex) {
            LOG.error("Error: " + ex.getMessage());
            throw new RestException("Error: " + ex.getMessage());
        }
        final HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(googleUserInfo, headers, HttpStatus.OK);
    }

    @GetMapping("/getUrlToAuth")
    public @ResponseBody
    ResponseEntity<String> auth_rest() throws RestException {

        String googleAuthUrl;

        try {
            googleAuthUrl = googleAuthHelper.buildLoginUrl();
        } catch (Exception ex) {
            LOG.error("Error: " + ex.getMessage());
            throw new RestException("Error: " + ex.getMessage());
        }

        final HttpHeaders headers = new HttpHeaders();

        return new ResponseEntity<>("<a href='" + googleAuthUrl + "'>Login with Google</a>", headers, HttpStatus.OK);
    }

}
