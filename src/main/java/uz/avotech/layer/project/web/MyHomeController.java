package uz.avotech.layer.project.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController
//@RequestMapping("/")
public class MyHomeController {
    @GetMapping
    public String get() {
        System.out.println("");
        return "dd";
    }
}
