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

public class StatisticsCourseListAdapter extends BaseAdapter {

    private Context context;
    private List<Course> courseList;
    private Fragment parent;
    private String userID = MainActivity.userID;

    public StatisticsCourseListAdapter(Context context, List<Course> courseList, Fragment parent) {
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
    public View getView(final int position, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.statistics, null);
        TextView courseGrade = (TextView) v.findViewById(R.id.courseGrade);
        TextView courseTitle = (TextView) v.findViewById(R.id.courseTitle);
        TextView courseDivide = (TextView) v.findViewById(R.id.courseDivide);
        TextView coursePersonnel = (TextView) v.findViewById(R.id.coursePersonnel);
        TextView courseRate = (TextView) v.findViewById(R.id.courseRate);

        if (courseList.get(position).getCourseGrade().equals("제한 없음") || courseList.get(position).getCourseGrade().equals("")) {
            courseGrade.setText("모든 학년");
        } else {
            courseGrade.setText(courseList.get(position).getCourseGrade() + "학년");
        }
        courseTitle.setText(courseList.get(position).getCourseTitle());
        courseDivide.setText(courseList.get(position).getCourseDivide() + "분반");
        if (courseList.get(position).getCoursePersonnel() == 0) {
            coursePersonnel.setText("인원 제한 없음");
            courseRate.setText("");
        } else {
            coursePersonnel.setText("신청 인원: " + courseList.get(position).getCourseRival()
                    + " / " + courseList.get(position).getCoursePersonnel());
            int rate = ((int) (((double) courseList.get(position).getCourseRival() * 100
                    / courseList.get(position).getCoursePersonnel()) + 0.5));
            courseRate.setText("경쟁률: " + rate + "%");
            if (rate < 20) {
                courseRate.setTextColor(parent.getResources().getColor(R.color.colorSafe));
            } else if (rate <= 50) {
                courseRate.setTextColor(parent.getResources().getColor(R.color.colorPrimary));
            } else if (rate <= 100) {
                courseRate.setTextColor(parent.getResources().getColor(R.color.colorDanger));
            } else if (rate <= 150) {
                courseRate.setTextColor(parent.getResources().getColor(R.color.colorWarning));
            } else {
                courseRate.setTextColor(parent.getResources().getColor(R.color.colorRed));
            }
        }
        v.setTag(courseList.get(position).getCourseID());

        Button deleteButton = (Button) v.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(parent.getActivity());
                                builder.setMessage("강의가 삭제되었습니다.")
                                        .setPositiveButton("확인", null)
                                        .create().show();

                                // 삭제할 강의의 정보를 미리 변수에 저장합니다.
                                int deletedCourseID = courseList.get(position).getCourseID();
                                int deletedCourseCredit = courseList.get(position).getCourseCredit();

                                // MainActivity의 중앙 데이터를 업데이트합니다.
                                MainActivity.courseIDList.remove(Integer.valueOf(deletedCourseID));
                                MainActivity.totalCredit -= deletedCourseCredit;

                                // StatisticsFragment의 화면을 업데이트합니다.
                                ((StatisticsFragment) parent).credit.setText(MainActivity.totalCredit + "학점");

                                // 이 어댑터의 리스트를 업데이트합니다.
                                courseList.remove(position);
                                notifyDataSetChanged();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(parent.getActivity());
                                builder.setMessage("강의 삭제에 실패했습니다.")
                                        .setNegativeButton("다시 시도", null)
                                        .create().show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                DeleteRequest deleteRequest = new DeleteRequest(userID, courseList.get(position).getCourseID() + "", responseListener);
                RequestQueue queue = Volley.newRequestQueue(parent.getActivity());
                queue.add(deleteRequest);
            }
        });
        return v;
    }
}