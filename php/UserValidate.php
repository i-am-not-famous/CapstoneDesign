<?php

    // 1. 데이터베이스 연결 설정
    // mysqli_connect() 함수를 사용하여 MySQL 데이터베이스에 연결합니다.
    // 파라미터는 순서대로 (호스트 주소, DB 사용자 ID, DB 비밀번호, 데이터베이스 이름) 입니다.
    $con = mysqli_connect("localhost", "jeje2025", "wjddnr6537@", "jeje2025");

    // 2. 안드로이드 앱에서 보낸 데이터 수신
    // 안드로이드 Volley 라이브러리에서 "userID"라는 키(key)로 보낸 값을 받아서 $userID 변수에 저장합니다.
    // [핵심 오류 1] 이 코드가 없었기 때문에, 아래에서 존재하지 않는 $userID 변수를 사용하려다 'HTTP ERROR 500'이 발생했습니다.
    $userID = $_POST["userID"];

    // 3. 데이터베이스 조회(Query) 준비
    // [핵심 오류 2] "SELECT *" 대신 필요한 컬럼인 "userID"만 명시적으로 조회합니다.
    // "SELECT *"를 사용하면 USER 테이블의 모든 컬럼(5개 이상)을 가져오게 되는데, 
    // 아래 mysqli_stmt_bind_result에서는 결과를 담을 변수를 1개만 준비했기 때문에 데이터 개수가 맞지 않는 논리적 오류가 발생합니다.
    $statement = mysqli_prepare($con, "SELECT userID FROM USER WHERE userID = ?");

    // 4. 준비된 조회문에 실제 값 바인딩
    // SQL Injection 공격을 방지하기 위해 사용합니다.
    // '?' 자리에 $userID 변수의 값을 문자열(s) 형태로 안전하게 채워 넣습니다.
    mysqli_stmt_bind_param($statement, "s", $userID);
    
    // 5. 조회문 실행
    mysqli_stmt_execute($statement);

    // 6. 조회 결과 저장
    // 실행된 조회문의 결과를 서버 메모리에 저장하여, 결과가 있는지 없는지 확인할 수 있도록 준비합니다.
    mysqli_stmt_store_result($statement);
    
    // 7. 조회 결과를 담을 변수 준비
    // 메모리에 저장된 결과(userID 컬럼 값)를 $resultUserID 라는 새로운 변수에 담을 준비를 합니다.
    mysqli_stmt_bind_result($statement, $resultUserID);

    // 8. 안드로이드 앱으로 보낼 응답(response) 배열 생성
    $response = array();
    $response["success"] = true; // 기본값은 '사용 가능한 아이디' (true)로 설정합니다.

    // 9. 결과 확인 및 최종 응답 결정
    // mysqli_stmt_fetch()는 메모리에 저장된 결과가 있으면 true, 없으면 false를 반환합니다.
    // 즉, 동일한 아이디가 데이터베이스에 존재하면 이 while문이 실행됩니다.
    while(mysqli_stmt_fetch($statement)) {
        // 동일한 아이디가 존재하므로, '사용 불가능한 아이디' (false)로 응답 값을 변경합니다.
        $response["success"] = false; 
    }

    // 10. 최종 응답을 JSON 형태로 출력
    // 안드로이드 앱(Volley)이 알아들을 수 있도록 배열을 JSON 문자열로 변환하여 출력합니다.
    echo json_encode($response);

    // 11. 데이터베이스 연결 종료
    // 모든 작업이 끝났으므로, 서버 자원을 아끼기 위해 데이터베이스 연결을 닫습니다.
    mysqli_close($con);
?>

