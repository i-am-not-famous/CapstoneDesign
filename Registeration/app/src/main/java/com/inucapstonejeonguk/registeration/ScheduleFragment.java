package com.inucapstonejeonguk.registeration;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class ScheduleFragment extends Fragment {

    public ScheduleFragment() {
        // Required empty public constructor
    }

    // 시간표의 각 칸을 제어하기 위한 TextView 배열들을 선언합니다.
    private AutoResizeTextView monday[] = new AutoResizeTextView[17];
    private AutoResizeTextView tuesday[] = new AutoResizeTextView[17];
    private AutoResizeTextView wednesday[] = new AutoResizeTextView[17];
    private AutoResizeTextView thursday[] = new AutoResizeTextView[17];
    private AutoResizeTextView friday[] = new AutoResizeTextView[17];

    private Schedule schedule = new Schedule();

    @Override
    public void onActivityCreated(Bundle b) {
        super.onActivityCreated(b);

        // 반복문을 사용하여 85개의 TextView를 효율적으로 찾아와 배열에 할당합니다.
        for(int i = 0; i < 17; i++) {
            monday[i] = (AutoResizeTextView) getView().findViewById(getResources().getIdentifier("monday" + i, "id", getActivity().getPackageName()));
            tuesday[i] = (AutoResizeTextView) getView().findViewById(getResources().getIdentifier("tuesday" + i, "id", getActivity().getPackageName()));
            wednesday[i] = (AutoResizeTextView) getView().findViewById(getResources().getIdentifier("wednesday" + i, "id", getActivity().getPackageName()));
            thursday[i] = (AutoResizeTextView) getView().findViewById(getResources().getIdentifier("thursday" + i, "id", getActivity().getPackageName()));
            friday[i] = (AutoResizeTextView) getView().findViewById(getResources().getIdentifier("friday" + i, "id", getActivity().getPackageName()));
        }

        // [수정] 이 Fragment가 화면에 보일 때마다, 서버에서 최신 시간표 데이터를 직접 가져오도록 BackgroundTask를 실행합니다.
        new BackgroundTask().execute();
    }

    // [추가] 서버와 직접 통신하여 시간표를 그려주는 BackgroundTask를 다시 추가합니다.
    class BackgroundTask extends AsyncTask<Void, Void, String> {
        String target;

        @Override
        protected void onPreExecute() {
            try {
                target = "http://jeje2025.mycafe24.com/ScheduleList.php?userID=" + URLEncoder.encode(MainActivity.userID, "UTF-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while((temp = bufferedReader.readLine()) != null) {
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onProgressUpdate(Void... values) {
            super.onProgressUpdate();
        }

        @Override
        public void onPostExecute(String result) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count = 0;
                String courseProfessor;
                String courseTime;
                String courseTitle;

                // 데이터를 그리기 전에 MainActivity의 '마스터 노트'를 깨끗하게 비웁니다.
                MainActivity.schedule = new Schedule();

                while(count < jsonArray.length()) {
                    JSONObject object = jsonArray.getJSONObject(count);
                    courseProfessor = object.getString("courseProfessor");
                    courseTime = object.getString("courseTime");
                    courseTitle = object.getString("courseTitle");

                    // MainActivity의 '마스터 노트'에 최신 시간표 정보를 기록합니다.
                    MainActivity.schedule.addSchedule(courseTime, courseTitle, courseProfessor);
                    count++;
                }
            } catch(Exception e) {
                e.printStackTrace();
            }

            // 최신 정보가 기록된 '마스터 노트'를 바탕으로 시간표를 화면에 그립니다.
            MainActivity.schedule.setting(monday, tuesday, wednesday, thursday, friday, getContext());
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_schedule, container, false);
    }
}
