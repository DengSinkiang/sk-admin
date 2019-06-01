package com.dxj.tools.rest;

import com.dxj.tools.domain.VerificationCode;
import com.dxj.tools.domain.vo.EmailVo;
import com.dxj.tools.service.EmailService;
import com.dxj.tools.service.VerificationCodeService;
import com.dxj.common.util.ElAdminConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

/**
 * @author jie
 * @date 2018-12-26
 */
@RestController
@RequestMapping("api")
public class VerificationCodeController {

    private final VerificationCodeService verificationCodeService;

    private final UserDetailsService userDetailsService;

    private final EmailService emailService;

    @Autowired
    public VerificationCodeController(VerificationCodeService verificationCodeService, @Qualifier("jwtUserDetailsService") UserDetailsService userDetailsService, EmailService emailService) {
        this.verificationCodeService = verificationCodeService;
        this.userDetailsService = userDetailsService;
        this.emailService = emailService;
    }

    @PostMapping(value = "/code/resetEmail")
    public ResponseEntity<Void> resetEmail(@RequestBody VerificationCode code) {
        sendEmail(code);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void sendEmail(@RequestBody VerificationCode code) {
        code.setScenes(ElAdminConstant.RESET_MAIL);
        EmailVo emailVo = verificationCodeService.sendEmail(code);
        emailService.send(emailVo, emailService.find());
    }

    @PostMapping(value = "/code/email/resetPass")
    public ResponseEntity<Void> resetPass(@RequestParam String email) {
        VerificationCode code = new VerificationCode();
        code.setType("email");
        code.setValue(email);
        sendEmail(code);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/code/validated")
    public ResponseEntity<Void> validated(VerificationCode code){
        verificationCodeService.validated(code);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
