package com.example.destresstreatment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.destresstreatment.Model.PsychiatristModel;

import java.util.List;

public class PsychAdapter  extends ArrayAdapter<PsychiatristModel> {

    private Activity context;
    private List<PsychiatristModel> psych;
    String databaseReference;

    public PsychAdapter(Activity context, List<PsychiatristModel> psych) {
        super(context, R.layout.activity_psych_adapter, psych);
        this.context = context;
        this.psych = psych;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            LayoutInflater inflater = context.getLayoutInflater();

            final View listView = inflater.inflate(R.layout.activity_psych_adapter, null, true);
            TextView tvPsychName = (TextView) listView.findViewById(R.id.tvPsychName);
            TextView tvContactNumber = (TextView) listView.findViewById(R.id.tvContactNumber);

            final PsychiatristModel list = psych.get(position);
            tvPsychName.setText(list.getPsychiatrist());
            tvContactNumber.setText(list.getContactNumber());

            return listView;
        }
}