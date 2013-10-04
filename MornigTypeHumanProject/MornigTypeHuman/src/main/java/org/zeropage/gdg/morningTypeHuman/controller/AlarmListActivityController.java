package org.zeropage.gdg.morningTypeHuman.controller;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.zeropage.gdg.morningTypeHuman.R;
import org.zeropage.gdg.morningTypeHuman.model.AlarmInfo;
import org.zeropage.gdg.morningTypeHuman.model.AlarmInfoStorage;
import org.zeropage.gdg.morningTypeHuman.view.AlarmAddActivity;
import org.zeropage.gdg.morningTypeHuman.view.AlarmEditActivity;
import org.zeropage.gdg.morningTypeHuman.view.AlarmListActivity;
import org.zeropage.gdg.morningTypeHuman.view.alarmmanage.AlarmCheckBox;

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
            alarmList = AlarmInfoStorage.loadList();
        } catch (IOException e) {
            Toast.makeText(activity, "Error: 리스트를 읽는데 실패하였습니다.", Toast.LENGTH_SHORT).show();
            alarmList = new ArrayList<AlarmInfo>();
        }

        for (AlarmInfo alarmInfo : alarmList) {
            add(alarmInfo);
        }
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = activity.getLayoutInflater().inflate(R.layout.alarm_list_row, null);
        AlarmInfo alarmInfo = getItem(position);

        Button editButton = (Button) row.findViewById(R.id.buttonEditAlarm);
        editButton.setTag(R.id.ALARMINFO_TO_EDIT_KEY, new Integer(position));
        editButton.setOnClickListener(this);

        TextView lectureName = (TextView) row.findViewById(R.id.textViewLectureName);
        lectureName.setText(alarmInfo.name);

        TextView ampm = (TextView) row.findViewById(R.id.textViewApPm);
        if (alarmInfo.hour >= 12) {
            ampm.setText("P.M.");
        } else {
            ampm.setText("A.M.");
        }

        TextView time = (TextView) row.findViewById(R.id.textViewTime);
        int hourForShow = alarmInfo.hour;
        if (hourForShow >= 12) {
            hourForShow -= 12;
        }
        time.setText(timeToString(hourForShow) + ":" + timeToString(alarmInfo.minute));

        AlarmCheckBox checkBoxMon = (AlarmCheckBox) row.findViewById(R.id.checkBoxMon);
        AlarmCheckBox checkBoxTue = (AlarmCheckBox) row.findViewById(R.id.checkBoxTue);
        AlarmCheckBox checkBoxWed = (AlarmCheckBox) row.findViewById(R.id.checkBoxWed);
        AlarmCheckBox checkBoxThu = (AlarmCheckBox) row.findViewById(R.id.checkBoxThu);
        AlarmCheckBox checkBoxFri = (AlarmCheckBox) row.findViewById(R.id.checkBoxFri);
        AlarmCheckBox checkBoxSat = (AlarmCheckBox) row.findViewById(R.id.checkBoxSat);
        AlarmCheckBox checkBoxSun = (AlarmCheckBox) row.findViewById(R.id.checkBoxSun);

        checkBoxMon.setChecked(alarmInfo.dayOfWeek.mon);
        checkBoxTue.setChecked(alarmInfo.dayOfWeek.tue);
        checkBoxWed.setChecked(alarmInfo.dayOfWeek.wed);
        checkBoxThu.setChecked(alarmInfo.dayOfWeek.thu);
        checkBoxFri.setChecked(alarmInfo.dayOfWeek.fri);
        checkBoxSat.setChecked(alarmInfo.dayOfWeek.sat);
        checkBoxSun.setChecked(alarmInfo.dayOfWeek.sun);

        FrameLayout isAlarmOn = (FrameLayout) row.findViewById(R.id.isAlarmOn);
        if (alarmInfo.isAlarmOn) {
            isAlarmOn.setBackgroundColor(Color.GREEN);
        } else {
            isAlarmOn.setBackgroundColor(Color.RED);
        }

        return row;
    }

    public void listItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        // NOT WORK! :(
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();

        if (viewId == R.id.buttonAddAlarm) {
            Intent intent = new Intent(activity, AlarmAddActivity.class);
            activity.startActivity(intent);
        } else if (viewId == R.id.buttonEditAlarm) {
            Intent intent = new Intent(activity, AlarmEditActivity.class);

            intent.putExtra("AlarmInfoToEdit", (Integer) view.getTag(R.id.ALARMINFO_TO_EDIT_KEY));

            activity.startActivity(intent);
        }
    }

    private String timeToString(int time) {
        if (time < 10) {
            return "0" + time;
        } else {
            return String.valueOf(time);
        }
    }
}
