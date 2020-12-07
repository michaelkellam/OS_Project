package com.example.os_project;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.fragment.NavHostFragment;

import com.example.os_project.sql.SQLOps;

import java.sql.Connection;

public class FirstFragment extends Fragment {

    public Connection con;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((MainActivity) getActivity()).f1 = this;

        EditText eIP = (EditText) view.findViewById(R.id.editTextIP);
        EditText eDB = (EditText) view.findViewById(R.id.editTextDB);
        EditText eUser = (EditText) view.findViewById(R.id.editTextUser);
        EditText ePass = (EditText) view.findViewById(R.id.editTextTextPassword);

        Button autoBtn = (Button) view.findViewById(R.id.autolog);

        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //String ip = "us-cdbr-iron-east-01.cleardb.net";
                //String db = "heroku_cb680c63ed9989a";
                //String user = "b0fc7571f78ffb";

                String ip = eIP.getText().toString();
                String db = eDB.getText().toString();
                String user = eUser.getText().toString();
                String pass = ePass.getText().toString();

                ((MainActivity)getActivity()).ip = ip;
                ((MainActivity)getActivity()).db = db;
                ((MainActivity)getActivity()).user = user;
                ((MainActivity)getActivity()).pass = pass;

                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);



            }
        });

        autoBtn.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View view) {
               String ip = "us-cdbr-iron-east-01.cleardb.net";
               String db = "heroku_cb680c63ed9989a";
               String user = "b0fc7571f78ffb";
               String pass = "b562c8a3";

               ((MainActivity)getActivity()).ip = ip;
               ((MainActivity)getActivity()).db = db;
               ((MainActivity)getActivity()).user = user;
               ((MainActivity)getActivity()).pass = pass;

               NavHostFragment.findNavController(FirstFragment.this)
                       .navigate(R.id.action_FirstFragment_to_SecondFragment);
           }
        });




    }
}