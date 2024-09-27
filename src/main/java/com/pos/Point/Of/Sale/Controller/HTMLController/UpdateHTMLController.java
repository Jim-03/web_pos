package com.pos.Point.Of.Sale.Controller.HTMLController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UpdateHTMLController {
    @GetMapping("/update-item")
    public String updateItemHTML() {
        return "update-item";
    }
}
