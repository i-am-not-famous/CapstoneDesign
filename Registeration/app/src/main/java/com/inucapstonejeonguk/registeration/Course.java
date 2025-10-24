package com.inucapstonejeonguk.registeration;

public class Course {

    int courseID; // DB에서 자동 저장되는 번호(순번, 안드로이드 스튜디오에서는 사용X)
    String courseNumber; // 학수번호(강의 고유 번호)
    String courseUniversity; // 학부 혹은 대학원
    String courseYear; // 해당 년도
    String courseTerm; // 해당 학기
    String courseArea; // 강의 영역
    // 학부: 기초교양, 핵심교양, 심화교양, 전공기초, 전공핵심, 전공심화
    // 대학원: 전공필수, 전공선택
    String courseMajor; // 세부 교양 및 해당 학과
    // 학부 - 기초교양: 기초교양(학문의기초)
    // 학부 - 핵심교양: 핵심교양(INU세미나), 핵심교양(인문), 핵심교양(사회), 핵심교양(과학기술), 핵심교양(예술체육), 핵심교양(외국어)
    // 학부 - 심화교양: 심화교양(인문), 심화교양(사회), 심화교양(과학기술), 심화교양(예술체육), 심화교양(외국어)
    // 학부 - 전공기초, 전공핵심, 전공심화: 해당 학과
    // 대학원 - 전공필수, 전공선택: 해당 학과
    String courseGrade; // 해당 학년
    String courseTitle; // 강의 제목(안드로이드 스튜디오에서 사용하는 변수)
    String courseTitleKorean; // 강의 제목(국문)(DB에 저장되어 있는 칼럼, 안드로이드 스튜디오에서는 사용X)
    String courseTitleEnglish; // 강의 제목(영문)(DB에 저장되어 있는 칼럼, 안드로이드 스튜디오에서는 사용X)
    int courseCredit; // 강의 학점
    int courseDivide; // 강의 분반
    int coursePersonnel; // 강의 제한 인원
    String courseProfessor; // 강의 교수
    String courseTime; // 강의 시간대
    String courseRoom; // 강의실
    String courseType; // 강의 유형
    String foreignLanguageLecture; // 원어 강의(Y 또는 N 으로 표기, DB에도 Char로 저장)
    int courseRival; // 강의 경쟁자 수

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
    }

    public String getCourseUniversity() {
        return courseUniversity;
    }

    public void setCourseUniversity(String courseUniversity) {
        this.courseUniversity = courseUniversity;
    }

    public String getCourseYear() {
        return courseYear;
    }

    public void setCourseYear(String courseYear) {
        this.courseYear = courseYear;
    }

    public String getCourseTerm() {
        return courseTerm;
    }

    public void setCourseTerm(String courseTerm) {
        this.courseTerm = courseTerm;
    }

    public String getCourseArea() {
        return courseArea;
    }

    public void setCourseArea(String courseArea) {
        this.courseArea = courseArea;
    }

    public String getCourseMajor() {
        return courseMajor;
    }

    public void setCourseMajor(String courseMajor) {
        this.courseMajor = courseMajor;
    }

    public String getCourseGrade() {
        return courseGrade;
    }

    public void setCourseGrade(String courseGrade) {
        this.courseGrade = courseGrade;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getCourseTitleKorean() {
        return courseTitleKorean;
    }

    public void setCourseTitleKorean(String courseTitleKorean) {
        this.courseTitleKorean = courseTitleKorean;
    }

    public String getCourseTitleEnglish() {
        return courseTitleEnglish;
    }

    public void setCourseTitleEnglish(String courseTitleEnglish) {
        this.courseTitleEnglish = courseTitleEnglish;
    }

    public int getCourseCredit() {
        return courseCredit;
    }

    public void setCourseCredit(int courseCredit) {
        this.courseCredit = courseCredit;
    }

    public int getCourseDivide() {
        return courseDivide;
    }

    public void setCourseDivide(int courseDivide) {
        this.courseDivide = courseDivide;
    }

    public int getCoursePersonnel() {
        return coursePersonnel;
    }

    public void setCoursePersonnel(int coursePersonnel) {
        this.coursePersonnel = coursePersonnel;
    }

    public String getCourseProfessor() {
        return courseProfessor;
    }

    public void setCourseProfessor(String courseProfessor) {
        this.courseProfessor = courseProfessor;
    }

    public String getCourseTime() {
        return courseTime;
    }

    public void setCourseTime(String courseTime) {
        this.courseTime = courseTime;
    }

    public String getCourseRoom() {
        return courseRoom;
    }

    public void setCourseRoom(String courseRoom) {
        this.courseRoom = courseRoom;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public String getForeignLanguageLecture() {
        return foreignLanguageLecture;
    }

    public void setForeignLanguageLecture(String foreignLanguageLecture) {
        this.foreignLanguageLecture = foreignLanguageLecture;
    }

    public Course(int courseID,
                  String courseGrade,
                  String courseTitle,
                  int courseDivide,
                  int coursePersonnel,
                  int courseRival) {
        this.courseID = courseID;
        this.courseGrade = courseGrade;
        this.courseTitle = courseTitle;
        this.courseDivide = courseDivide;
        this.coursePersonnel = coursePersonnel;
        this.courseRival = courseRival;
    }

    public Course(int courseID,
                  String courseGrade,
                  String courseTitle,
                  int courseDivide,
                  int coursePersonnel,
                  int courseRival,
                  int courseCredit) {
        this.courseID = courseID;
        this.courseGrade = courseGrade;
        this.courseTitle = courseTitle;
        this.courseDivide = courseDivide;
        this.coursePersonnel = coursePersonnel;
        this.courseRival = courseRival;
        this.courseCredit = getCourseCredit();
    }

    public Course(int courseID,
                  String courseGrade,
                  String courseTitle,
                  String courseProfessor,
                  int courseCredit,
                  int courseDivide,
                  int coursePersonnel,
                  String courseTime) {
        this.courseID = courseID;
        this.courseGrade = courseGrade;
        this.courseTitle = courseTitle;
        this.courseProfessor = courseProfessor;
        this.courseCredit = courseCredit;
        this.courseDivide = courseDivide;
        this.coursePersonnel = coursePersonnel;
        this.courseTime = courseTime;
    }

    public int getCourseRival() {
        return courseRival;
    }

    public void setCourseRival(int courseRival) {
        this.courseRival = courseRival;
    }

    public Course(int courseID,
                  String courseNumber,
                  String courseUniversity,
                  String courseYear,
                  String courseTerm,
                  String courseArea,
                  String courseMajor,
                  String courseGrade,
                  String courseTitleKorean,
                  String courseTitleEnglish,
                  int courseCredit,
                  int courseDivide,
                  int coursePersonnel,
                  String courseProfessor,
                  String courseTime,
                  String courseRoom,
                  String courseType,
                  String foreignLanguageLecture) {
        this.courseID = courseID;
        this.courseNumber = courseNumber;
        this.courseUniversity = courseUniversity;
        this.courseYear = courseYear;
        this.courseTerm = courseTerm;
        this.courseArea = courseArea;
        this.courseMajor = courseMajor;
        this.courseGrade = courseGrade;
        this.courseTitle = courseTitleKorean;
        this.courseTitleKorean = courseTitleKorean;
        this.courseTitleEnglish = courseTitleEnglish;
        this.courseCredit = courseCredit;
        this.courseDivide = courseDivide;
        this.coursePersonnel = coursePersonnel;
        this.courseProfessor = courseProfessor;
        this.courseTime = courseTime;
        this.courseRoom = courseRoom;
        this.courseType = courseType;
        this.foreignLanguageLecture = foreignLanguageLecture;
    }
}
