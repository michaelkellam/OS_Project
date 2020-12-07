package com.example.os_project;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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



        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String ip = "us-cdbr-iron-east-01.cleardb.net";
                String db = "heroku_cb680c63ed9989a";
                String user = "b0fc7571f78ffb";

                ((MainActivity)getActivity()).ip = ip;
                ((MainActivity)getActivity()).db = db;
                ((MainActivity)getActivity()).user = user;

                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);



            }
        });




    }
}