package top.alexmmd.alexjwt.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.alexmmd.alexjwt.model.ResultUtils;

/**
 * @author 汪永晖
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/secure")
    public ResultUtils reachSecureEndpoint() {
        return new ResultUtils(100, "If your are reading this you reached a admin endpoint");
    }
}
