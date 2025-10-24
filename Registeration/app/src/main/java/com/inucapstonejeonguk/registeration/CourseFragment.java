package com.inucapstonejeonguk.registeration;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CourseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CourseFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CourseFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CourseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CourseFragment newInstance(String param1, String param2) {
        CourseFragment fragment = new CourseFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private ArrayAdapter yearAdapter;
    private Spinner yearSpinner;
    private ArrayAdapter termAdapter;
    private Spinner termSpinner;
    private ArrayAdapter areaAdapter;
    private Spinner areaSpinner;
    private ArrayAdapter majorAdapter;
    private Spinner majorSpinner;


    private String courseUniversity = "";

    private ListView courseListView;
    private CourseListAdapter adapter;
    private List<Course> courseList;

    @Override
    public void onActivityCreated(Bundle b) {
        super.onActivityCreated(b);

        final RadioGroup courseUniversityGroup = (RadioGroup) getView().findViewById(R.id.courseUniversityGroup);
        yearSpinner = (Spinner) getView().findViewById(R.id.yearSpinner);
        termSpinner = (Spinner) getView().findViewById(R.id.termSpinner);
        areaSpinner = (Spinner) getView().findViewById(R.id.areaSpinner);
        majorSpinner = (Spinner) getView().findViewById(R.id.majorSpinner);

        courseUniversityGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton courseButton = (RadioButton) getView().findViewById(checkedId);
                courseUniversity = courseButton.getText().toString();

                yearAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.year, android.R.layout.simple_spinner_dropdown_item);
                yearSpinner.setAdapter(yearAdapter);
                yearSpinner.setSelection(9);

                termAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.term, android.R.layout.simple_spinner_dropdown_item);
                termSpinner.setAdapter(termAdapter);
                termSpinner.setSelection(0);

                // [수정] '학부'나 '대학원' 선택 시, areaSpinner의 내용만 우선 변경합니다.
                // majorSpinner의 내용은 areaSpinner가 선택된 후에 결정되므로, 여기서 미리 설정하던 코드를 삭제합니다.
                if(courseUniversity.equals("학부")) {
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.universityArea, android.R.layout.simple_spinner_dropdown_item);
                    areaSpinner.setAdapter(areaAdapter);
                } else if(courseUniversity.equals("대학원")) {
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.graduateArea, android.R.layout.simple_spinner_dropdown_item);
                    areaSpinner.setAdapter(areaAdapter);
                }
            }
        });

        // [수정] areaSpinner의 선택에 따라 majorSpinner의 내용을 동적으로 변경하는 핵심 로직입니다.
        areaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // '학부'가 선택된 상태일 때의 로직
                if(courseUniversity.equals("학부")) {
                    String selectedArea = areaSpinner.getSelectedItem().toString();
                    // 선택된 이수 구분에 따라 서로 다른 배열로 majorAdapter를 초기화합니다.
                    if(selectedArea.equals("기초교양")) {
                        majorAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.university_area_basic, android.R.layout.simple_spinner_dropdown_item);
                    } else if (selectedArea.equals("핵심교양")) {
                        majorAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.university_area_core, android.R.layout.simple_spinner_dropdown_item);
                    } else if (selectedArea.equals("심화교양")) {
                        majorAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.university_area_advanced, android.R.layout.simple_spinner_dropdown_item);
                    } else { // 전공기초, 전공핵심, 전공심화의 경우
                        // [수정] 학부 전공은 universityMajor 배열을 사용합니다.
                        majorAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.universityMajor, android.R.layout.simple_spinner_dropdown_item);
                    }
                    majorSpinner.setAdapter(majorAdapter);
                }
                // '대학원'이 선택된 상태일 때의 로직
                else if (courseUniversity.equals("대학원")) {
                    // [수정] '대학원' 선택 시에는 'graduateMajor' 배열을 사용해야 합니다.
                    majorAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.graduateMajor, android.R.layout.simple_spinner_dropdown_item);
                    majorSpinner.setAdapter(majorAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        courseListView = (ListView) getView().findViewById(R.id.courseListView);
        courseList = new ArrayList<Course>();
        adapter = new CourseListAdapter(getContext().getApplicationContext(), courseList, this);
        courseListView.setAdapter(adapter);

        Button searchButton = (Button) getView(). findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new BackgroundTask().execute();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_course, container, false);
    }

    class BackgroundTask extends AsyncTask<Void, Void, String> {

        String target;

        @Override
        protected void onPreExecute() {

            try {

                target  = "http://jeje2025.mycafe24.com/CourseList.php?courseUniversity="
                        + URLEncoder.encode(courseUniversity, "UTF-8")
                        + "&courseYear=" + URLEncoder.encode(yearSpinner.getSelectedItem().toString(), "UTF-8")
                        + "&courseTerm=" + URLEncoder.encode(termSpinner.getSelectedItem().toString(),"UTF-8")
                        + "&courseArea=" + URLEncoder.encode(areaSpinner.getSelectedItem().toString(), "UTF-8")
                        + "&courseMajor=" + URLEncoder.encode(majorSpinner.getSelectedItem().toString(), "UTF-8");
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
                courseList.clear();
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count = 0;

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

                while(count < jsonArray.length()) {
                    JSONObject object = jsonArray.getJSONObject(count);
                    courseID = object.getInt("courseID");
                    courseNumber = object.getString("courseNumber");
                    courseUniversity = object.getString("courseUniversity");
                    courseYear = object.getString("courseYear");
                    courseTerm = object.getString("courseTerm");
                    courseArea = object.getString("courseArea");
                    courseMajor = object.getString("courseMajor");
                    courseGrade = object.getString("courseGrade");
                    courseTitleKorean = object.getString("courseTitleKorean");
                    courseTitleEnglish = object.getString("courseTitleEnglish");
                    courseCredit = object.getInt("courseCredit");
                    courseDivide = object.getInt("courseDivide");
                    coursePersonnel = object.getInt("coursePersonnel");
                    courseProfessor = object.getString("courseProfessor");
                    courseTime = object.getString("courseTime");
                    courseRoom = object.getString("courseRoom");
                    courseType = object.getString("courseType");
                    foreignLanguageLecture = object.getString("foreignLanguageLecture");


                    Course course = new Course(
                            courseID,
                            courseNumber,
                            courseUniversity,
                            courseYear,
                            courseTerm,
                            courseArea,
                            courseMajor,
                            courseGrade,
                            courseTitleKorean,
                            courseTitleEnglish,
                            courseCredit,
                            courseDivide,
                            coursePersonnel,
                            courseProfessor,
                            courseTime,
                            courseRoom,
                            courseType,
                            foreignLanguageLecture);

                    courseList.add(course);
                    count++;
                }

                if(count == 0) {
                    AlertDialog dialog;
                    AlertDialog.Builder builder = new AlertDialog.Builder(CourseFragment.this.getActivity());
                    dialog = builder.setMessage("조회된 강의가 없습니다. \n날짜를 확인하세요.")
                            .setPositiveButton("확인", null)
                            .create();
                    dialog.show();
                }
                adapter.notifyDataSetChanged();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}