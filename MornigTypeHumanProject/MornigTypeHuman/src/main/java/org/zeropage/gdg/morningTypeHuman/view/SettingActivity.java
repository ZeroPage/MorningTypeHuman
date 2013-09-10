package org.zeropage.gdg.morningTypeHuman.view;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;

import org.zeropage.gdg.morningTypeHuman.R;

public class SettingActivity extends PreferenceActivity {

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference);

        Preference statsPreference = findPreference("stats");
        Preference creditsPreference = findPreference("credits");

        if (statsPreference == null || creditsPreference == null) {
            throw new AssertionError();
        }

        statsPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
            //Intent intent = new Intent(SettingActivity.this, StatsActivity.class);
            //startActivity(intent);
            //ToDo make StatsActivity and then remove this line and uncomment 2 lines above it.

            return false;
            }
        });

        creditsPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
            //Intent intent = new Intent(SettingActivity.this, CreditsActivity.class);
            //startActivity(intent);
                //ToDo make CreditsActivity and then remove this line and uncomment 2 lines above it.

            return false;
            }
        });
    }
}
