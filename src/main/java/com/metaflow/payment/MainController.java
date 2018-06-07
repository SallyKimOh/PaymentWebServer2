package com.metaflow.payment;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

	@RequestMapping("first")                                              
    public ModelAndView practiceSpring(){                      
        ModelAndView mav = new ModelAndView("Hello");    
        String val = "HelloWorld!!";
        mav.addObject("greeting", val);                              
        return mav;                                                         
    }
	
}
