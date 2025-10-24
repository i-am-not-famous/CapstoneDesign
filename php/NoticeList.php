<?php

    // 계정으로 데이터베이스 접속
    $con = mysqli_connect("localhost", "jeje2025", "wjddnr6537@", "jeje2025");

    // NOTICE 테이블 안에서 noticeDate를 기준으로 내림차순(최신 순서)으로 검색
    $result = mysqli_query($con, "SELECT * FROM NOTICE ORDER BY noticeDate DESC;");

    // 배열 생성
    $response = array();

    // 배열의 내용(공지사항의 내용, 작성자 이름, 날짜)을 받아옴
    while($row = mysqli_fetch_array($result)) {
        array_push($response, array("noticeContent" => $row[0], "noticeName" => $row[1], "NoticeDate" => $row[2]));
    }

    // response 형태로 사용자에게 보여줌
    echo json_encode(Array("response" => $response));
    mysqli_close($con);
?>