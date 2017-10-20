package com.android.tutotials.facebooklogintest;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;


public class FacebookFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String LOGIN_SUCCESS_TAG = "success";
    public static final int FB_LOGIN_SUCCEED_RES_CODE = 100;

    private LoginButton loginButton;
    private CallbackManager callbackManager;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FacebookFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static FacebookFragment newInstance(String param1, String param2) {
        FacebookFragment fragment = new FacebookFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_facebook, container, false);
        loginButton = (LoginButton) v.findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");
        // Inflate the layout for this fragment

        callbackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                sendResult(true);
            }

            @Override
            public void onCancel() {
                //Toast.makeText(getApplicationContext(), "Login Cancelled!",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("user_photos"));

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    private void sendResult(boolean flag){
        mListener.onFBFragmentInteraction(flag);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFBFragmentInteraction(boolean result);
    }
}
