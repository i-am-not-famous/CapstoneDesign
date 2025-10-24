package com.inucapstonejeonguk.registeration;

import android.content.Context;
import android.widget.TextView;

public class Schedule {

    private String monday[] = new String[17];
    private String tuesday[] = new String[17];
    private String wednesday[] = new String[17];
    private String thursday[] = new String[17];
    private String friday[] = new String[17];

    public Schedule() {
        for (int i = 0; i < 17; i++) {
            monday[i] = "";
            tuesday[i] = "";
            wednesday[i] = "";
            thursday[i] = "";
            friday[i] = "";
        }
    }

    // [수정] addSchedule 메소드를 하나로 통합하고, 안정적인 파싱 로직을 사용합니다.
    public void addSchedule(String scheduleText, String courseTitle, String courseProfessor) {
        String professor = "";
        String courseInfo = courseTitle;

        // 안정적인 '스마트 스캐너' 방식으로 파싱을 수행합니다.
        addTimeToDay("월", scheduleText, monday, courseInfo);
        addTimeToDay("화", scheduleText, tuesday, courseInfo);
        addTimeToDay("수", scheduleText, wednesday, courseInfo);
        addTimeToDay("목", scheduleText, thursday, courseInfo);
        addTimeToDay("금", scheduleText, friday, courseInfo);
    }

    public boolean validate(String scheduleText) {
        if (scheduleText.equals("")) {
            return true;
        }
        if (!checkOverlap("월", scheduleText, monday)) return false;
        if (!checkOverlap("화", scheduleText, tuesday)) return false;
        if (!checkOverlap("수", scheduleText, wednesday)) return false;
        if (!checkOverlap("목", scheduleText, thursday)) return false;
        if (!checkOverlap("금", scheduleText, friday)) return false;
        return true;
    }

    // [수정] 2자리수 교시(10~16)를 처리할 수 있도록 파싱 로직을 개선한 도우미 메소드
    private void addTimeToDay(String day, String scheduleText, String[] dayArray, String courseInfo) {
        int dayIndex = scheduleText.indexOf(day);
        if (dayIndex == -1) return;

        int i = dayIndex + 1;
        while (i < scheduleText.length()) {
            if (Character.isLetter(scheduleText.charAt(i)) && scheduleText.charAt(i) != ' ') break;
            if (Character.isDigit(scheduleText.charAt(i))) {
                int time = 0;
                while (i < scheduleText.length() && Character.isDigit(scheduleText.charAt(i))) {
                    time = time * 10 + Character.getNumericValue(scheduleText.charAt(i));
                    i++;
                }
                // 교시는 1~17, 배열 인덱스는 0~16
                int timeIndex = time - 1;
                if (timeIndex >= 0 && timeIndex < dayArray.length) {
                    dayArray[timeIndex] = courseInfo;
                }
            } else {
                i++;
            }
        }
    }

    private boolean checkOverlap(String day, String scheduleText, String[] dayArray) {
        int dayIndex = scheduleText.indexOf(day);
        if (dayIndex == -1) return true;

        int i = dayIndex + 1;
        while (i < scheduleText.length()) {
            if (Character.isLetter(scheduleText.charAt(i)) && scheduleText.charAt(i) != ' ') break;
            if (Character.isDigit(scheduleText.charAt(i))) {
                int time = 0;
                while (i < scheduleText.length() && Character.isDigit(scheduleText.charAt(i))) {
                    time = time * 10 + Character.getNumericValue(scheduleText.charAt(i));
                    i++;
                }
                int timeIndex = time - 1;
                if (timeIndex >= 0 && timeIndex < dayArray.length) {
                    if (!dayArray[timeIndex].equals("")) {
                        return false;
                    }
                }
            } else {
                i++;
            }
        }
        return true;
    }

    public void setting(AutoResizeTextView[] monday,
                        AutoResizeTextView[] tuesday,
                        AutoResizeTextView[] wednesday,
                        AutoResizeTextView[] thursday,
                        AutoResizeTextView[] friday,
                        Context context) {

        int maxLength = 0;
        String maxString ="";
        for (int i = 0; i < 17; i++) {
            if (this.monday[i].length() > maxLength) {
                maxLength = this.monday[i].length();
                maxString = this.monday[i];
            }
            if (this.tuesday[i].length() > maxLength) {
                maxLength = this.tuesday[i].length();
                maxString = this.tuesday[i];
            }
            if (this.wednesday[i].length() > maxLength) {
                maxLength = this.wednesday[i].length();
                maxString = this.wednesday[i];
            }
            if (this.thursday[i].length() > maxLength) {
                maxLength = this.thursday[i].length();
                maxString = this.thursday[i];
            }
            if (this.friday[i].length() > maxLength) {
                maxLength = this.friday[i].length();
                maxString = this.friday[i];
            }
        }

        for (int i = 0; i < 17; i++) {
            if (!this.monday[i].equals("")) {
                monday[i].setText(this.monday[i]);
                monday[i].setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
            } else {
                monday[i].setText(maxString);
                monday[i].setBackgroundResource(R.drawable.cell_shape);
            }
            if (!this.tuesday[i].equals("")) {
                tuesday[i].setText(this.tuesday[i]);
                tuesday[i].setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
            } else {
                tuesday[i].setText(maxString);
                tuesday[i].setBackgroundResource(R.drawable.cell_shape);
            }
            if (!this.wednesday[i].equals("")) {
                wednesday[i].setText(this.wednesday[i]);
                wednesday[i].setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
            } else {
                wednesday[i].setText(maxString);
                wednesday[i].setBackgroundResource(R.drawable.cell_shape);
            }
            if (!this.thursday[i].equals("")) {
                thursday[i].setText(this.thursday[i]);
                thursday[i].setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
            } else {
                thursday[i].setText(maxString);
                thursday[i].setBackgroundResource(R.drawable.cell_shape);
            }
            if (!this.friday[i].equals("")) {
                friday[i].setText(this.friday[i]);
                friday[i].setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
            } else {
                friday[i].setText(maxString);
                friday[i].setBackgroundResource(R.drawable.cell_shape);
            }

            monday[i].resizeText();
            tuesday[i].resizeText();
            wednesday[i].resizeText();
            thursday[i].resizeText();
            friday[i].resizeText();

        }
    }
}