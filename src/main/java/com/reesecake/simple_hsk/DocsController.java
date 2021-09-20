package com.reesecake.simple_hsk;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/docs")
public class DocsController {

    @RequestMapping
    public String docs() {
        return "docs/docs";
    }

    @RequestMapping("/about")
    public String about() {
        return "docs/docs_about";
    }

    @RequestMapping("/implementation")
    public String implementation() {
        return "docs/implementation";
    }

    @RequestMapping("/testing")
    public String testing() {
        return "docs/testing";
    }
}
