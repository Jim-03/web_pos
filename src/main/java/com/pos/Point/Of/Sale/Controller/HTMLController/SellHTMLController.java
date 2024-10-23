package com.pos.Point.Of.Sale.Controller.HTMLController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SellHTMLController {
    @GetMapping
    public String sellHTML() {
        return "index";
    }

    @GetMapping("/sold")
    public String soldHtml() { return "sell";}
}
