<?php

    $con = mysqli_connect("localhost", "jeje2025", "wjddnr6537@", "jeje2025");

    $userID = $_POST["userID"];
    $courseID = $_POST["courseID"]; 

    $statement = mysqli_prepare($con, "INSERT INTO SCHEDULE VALUES (?, ?)");
    mysqli_stmt_bind_param($statement, "si", $userID, $courseID);
    
    // [수정] mysqli_stmt_execute()의 실행 결과를 변수에 저장합니다.
    // 성공하면 true, 실패하면 false가 저장됩니다.
    $executeSuccess = mysqli_stmt_execute($statement);

    $response = array();
    // [수정] 무조건 true를 보내는 대신, 실제 실행 결과를 success 값으로 보냅니다.
    $response["success"] = $executeSuccess;

    echo json_encode($response);
?>