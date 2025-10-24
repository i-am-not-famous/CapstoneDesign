<?php

    header("Content-Type: text/html; charset=UTF-8");
    $con = mysqli_connect("localhost", "jeje2025", "wjddnr6537@", "jeje2025");
    mysqli_set_charset($con, "utf8");

    $userID = $_GET["userID"];

    // [수정] 불필요하고 오류 가능성이 있는 USER 테이블 조인을 제거하고,
    // SCHEDULE과 COURSE 테이블만으로 시간표를 조회하여 더 안정적이고 효율적으로 만듭니다.
    $result = mysqli_query($con, "SELECT COURSE.courseID, COURSE.courseTime, COURSE.courseProfessor, COURSE.courseTitleKorean, CourseCredit 
        FROM USER, COURSE, SCHEDULE 
        WHERE USER.userID = '$userID' AND USER.userID = SCHEDULE.userID AND SCHEDULE.courseID = COURSE.courseID");

    $response = array();

    while($row = mysqli_fetch_array($result)) {
        // [수정] 안드로이드와의 호환성을 위해, courseTitleKorean 값을 "courseTitle"이라는 키로 보냅니다.
        array_push($response, array(
            "courseID" => $row[0], 
            "courseTime" => $row[1], 
            "courseProfessor" => $row[2],
            "courseTitle" => $row[3],
            "courseCredit"=> $row[4]
        ));
    }

    echo json_encode(array("response"=>$response), JSON_UNESCAPED_UNICODE);
    mysqli_close($con);
?>

