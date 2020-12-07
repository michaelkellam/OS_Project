package com.example.os_project;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.sql.Connection;

public class ThirdFragment extends Fragment {

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

        ListView lv = view.findViewById(R.id.listview_third);
        //((MainActivity) getActivity()).displayTables(lv);

        Log.d(null, "THIRD CREATED");

        //view.findViewById(R.id.button_to_second).setOnClickListener(new View.OnClickListener() {
          //  @Override
            //public void onClick(View view) {

              //  NavHostFragment.findNavController(ThirdFragment.this)
                //        .navigate(R.id.action_ThirdFragment_to_SecondFragment);

            //}
        //});




    }
}