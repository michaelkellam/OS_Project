package com.example.os_project;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BlankFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BlankFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BlankFragment newInstance(String param1, String param2) {
        BlankFragment fragment = new BlankFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        MainActivity ma = ((MainActivity) getActivity());

        ma.bf = this;

        ArrayList<ArrayList<String>> format = new ArrayList<ArrayList<String>>();

        int numRows = ma.numRows + 1;
        int numCols = ma.numCols;

        TableRow[] rows = new TableRow[numRows];

        ScrollView sv = new ScrollView(ma);
        HorizontalScrollView hsv = new HorizontalScrollView(ma);
        TableLayout tl = new TableLayout(ma);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        ArrayList<String> names = ma.columnNames();
        ArrayList<String> data = ma.getData();

        String[] rowTxt = new String[numCols];
        format.add(new ArrayList<String>());
        for (int i = 0; i < numCols; i++) {
            format.get(0).add(names.get(i));
        }
        rows[1] = new TableRow(ma);

        for (int i = 0; i < names.size(); i++) {
            TextView tv = new TextView(ma);
            tv.setPadding(15,15,15,15);
            tv.setTextSize(20);
            tv.setTypeface(null, Typeface.BOLD);
            tv.setText(names.get(i));
            rows[1].addView(tv);
        }


        tl.addView(rows[1]);

        int rowReset = numRows - 1;
        int offset = 0;
        int colOn = 0;
        int rowNum = 0;
        for (int i = 2; i < rows.length; i++) {
            rows[i] = new TableRow(ma);

            while (rowReset >= 0) {
                TextView tv = new TextView(ma);
                tv.setPadding(15,15,15,15);
                tv.setTextSize(20);
                tv.setTypeface(null, Typeface.BOLD);
                if (offset < data.size()) {
                    tv.setText(data.get(offset));
                }

                offset += numRows - 1;
                rowReset--;
                rows[i].addView(tv);
                Log.d(null, "Added " + offset);
                rowNum++;
            }
            rowNum = 0;
            colOn++;
            offset = colOn;
            tl.addView(rows[i]);
            rowReset = numRows - 1;
        }

        TableRow lastRow = new TableRow(ma);
        int lastOff = numRows - 2;
        for (int i = 0; i < numCols; i++) {
            TextView tv = new TextView(ma);
            tv.setPadding(15,15,15,15);
            if (lastOff < data.size()) {
                    tv.setText(data.get(lastOff));
            }
            tv.setTextSize(20);
            tv.setTypeface(null, Typeface.BOLD);
            lastRow.addView(tv);
            lastOff += numRows - 1;
        }

        lastOff = numRows - 2;
        tl.addView(lastRow);

        TableRow buttons = new TableRow(ma);
        Button saveCsv = new Button(ma);
        saveCsv.setText("Save as CSV");
        Button returnBtn = new Button(ma);
        returnBtn.setText("Return to table list");
        Button logBtn = new Button(ma);
        logBtn.setText("Log out");

        buttons.addView(logBtn);
        buttons.addView(returnBtn);
        buttons.addView(saveCsv);

        tl.addView(buttons);


        sv.addView(tl);
        hsv.addView(sv);

        RelativeLayout rl = view.findViewById(R.id.rl_layout);
        hsv.setLayoutParams(params);

        rl.addView(hsv);

        ViewGroup vg = ma.findViewById(android.R.id.content);

        logBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                for (int i = 0; i < vg.getChildCount(); i++) {
                    if (vg.getChildAt(i) == hsv || vg.getChildAt(i) == sv || vg.getChildAt(i) == tl) {
                        vg.removeViewAt(i);
                    }
                }
                NavHostFragment.findNavController(BlankFragment.this)
                        .navigate(R.id.action_blankFragment_to_FirstFragment2);
                data.clear();
            }
        });

        returnBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                for (int i = 0; i < vg.getChildCount(); i++) {
                    if (vg.getChildAt(i) == hsv) {
                        vg.removeViewAt(i);
                    }
                }
                NavHostFragment.findNavController(BlankFragment.this)
                        .navigate(R.id.action_blankFragment_to_tableFragment2);
                data.clear();
            }
        });

        saveCsv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ArrayList<String[]> test = new ArrayList<String[]>();
                test.add(new String[names.size()]);
                Log.d(null, "" + test.get(0).length);
                for (int i = 0; i < names.size(); i++) {
                    test.get(0)[i] = names.get(i);
                }
                Log.d(null, "NAMES: " + names);



                int rowReset = numRows - 1;
                int offset = 0;
                int colOn = 0;
                int rowNum = 0;
                ArrayList<String> temp = new ArrayList<String>();
                for (int i = 2; i < rows.length; i++) {
                    rows[i] = new TableRow(ma);

                    while (rowReset >= 0) {
                        if (offset < data.size()) {
                            temp.add(data.get(offset));
                            Log.d(null, "ADDED TO T: " + data.get(offset));
                        }

                        offset += numRows - 1;
                        rowReset--;
                        rowNum++;

                    }

                    test.add(new String[names.size()]);
                    for (int j = 0; j < names.size(); j++) {
                        test.get(test.size() - 1)[j] = temp.get(j);
                        Log.d(null,"got " + temp.get(j));
                    }
                    temp.clear();

                    rowNum = 0;
                    colOn++;
                    offset = colOn;
                    rowReset = numRows - 1;
                }


                temp.clear();

                int lastOff = numRows - 2;
                for (int i = 0; i < numCols; i++) {
                    temp.add(data.get(lastOff));
                    lastOff += numRows - 1;
                }

                test.add(new String[names.size()]);

                for (int i = 0; i < names.size(); i++) {
                    test.get(test.size() - 1)[i] = temp.get(i);
                }

                lastOff = numRows - 2;

                AlertDialog.Builder alert = new AlertDialog.Builder(ma);
                alert.setTitle("Saving CSV");
                alert.setMessage("Give a name for file");
                final EditText input = new EditText(ma);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                alert.setView(input);

                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String title = input.getText().toString();
                        ma.saveToCsv(test, title);
                        data.clear();
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                    }
                });
                alert.show();


            }
        });
    }
}