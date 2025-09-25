package org.mrbag.LinkCompresor.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public class ErrorController {

	@GetMapping(value = "/error")
	public String getMethodName(Model model) {
		return "error";
	}

}
