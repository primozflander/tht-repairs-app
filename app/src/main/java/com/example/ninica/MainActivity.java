package com.example.ninica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private Spinner spinner;
    private Button exportButton;
    private static final String[] projects = {"...",
            "A338",
            "B338",
            "E338",
            "F338",
            "G338",
            "H338",
            "I338",
            "K338",
            "L338",
            "R338",
            "X338",
            "A339",
            "D339",
            "F339",
            "H339",
            "J339",
            "L339",
            "A381",
            "A380",
            "A1018",
            "A0904",
            "B0904",
            "A614",
            "B614",
            "A609",
            "Axel classic",
            "Axel premium",
            "Ročajna",
            "Potenciometer F338",
    };
    Intent intent ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = (Spinner)findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_spinner_item, projects);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        ArrayList errorsListData = getListData();
        final ListView lv = (ListView) findViewById(R.id.errorsList);

        lv.setAdapter(new CustomListAdapter(this, errorsListData));

        exportButton= (Button) findViewById(R.id.exportButton);

        exportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String recipient = "ziga.cuk@kolektor.com";
                String message = "";

                String projectName = spinner.getSelectedItem().toString();
                EditText editText = (EditText) findViewById(R.id.editText);
                message = message.concat("Projekt: " + projectName);
                message = message.concat("\n\n Nalog in število kosov: " + editText.getText().toString());git status
                message = message.concat("\n\n Seznam napak:");
                ListAdapter listAdapter = lv.getAdapter();

                for (int i = 0; i<listAdapter.getCount(); i++){
                    ListItem listItem = (ListItem) listAdapter.getItem(i);
                    String newError = "\n" + listItem.getErrorCount() + "   " + listItem.getErrorName();
                    message = message.concat(newError);
                }

                String subject = "THT popravila";

                intent = new Intent(Intent.ACTION_SEND);

                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{recipient});
                intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                intent.putExtra(Intent.EXTRA_TEXT, message);

                intent.setType("message/rfc822");

                startActivity(Intent.createChooser(intent, "Select Email Sending App :"));
            }
        });



    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        ((TextView) parent.getChildAt(0)).setTextSize(24);
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub
    }


    private ArrayList getListData() {

        String[] errors = {"Manjka SMD komponenta",
                "Manjka THT komponenta",
                "Manjka uC",
                "Nepospajkana SMD komponenta",
                "Nepospajkana SMD komponenta (SOT23)",
                "Slabo pospajkan faston konektor",
                "Slabo pospajkana THT komponenta",
                "Slabo pospajkani shunti",
                "Slabo vstavljena THT komponenta",
                "Stik med SMD diodo in THT komponento",
                "Stik uC",
                "Spajka na faston konektorju",
                "Slabo zalit modul s PU maso - vidne komponente",
                "Slabo zalit modul s PU maso - mehurčkasta masa",
                "Poškodovan priključni kabel",
        };


        ArrayList<ListItem> results = new ArrayList<>();
        for (int i = 0; i < errors.length; i++) {
            ListItem error = new ListItem();
            error.setErrorName(errors[i]);
            error.setErrorCount("0");
            results.add(error);
        }
        return results;
    }
}
