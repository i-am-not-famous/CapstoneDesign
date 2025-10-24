<?php

    $con = mysqli_connect("localhost", "jeje2025", "wjddnr6537@", "jeje2025");
    // [추가] 한글 깨짐 방지를 위해 charset을 설정합니다.
    mysqli_set_charset($con, "utf8");

    $userID = $_POST["userID"];
    $courseID = $_POST["courseID"];

    // [수정] SQL Injection 공격을 방지하기 위해, 변수를 직접 넣는 대신 안전한 Placeholder(?)를 사용합니다.
    $statement = mysqli_prepare($con, "DELETE FROM SCHEDULE WHERE userID = ? AND courseID = ?");

    // [수정] 이제 이 코드는 Placeholder(?)에 변수를 안전하게 바인딩하는 올바른 역할을 수행합니다.
    mysqli_stmt_bind_param($statement, "si", $userID, $courseID);
    
    // [추가] 실제 실행 성공 여부를 변수에 저장합니다.
    $executeSuccess = mysqli_stmt_execute($statement);

    $response = array();
    // [수정] 무조건 true를 보내는 대신, 실제 삭제 성공 여부를 success 값으로 보냅니다.
    $response["success"] = $executeSuccess;

    echo json_encode($response);
?>