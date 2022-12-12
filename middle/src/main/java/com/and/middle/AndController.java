package com.and.middle;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

//@Controller
@RestController
public class AndController {
	
	//@ResponseBody <= RestAPI 방식은 데이터를 return하기 위한 방식이기 때문에
	//@ResponseBody를 생략해도 똑같은 처리가 된다.
	@RequestMapping("/and")
	public String andController() {
		return "test";
	}
}