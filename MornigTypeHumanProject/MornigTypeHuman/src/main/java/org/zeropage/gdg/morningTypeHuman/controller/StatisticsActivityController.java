package org.zeropage.gdg.morningTypeHuman.controller;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.zeropage.gdg.morningTypeHuman.R;
import org.zeropage.gdg.morningTypeHuman.model.AppStatisticsManager;
import org.zeropage.gdg.morningTypeHuman.model.StatisticsValue;
import org.zeropage.gdg.morningTypeHuman.view.StatisticsActivity;

import java.util.ArrayList;

/**
 * Created by Skywave on 13. 9. 14.
 */
public class StatisticsActivityController extends ArrayAdapter<StatisticsValue>{
    private StatisticsActivity activity;

    public StatisticsActivityController(StatisticsActivity activity) {
        super(activity, R.layout.statistics_list_row);
        this.activity = activity;
        clear();

        ArrayList<StatisticsValue> statisticsValues = AppStatisticsManager.getAllStatisticsValues();

        for(StatisticsValue value : statisticsValues) {
            add(value);
        }
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = activity.getLayoutInflater().inflate(R.layout.statistics_list_row, null);

        TextView description = (TextView) row.findViewById(R.id.description);
        TextView value = (TextView) row.findViewById(R.id.value);

        StatisticsValue statisticsValue = getItem(position);

        description.setText(statisticsValue.description);
        value.setText(statisticsValue.value);

        return row;
    }
}
