package org.zeropage.gdg.morningTypeHuman.controller;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.zeropage.gdg.morningTypeHuman.R;
import org.zeropage.gdg.morningTypeHuman.model.AlarmInfo;
import org.zeropage.gdg.morningTypeHuman.model.AlarmInfoStorage;
import org.zeropage.gdg.morningTypeHuman.view.AlarmAddActivity;
import org.zeropage.gdg.morningTypeHuman.view.AlarmListActivity;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by rino0601 on 13. 9. 13..
 */
public class AlarmListActivityController extends ArrayAdapter<AlarmInfo> implements View.OnClickListener {
    private AlarmListActivity activity;

    public AlarmListActivityController(AlarmListActivity activity) {
        super(activity, R.layout.alarm_list_row); //2번째 파라미터는 쓰지 않는 값...
        this.activity = activity;
        clear();
        ArrayList<AlarmInfo> alarmList = null;
        try {
            alarmList = AlarmInfoStorage.load();
        } catch (IOException e) {
            alarmList = new ArrayList<AlarmInfo>(); // 없으면 새로 만듬.
            try {
                AlarmInfoStorage.save(alarmList);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        for(AlarmInfo alarmInfo : alarmList ){
            add(alarmInfo);
        }
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = activity.getLayoutInflater().inflate(R.layout.alarm_list_row, null);
        AlarmInfo alarmInfo = getItem(position);

        // skip imageView

        TextView lectureName = (TextView) row.findViewById(R.id.textViewLectureName);
        lectureName.setText(alarmInfo.name);

        TextView ampm = (TextView) row.findViewById(R.id.textViewApPm);
        ampm.setText(alarmInfo.hour>=12?"p.m":"a.m");

        TextView time = (TextView) row.findViewById(R.id.textViewTime);
        time.setText(""+(alarmInfo.hour>12?alarmInfo.hour-12:alarmInfo.hour)+":"+alarmInfo.minute);

        return row;
    }

    public void listItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        // TODO : call AlarmEditActivity
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(activity, AlarmAddActivity.class);
        activity.startActivity(intent);
    }
}
