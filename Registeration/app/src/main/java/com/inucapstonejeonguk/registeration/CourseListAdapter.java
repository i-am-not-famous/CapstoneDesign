package com.inucapstonejeonguk.registeration;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.List;

public class CourseListAdapter extends BaseAdapter {

    private Context context;
    private List<Course> courseList;
    private Fragment parent;
    private String userID = MainActivity.userID;

    // 이 어댑터는 이제 MainActivity의 중앙 데이터를 그대로 사용합니다.
    private Schedule schedule = MainActivity.schedule;
    private List<Integer> courseIDList = MainActivity.courseIDList;

    // 어댑터 내의 모든 학점 관련 변수와 BackgroundTask를 삭제합니다.

    public CourseListAdapter(Context context, List<Course> courseList, Fragment parent) {
        this.context = context;
        this.courseList = courseList;
        this.parent = parent;
    }

    @Override
    public int getCount() {
        return courseList.size();
    }

    @Override
    public Object getItem(int position) {
        return courseList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.course, null);
        TextView courseGrade = (TextView) v.findViewById(R.id.courseGrade);
        TextView courseTitle = (TextView) v.findViewById(R.id.courseTitle);
        TextView courseCredit = (TextView) v.findViewById(R.id.courseCredit);
        TextView courseDivide = (TextView) v.findViewById(R.id.courseDivide);
        TextView coursePersonnel = (TextView) v.findViewById(R.id.coursePersonnel);
        TextView courseProfessor = (TextView) v.findViewById(R.id.courseProfessor);
        TextView courseTime = (TextView) v.findViewById(R.id.courseTime);

        if(courseList.get(position).getCourseGrade().equals("제한 없음") || courseList.get(position).getCourseGrade().equals("")) {
            courseGrade.setText("모든 학년");
        } else {
            courseGrade.setText(courseList.get(position).getCourseGrade() + "학년");
        }

        courseTitle.setText(courseList.get(position).getCourseTitle());
        courseCredit.setText(courseList.get(position).getCourseCredit() + "학점");
        courseDivide.setText(courseList.get(position).getCourseDivide() + "분반");

        if(courseList.get(position).getCoursePersonnel() == 0) {
            coursePersonnel.setText("인원 제한 없음");
        } else {
            coursePersonnel.setText("제한 인원 : " + courseList.get(position).getCoursePersonnel() + "명");
        }

        if(courseList.get(position).getCourseProfessor().equals("")) {
            courseProfessor.setText("개인 연구");
        } else {
            courseProfessor.setText(courseList.get(position).getCourseProfessor() + "교수님");
        }

        courseTime.setText(courseList.get(position).getCourseTime());

        v.setTag(courseList.get(position).getCourseID());

        Button addButton = (Button) v.findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 학점 검사를 MainActivity.totalCredit으로 수행합니다.
                if (MainActivity.totalCredit + courseList.get(position).getCourseCredit() > 24) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(parent.getActivity());
                    builder.setMessage("24학점을 초과할 수 없습니다.")
                            .setPositiveButton("다시 시도", null)
                            .create().show();
                    return;
                }

                if(!alreadyIn(courseIDList, courseList.get(position).getCourseID())) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(parent.getActivity());
                    builder.setMessage("이미 추가한 강의입니다.")
                            .setPositiveButton("다시 시도", null)
                            .create().show();
                    return;
                }

                if (!schedule.validate(courseList.get(position).getCourseTime())) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(parent.getActivity());
                    builder.setMessage("시간표가 중복됩니다.")
                            .setPositiveButton("다시 시도", null)
                            .create().show();
                    return;
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(parent.getActivity());
                                builder.setMessage("강의가 추가되었습니다.")
                                        .setPositiveButton("확인", null)
                                        .create().show();

                                // 성공 시 MainActivity의 중앙 데이터들을 직접 업데이트합니다.
                                MainActivity.courseIDList.add(courseList.get(position).getCourseID());
                                MainActivity.schedule.addSchedule(
                                        courseList.get(position).getCourseTime(),
                                        courseList.get(position).getCourseTitle(),
                                        courseList.get(position).getCourseProfessor()
                                );
                                MainActivity.totalCredit += courseList.get(position).getCourseCredit();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(parent.getActivity());
                                builder.setMessage("강의 추가에 실패했습니다.")
                                        .setNegativeButton("확인", null)
                                        .create().show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                AddRequest addRequest = new AddRequest(userID, courseList.get(position).getCourseID() + "", responseListener);
                RequestQueue queue = Volley.newRequestQueue(parent.getActivity());
                queue.add(addRequest);
            }
        });
        return v;
    }

    public boolean alreadyIn(List<Integer> courseIDList, int item) {
        for (int i = 0; i < courseIDList.size(); i++) {
            if (courseIDList.get(i) == item) {
                return false;
            }
        }
        return true;
    }
}