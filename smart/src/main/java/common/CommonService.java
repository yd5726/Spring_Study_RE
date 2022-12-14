package common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.springframework.stereotype.Service;

@Service
public class CommonService {
	// 솔트 생성 메소드 - 목적 : 암호화
	public String generateSalt() {
		// 암호화 랜덤값 생성 객체
		SecureRandom secure = new SecureRandom();
		byte bytes[] = new byte[24];
		secure.nextBytes(bytes);
		
		// 1byte = 8bit = 2^8 : -256 ~ 255
		// byte 값을 16진수로 변환 = %02x : 두 자리 수
		StringBuffer salt = new StringBuffer();
		for(byte b : bytes) {
			salt.append(String.format("%02x", b));
		}
		return salt.toString();
	}
	
	// 솔트를 사용해 비밀번호를 암호화하는 메소드
	public String getEncrypt(String salt, String pw) {
		pw += salt;
		
		// 암호화 해시 함수 : 암호화 방식(SHA-256) 지정
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(pw.getBytes());
			byte[] bytes = md.digest();
			// 16진수로 변환
			StringBuffer data = new StringBuffer();
			for(byte b : bytes) {
				data.append(String.format("%02x", b));
			}
			pw = data.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return pw;
	}
}
