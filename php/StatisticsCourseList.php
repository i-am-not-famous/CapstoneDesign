<?php

    header("Content-Type: text/html; charset=UTF-8");
    $con = mysqli_connect("localhost", "jeje2025", "wjddnr6537@", "jeje2025");
    mysqli_set_charset($con, "utf8");
    
    $userID = $_GET["userID"];

    // [수정] 1. DB에 없는 courseTitle 대신 courseTitleKorean을 조회합니다.
    // [수정] 2. COUNT()의 결과에 'courseRival'이라는 깔끔한 별명(Alias)을 붙여줍니다. (AS courseRival)
    $result = mysqli_query($con, "SELECT COURSE.courseID, COURSE.courseGrade, COURSE.courseTitleKorean, COURSE.courseDivide, COURSE.coursePersonnel,
        COUNT(SCHEDULE.courseID) AS courseRival, COURSE.courseCredit FROM SCHEDULE, COURSE 
        WHERE SCHEDULE.courseID IN(SELECT SCHEDULE.courseID FROM SCHEDULE WHERE SCHEDULE.userID = '$userID') 
        AND SCHEDULE.courseID = COURSE.courseID GROUP BY SCHEDULE.courseID");

    $response = array();

    while($row = mysqli_fetch_array($result)) {
        // [수정] 3. 안드로이드와 약속된 "courseTitle", "courseRival" 키를 사용합니다.
        array_push($response, array(
            "courseID" => $row[0], 
            "courseGrade" => $row[1], 
            "courseTitle" => $row[2], 
            "courseDivide" => $row[3], 
            "coursePersonnel" => $row[4], 
            "courseRival" => $row[5], 
            "courseCredit" => $row[6]
        ));
    }

    echo json_encode(array("response" => $response), JSON_UNESCAPED_UNICODE);

    mysqli_close($con);
?>

