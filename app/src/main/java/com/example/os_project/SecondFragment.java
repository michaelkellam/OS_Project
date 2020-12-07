package com.example.os_project;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.gridlayout.widget.GridLayout;
import androidx.navigation.fragment.NavHostFragment;

import com.example.os_project.sql.*;

import java.sql.Connection;
import java.util.ArrayList;

public class SecondFragment extends Fragment {

    public static Connection con;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    public void establishConnection(String ip, String db, String user, String pass) {
        ((MainActivity)getActivity()).startCon();

        TextView tv = (TextView) getView().findViewById(R.id.textview_second);
        if (((MainActivity)getActivity()).success) {
            tv.setText("Success");
        } else {
            tv.setText("Invalid login, please try again");
        }
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MainActivity ma = ((MainActivity) getActivity());

        ma.f2 = this;

        String ip = ((MainActivity)getActivity()).ip;
        String db = ((MainActivity)getActivity()).db;
        String user = ((MainActivity)getActivity()).user;
        String pass = ((MainActivity)getActivity()).pass;


        establishConnection(ip, db, user, pass);

        TableLayout tl = new TableLayout(ma);

        ((MainActivity) getActivity()).table = tl;

        view.findViewById(R.id.button_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });

        view.findViewById(R.id.button_tables).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity ma = ((MainActivity) getActivity());

                if (ma.success) {
                    NavHostFragment.findNavController(SecondFragment.this)
                            .navigate(R.id.action_SecondFragment_to_tableFragment);
                }


            }
        });
    }
}