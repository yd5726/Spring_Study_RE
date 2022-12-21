package com.and.middle;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

//@Controller : 대부분 요청을 받아서 페이지 전환을 하기 위한 것(Ajax 비동기 처리 또는 데이터만 필요할 때)
//@RestController : 데이터만 필요한 경우 사용하는 컨트롤러(@RequestBody 생락 가능)  
@RestController
public class ExamController {
	
	// 크롬 => 톰캣 => 컨트롤러
	@RequestMapping(value = "/test1", produces="text/html;charset=UTF-8")
	public void test1() {
		System.out.println("Spring Console - 요청 받음1");
	}
	
	@RequestMapping(value = "/test2", produces="text/html;charset=UTF-8")
	public void test2(String id, String pw, String email) {
		System.out.println("Spring Console - 요청 받음2");
		// 요청할 때 요청하는 쪽에서 보내주는 데이터 출력
		System.out.println(id);
		System.out.println(pw);
		System.out.println(email);
	}
	
	@RequestMapping(value = "/test3", produces="text/html;charset=UTF-8")
	public String test3() {
		System.out.println("Spring Console - 요청 받음3");
		return "kmj";
	}
	
	@RequestMapping(value = "/test4", produces="text/html;charset=UTF-8")
	public String test4() {
		System.out.println("Spring Console - 요청 받음4");
		TestVO vo = new TestVO();
		vo.setiVal(0);
		vo.setdVal(1.3);
		vo.setsVal("str");
		return new Gson().toJson(vo);
	}
	
	@RequestMapping(value = "/test5", produces="text/html;charset=UTF-8")
	public String test5(TestVO vo) {
		System.out.println("Spring Console - 요청 받음5");
		ArrayList<TestVO> list = new ArrayList<TestVO>();
		//list.add(new TestVO());
		/*
		for (int i = 0; i < 10; i++) {
			TestVO vo2 = new TestVO();
			vo2.setiVal(0);
			vo2.setdVal(1.3);
			vo2.setsVal("str");
			list.add(vo2);
		}
		*/
		for (int i = 0; i < 10; i++) {
			list.add(new TestVO(0,"str",1.3));
		}
		
		return new Gson().toJson(list);
	}
}
