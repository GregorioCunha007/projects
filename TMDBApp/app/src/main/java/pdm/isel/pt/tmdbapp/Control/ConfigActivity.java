package pdm.isel.pt.tmdbapp.Control;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;

import pdm.isel.pt.tmdbapp.R;

public class ConfigActivity extends AppCompatActivity {

    public CheckBox day , week;
    int checked;
    private UpdateDataBaseServiceConfig serviceConfig;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        day = (CheckBox) findViewById(R.id.day);
        week = (CheckBox) findViewById(R.id.week);

        serviceConfig = new UpdateDataBaseServiceConfig(this);

        if (serviceConfig.getTimeInterval() == UpdateDataBaseServiceConfig.DAILY) {
            checked = UpdateDataBaseServiceConfig.DAILY;
            day.setChecked(true);
        } else {
            checked = UpdateDataBaseServiceConfig.WEEKLY;
            week.setChecked(true);
        }

        day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checked == UpdateDataBaseServiceConfig.WEEKLY){
                    checked = UpdateDataBaseServiceConfig.DAILY;
                    week.setChecked(false);
                    day.setChecked(true);
                    serviceConfig.setTimeInterval(UpdateDataBaseServiceConfig.DAILY);
                }
                else{
                    day.setChecked(true);
                }
            }
        });

        week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checked == UpdateDataBaseServiceConfig.DAILY) {
                    checked = UpdateDataBaseServiceConfig.WEEKLY;
                    week.setChecked(true);
                    day.setChecked(false);
                    serviceConfig.setTimeInterval(UpdateDataBaseServiceConfig.WEEKLY);
                } else{
                    week.setChecked(true);
                }
            }
        });


    }


}
