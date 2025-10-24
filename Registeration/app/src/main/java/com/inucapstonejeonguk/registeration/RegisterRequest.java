package com.inucapstonejeonguk.registeration;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {

    // 서버 주소
    // http://jeje2025.mycafe24.com/mydbeditor/

    // 기존 코드(오류): http://jeje2025.mycafe24.com/mydbeditor/UserValidate.php
    // 오류 상황: 서버와의 연결 불가 상황
    // 수정 방법(1): 코드 수정
    // http://jeje2025.mycafe24.com/UserValidate.php -> 실패
    // 수정 방법(2): RegisterRequest.java 파일 서버 주소를 변경하기
    // http://jeje2025.mycafe24.com/UserRegister.php -> 실패
    // 수정 방법(3): UserValidate.php 파일의 ##   $userID = $_POST["userID"]; ## 코드 누락 -> 미해결
    // 수정 방법(4): SELECT *와 bind_result의 개수 불일치 상황 파악 및 SELECT userID로 꼭 필요한 데이터만 1개 가져오도록 수정
    // 수정 방법(3)과 (4)로 해결.

    final static private String URL = "http://jeje2025.mycafe24.com/UserRegister.php";
    private Map<String, String> parameters;

    public RegisterRequest(String userID, String userPassword, String userGender, String userMajor, String userEmail, Response.Listener<String> listener) {

        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("userPassword", userPassword);
        parameters.put("userGender", userGender);
        parameters.put("userMajor", userMajor);
        parameters.put("userEmail", userEmail);
    }

    @Override
    public Map<String, String> getParams() {

        return parameters;
    }

}

