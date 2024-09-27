package com.pos.Point.Of.Sale.Controller.HTMLController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InventoryHTMLController {
    @GetMapping("/inventory")
    public String checkInventoryHTML() {
        return "inventory";
    }
}
