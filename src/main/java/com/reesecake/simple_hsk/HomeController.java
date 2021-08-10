package com.reesecake.simple_hsk;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @RequestMapping(value = "/")
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/about")
    public String about() {
        return "about";
    }

    @RequestMapping(value = "/hsk/{level_id}", method = RequestMethod.GET)
    @ResponseBody
    public String hsk(
        @PathVariable("level_id") long level_id) {
            return "hsk" + level_id;
    }

}
