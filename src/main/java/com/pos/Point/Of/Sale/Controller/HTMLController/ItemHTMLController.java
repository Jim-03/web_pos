package com.pos.Point.Of.Sale.Controller.HTMLController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/item")
public class ItemHTMLController {
    @GetMapping("/add")
    public String addItem() {
        return "new-item";
    }
}
