<?php

    header('Content-Type: text/html; charset=UTF-8');

    $con = mysqli_connect("localhost", "jeje2025", "wjddnr6537@", "jeje2025");
    mysqli_set_charset($con,"utf8");

    $courseUniversity = $_GET["courseUniversity"];
    $courseYear = $_GET["courseYear"];
    $courseTerm = $_GET["courseTerm"];
    $courseArea = $_GET["courseArea"];
    $courseMajor = $_GET["courseMajor"];

    $result = mysqli_query($con, "SELECT * FROM COURSE WHERE courseUniversity = '$courseUniversity' 
        AND courseYear = '$courseYear' AND courseTerm = '$courseTerm' AND courseArea = '$courseArea' AND courseMajor = '$courseMajor'");
    
    $response = array();

    while($row = mysqli_fetch_array($result)) {
        // [수정] 새로운 테이블 컬럼 순서에 맞게 인덱스 번호를 조정합니다.
        array_push($response, array(
            "courseID" => $row[0], 
            "courseNumber" => $row[1],
            "courseUniversity" => $row[2],
            "courseYear" => $row[3],
            "courseTerm" => $row[4],
            "courseArea" => $row[5],
            "courseMajor" => $row[6],
            "courseGrade" => $row[7],
            "courseTitleKorean" => $row[8],
            "courseTitleEnglish" => $row[9],
            "courseCredit" => $row[10],
            "courseDivide" => $row[11],
            "coursePersonnel" => $row[12],
            "courseProfessor" => $row[13],
            "courseTime" => $row[14],
            "courseRoom" => $row[15],
            "courseType" => $row[16],
            "foreignLanguageLecture" => $row[17]
            // courseNumber, courseTitleEnglish 등 새로운 컬럼은 
            // 안드로이드 코드와 호환성을 위해 우선 응답에 포함하지 않았습니다.
        ));
    }

    echo json_encode(array("response" => $response), JSON_UNESCAPED_UNICODE);
    
    mysqli_close($con);
?>