package co.kr.smart;

import java.util.HashMap;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import common.CommonService;
import member.MemberService;
import member.MemberVO;


@Controller
public class MemberController {
//	@Autowired private MemberServiceImpl service;
	@Autowired private MemberService member;
	@Autowired private CommonService common;
//	private MemberService member;
//	public MemberController(MemberService member) {
//		this.member = member;
//	}
	// 회원가입처리 요청 (@ResponseBody = 이 자체가 응답) alert 인코딩
	@ResponseBody @RequestMapping(value = "/join",produces="text/html; charset=UTF-8")
	public String join(MemberVO vo, MultipartFile profile_image ,HttpServletRequest request) {
		// 첨부된 프로필 파일이 있는 경우
		if(! profile_image.isEmpty()) {
			// 서버의 물리적 영역에 첨부 파일을 저장한 후 DB에 저장할 수 있도록 처리한다.
			vo.setProfile(common.fileUpload("profile", profile_image, request));
		}
		// 화면에서 입력한 정보를 DB에 신규저장한다.
		// 입력한 비번을 암호화 처리
		String salt = common.generateSalt();
		String userpw = common.getEncrypt(salt,vo.getUserpw());
		vo.setSalt(salt);
		vo.setUserpw(userpw);
		StringBuffer msg = new StringBuffer("<script>");
		if( member.member_join(vo) == 1) {
			msg.append("alert('회원가입을 축하합니다. ^^'); location='")
				.append(request.getContextPath())
				.append("';");
		}else {
			msg.append("alert('회원가입을 실패하셨습니다..ㅠ'); history.go(-1);");
		}
		msg.append("</script>");
		
		// 응답화면연결
		return msg.toString();
	}
		
	// 회원가입화면 요청
	@RequestMapping("/member")
	public String member(HttpSession session) {
		// 카테고리 이벤트
		session.setAttribute("caregory", "join");
		return "member/join";
	}
	
	
	private String Kakao_REST_API_KEY = "32f08fdaa5f0e5cc5cabd7bf2dfe97d7";
	
	// 카카오 로그인처리 요청
	@RequestMapping("/kakaoLogin")
	public String kakaoLogin(HttpSession session, HttpServletRequest request) {
		// Step1. 인가코드 받기
		// 		  - Request
		//		  - Response : code, state
		// https://kauth.kakao.com/oauth/authorize?response_type=code
		// &client_id=${REST_API_KEY}
		// &redirect_uri=${REDIRECT_URI}
		// REST API 키 : 32f08fdaa5f0e5cc5cabd7bf2dfe97d7	

		StringBuffer url = new StringBuffer("https://kauth.kakao.com/oauth/authorize?response_type=code");
		url.append("&client_id=").append(Kakao_REST_API_KEY);
		url.append("&redirect_uri=").append(common.appURL(request)).append("/kakaocallback");
		request.setAttribute("url", url.toString());

		// 소셜 로그인 동의항목 재동의 요청
		url.append("&auth_type=reprompt");
		return "redirect:" + url.toString(); // /kakaocallback
	}
	
	// 카카오 콜백 처리
	@RequestMapping("/kakaocallback")
	public String kakaocallback(String code, HttpSession sessioin) {
		// Step1. 인가코드 받기
		//		  - Request
		// 		  - Response : code
		if( code==null ) {
			return "redirect:/";
		}
		
		StringBuffer url = new StringBuffer(
				"https://kauth.kakao.com/oauth/token?grant_type=authorization_code");
			url.append("&client_id=")
				.append(Kakao_REST_API_KEY);
			url.append("&code=").append(code);
		
		// Step2. 토큰 받기
		// curl -v -X POST "https://kauth.kakao.com/oauth/token" \
		//  -H "Content-Type: application/x-www-form-urlencoded" \
		//  -d "grant_type=authorization_code" \
		//  -d "client_id=${REST_API_KEY}" \
		//  --data-urlencode "redirect_uri=${REDIRECT_URI}" \
		//  -d "code=${AUTHORIZE_CODE}"	
			
		//String tokens = CommonUtility.requestAPI(url.toString());
		//JSONObject json = new JSONObject(tokens);
		//String token_type = json.getString("token_type");
		//String access_token = json.getString("access_token");
		
		return "redirect:/";
	}
	
	private String NaverClientId = "ZoxO0ftx00JuKNVrs8uv";
	private String NaverClinentSecret = "DcyduAhpqN";
	
	// 네이버 로그인처리 요청
	@RequestMapping("/naverLogin")
	public String naverLogin(HttpSession session, HttpServletRequest request) {
		// 3.4.2 네이버 로그인 연동 URL 생성하기
		// https://nid.naver.com/oauth2.0/authorize?response_type=code
		// &client_id=CLIENT_ID
		// &state=STATE_STRING
		// &redirect_uri=CALLBACK_URL
		String state = UUID.randomUUID().toString();
		session.setAttribute("state", state);
		
		StringBuffer url = new StringBuffer(
				"https://nid.naver.com/oauth2.0/authorize?response_type=code");
		url.append("&client_id=").append(NaverClientId);
		url.append("&state=").append(state);
		url.append("&redirect_uri=").append(common.appURL(request)).append("/navercallback");
		
		// 동의항목 재동의 요청
		url.append("&auth_type=reprompt");
		return "redirect:" + url.toString(); // /navercallback
	}
	
	// 네이버 콜백 처리
	@RequestMapping("/navercallback")
	public String navercallback(String code, String state, HttpSession sessioin) {
		// 3.4.3 네이버 로그인 연동 결과 Callback 정보
		// API 요청 성공시 : http://콜백URL/redirect?code={code값}&state={state값}
		// API 요청 실패시 : http://콜백URL/redirect?state={state값}&error={에러코드값}&error_description={에러메시지}
		String sState = (String)sessioin.getAttribute("state");
		if( code==null || !state.equals(sState) ) {
			return "redirect:/";
		}
		// Callback으로 전달받은 'code' 값을 이용하여 '접근토큰발급API'를 호출
		// https://nid.naver.com/oauth2.0/token?grant_type=authorization_code
		// &client_id=jyvqXeaVOVmV
		// &client_secret=527300A0_COq1_XV33cf
		// &code=EIc5bFrl4RibFls1
		// &state=9kgsGTfH4j7IyAkg
		StringBuffer url = new StringBuffer(
				"https://nid.naver.com/oauth2.0/token?grant_type=authorization_code");
		url.append("&client_id=").append(NaverClientId);
		url.append("&client_secret=").append(NaverClinentSecret);
		url.append("&code=").append(code);
		url.append("&state=").append(state);
		
		// 로그인 API 명세 - 2. callback.jsp
		String response = common.requestAPI(url.toString());
		JSONObject json = new JSONObject(response);
		String token = json.getString("access_token");
		String type = json.getString("token_type");
		
		// 3.4.5 접근 토큰을 이용하여 프로필 API 호출하기
		//curl  -XGET "https://openapi.naver.com/v1/nid/me" \
	    //-H "Authorization: Bearer AAAAPIuf0L+qfDkMABQ3IJ8heq2mlw71DojBj3oc2Z6OxMQESVSrtR0dbvsiQbPbP1/cxva23n7mQShtfK4pchdk/rc="
		// Authorization : {토큰 타입} {접근 토큰}
		response = common.requestAPI("https://openapi.naver.com/v1/nid/me", type + " " + token);
		json = new JSONObject(response);
		
		// API 호출 결과코드가 정상(00)인 경우 프로필 정보에 접근
		if(json.getString("resultcode").equals("00")) {
			MemberVO vo = new MemberVO();
			vo.setSocial("N");
			
			json = json.getJSONObject("response");
			vo.setUserid(json.getString("id"));
			/*
			vo.setName(json.has("nickname") ? json.getString("nickname") : "");
			if(vo.getName().isEmpty()) {
				vo.setName(json.has("name") ? json.getString("name") : "이름없음");
			}
			vo.setEmail(json.has("email") ? json.getString("email") : "");
			*/
			vo.setName(jsonValue("nickname", json));
			if(vo.getName().isEmpty()) {
				vo.setName(jsonValue("name", json,"이름없음"));
			}
			vo.setEmail(jsonValue("email", json));
			vo.setGender(jsonValue("gender", json, "M").equals("M") ? "남" : "여");
			vo.setProfile(jsonValue("profile_image", json));
			vo.setPhone(jsonValue("mobile", json));
			/* vo.setBirth(jsonValue("birthday", json)); */
			
			if(member.member_idCheck(vo.getUserid())==1) { //update
				member.member_myInfo_update(vo);
			}else { // insert
				member.member_join(vo);
			}
			// 소셜 로그인되게 세션에 로그인 정보를 담는다.
			sessioin.setAttribute("loginInfo", vo);
		}
		
		return "redirect:/";
	}
	
	// jsonValue 판단 메소드 : (json.has("nickname") ? json.getString("nickname") : "");
	private String jsonValue(String key, JSONObject json) {
		return json.has(key) ? json.getString(key) : "";
	}
	private String jsonValue(String key, JSONObject json, String defaultValue) {
		return json.has(key) ? json.getString(key) : defaultValue;
	}
	
	// 아이디 중복확인 요청 (@ResponseBody : 이 자체가 응답이다. 화면 응답 아님.)
	@ResponseBody @RequestMapping("/idCheck")
	public boolean idCheck(String id) {
		// 비지니스 로직
		// 화면에서 입력한 아이디가 DB에 존재하는지 확인 : 0이면 아이디가 존재x, 1이면 아이디가 존재o
		return member.member_idCheck(id) == 0 ? false : true;
	}
	
	
	// 비밀번호 변경 처리 요청
	@RequestMapping("/changePassword")
	public String changePassword(String userpw, HttpSession session) {
		// 비지니스 로직
		// 화면에서 입력한 비밀번호로 DB에 변경저장한다.
		// 기존의 salt(로그인 정보)를 사용해서 새로입력한 비번을 암호화한다.
		MemberVO vo = (MemberVO)session.getAttribute("loginInfo");
		userpw = common.getEncrypt(vo.getSalt(), userpw);
		vo.setUserpw(userpw);
		
		// DB에 변경저장한다.
		member.member_password_update(vo);
		// 세션에 담긴 loginInfo를 변경해준다. 
		session.setAttribute("loginInfo", vo);
		
		// 응답화면 연결
		return "redirect:/";
	}
	
	// 비밀번호 변경 화면 요청
	@RequestMapping("/changePW")
	public String changePW() {
		return "member/change";
	}
	
	//비밀번호 재발급처리 요청 - 이 자체가 응답(@ResponseBody)
	@ResponseBody @RequestMapping(value = "/reset", produces = "text/html; charset = UTF-8")
	public String reset(MemberVO vo) {
		// 비지니스로직
		// 화면에서 입력한 아이디/이메일이 일치하는 회원에게
		// 임시 비번을 발급해준다.
		// 임시 비번을 생성한 후 회원정보에 변경저장하고, 임시 비번을 회원에게 이메일로 알려준다.
		String name = member.member_userid_email(vo);
		if(name == null) {
			StringBuffer msg = new StringBuffer("<script>");	
			msg.append("alert('아이디나 이메일이 맞지 않습니다. \\n확인하세요!');");
			msg.append("history.go(-1);");  // 이전 화면으로 이동
			msg.append("</script>");
			return msg.toString();
		}
		// 임시 비번을 생성
		String pw = UUID.randomUUID().toString(); //afsfisf-safsaff-safsbion12
		pw = pw.substring(pw.lastIndexOf("-")+1); //safsbion12
		
		// 임시 비번 암호화
		String salt = common.generateSalt();
		vo.setSalt(salt);
		vo.setUserpw(common.getEncrypt(salt, pw));
		vo.setName(name);
		
		// 회원정보에 변경저장 + 이메일 보내는 처리
		StringBuffer msg = new StringBuffer("<script>");
		if(member.member_password_update(vo) == 1 && common.sendPassword(vo, pw)) {
			msg.append("alert('임시 비밀번호가 발급되었습니다. \\n이메일을 확인하세요.');");
			msg.append("location='login';"); // 임시 비번으로 로그인하도록, 로그인 화면으로 이동
		}else {
			msg.append("alert('임시 비밀번호 발급이 실패하였습니다...');");
			msg.append("history.go(-1);"); // 이전 페이지로 이동
		}
		msg.append("</script>");
		
		return msg.toString();
	}
	
	// 비밀번호 찾기 화면 요청 - 비밀번호 재발급 (임시비번발급) 화면
	@RequestMapping("/find")
	public String find() {
		return "default/member/find";
	}
	
	// 로그인처리 요청
	@ResponseBody @RequestMapping("/smartLogin")
	public boolean login(String id, String pw, HttpSession session) {
		// 비지니스로직
		// 화면에서 입력한 아이디/비먼이 일치하는 회원정보를 DB에서 조회해온
		String salt = member.member_salt(id);
		// slat를 사용해 화면에서 입력한 비번을 암호화 한다.
		pw = common.getEncrypt(salt, pw);
		HashMap<String,String> map = new HashMap<String, String>();
		map.put("id", id);
		map.put("pw", pw);
		MemberVO vo = member.member_login(map);
		// 화면에 출력할 수 있도록 세션에 attribute로 담는다.
		session.setAttribute("loginInfo", vo);
		
		return vo == null ? false : true;
	}
	
	// 로그인화면 요청
	@RequestMapping("/login")
	public String login(HttpSession session) {
		session.setAttribute("category", "login");
		return "default/member/login";
	}
	
	// 로그아웃화면 요청
	@RequestMapping("/logout")
	public String loginout(HttpSession session) {
		// 비지니스로직
		// 세션에 있는 로그인 정보를 삭제한다.
		session.removeAttribute("loginInfo");
		
		// 응답화면 연결
		return "redirect:/";
	}
}