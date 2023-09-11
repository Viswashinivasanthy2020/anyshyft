package com.demo.anyshyft.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.demo.anyshyft.R;
import com.demo.anyshyft.view.Personaldetails1;
import com.demo.anyshyft.view.Personaldetails2;
import com.demo.anyshyft.view.Personalinformation;


public class Account_fragment extends Fragment {

    TextView txt_personal;
    public Account_fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      //  return inflater.inflate(R.layout.fragment_account_fragment, container, false);
        View view = inflater.inflate(R.layout.fragment_account_fragment, container, false);
        txt_personal = view.findViewById(R.id.personal);
        txt_personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Personalinformation.class);
                startActivity(intent);
            }
        });
        return view;

    }
}