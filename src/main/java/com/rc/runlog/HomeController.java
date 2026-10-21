package com.rc.runlog;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class HomeController {

    @GetMapping("/")
    public String home(){
        return "RunLog is Up and running!!";
    }
}
