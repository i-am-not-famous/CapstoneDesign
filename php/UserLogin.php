<?php

    // 계정으로 데이터베이스 접속
    $con = mysqli_connect("localhost", "jeje2025", "wjddnr6537@", "jeje2025");

    $userID = $_POST["userID"];
    $userPassword = $_POST["userPassword"];

    // [수정] 1. userID만으로 암호화된 비밀번호(userPassword)를 데이터베이스에서 가져옵니다.
    $statement = mysqli_prepare($con, "SELECT userPassword FROM USER WHERE userID = ?");
    mysqli_stmt_bind_param($statement, "s", $userID);
    mysqli_stmt_execute($statement);
    mysqli_stmt_store_result($statement);
    
    // [수정] 가져온 암호화된 비밀번호를 $hashedPassword 변수에 저장합니다.
    mysqli_stmt_bind_result($statement, $hashedPassword);

    $response = array();
    $response["success"] = false;

    // mysqli_stmt_fetch()를 통해 일치하는 ID의 사용자가 있는지 확인합니다.
    if(mysqli_stmt_fetch($statement)) {
        // [수정] 2. password_verify로 생체 비밀번호와 DB의 암호화된 비밀번호를 비교합니다.
        if(password_verify($userPassword, $hashedPassword)) {
            $response["success"] = true;
            $response["userID"] = $userID;
        }
    }

    // 최종 응답을 JSON 형태로 출력합니다.
    echo json_encode($response);
?>