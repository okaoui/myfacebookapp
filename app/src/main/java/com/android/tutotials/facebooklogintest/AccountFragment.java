package com.android.tutotials.facebooklogintest;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class AccountFragment extends Fragment {


    private static final String ACCOUNT_CREATED = "accountCreated";
    private static final int ACCOUNT_CREATED_RES_CODE = 101;
    private static final int USER_SIGNEDIN_RES_CODE = 102;
    private static final String USER_SIGNED_IN = "userSignedIn";
    private OnFragmentInteractionListener mListener;
    private EditText emailText, passwordText;
    private Button createAccountButton, signinButton;

    private SharedPreferences accountPreferences;
    private SharedPreferences.Editor accountPrefsEditor;

    private static final String ACCOUNT_PREFS = "accountPrefs";
    private static final String ACCOUNT_SAVED = "accountSaved";
    private static final String ACCOUNT_EMAIL_TAG = "email";
    private static final String ACCOUNT_PASSWORD_TAG = "password";

    private String email, password;
    private boolean savedAccount;



    private ProgressDialog dialog;
    public AccountFragment() {
        // Required empty public constructor
    }


    public static AccountFragment newInstance(String param1, String param2) {
        AccountFragment fragment = new AccountFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //mParam1 = getArguments().getString(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_account, container, false);
        Typeface fbFontStyle = Typeface.createFromAsset(getActivity().getAssets(),  "fonts/FBLight-Regular.ttf");

        emailText = (EditText)v.findViewById(R.id.account_email_txt);
        passwordText = (EditText)v.findViewById(R.id.pass_txt);
        createAccountButton = (Button)v.findViewById(R.id.create_account);
        signinButton = (Button)v.findViewById(R.id.signin);

        emailText.setTypeface(fbFontStyle);
        passwordText.setTypeface(fbFontStyle);
        createAccountButton.setTypeface(fbFontStyle);
        signinButton.setTypeface(fbFontStyle);

        passwordText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                createAccountButton.setEnabled(true);
            }
        });

        accountPreferences = getActivity().getSharedPreferences(ACCOUNT_PREFS, Context.MODE_PRIVATE);
        accountPrefsEditor = accountPreferences.edit();
        savedAccount = accountPreferences.getBoolean(ACCOUNT_SAVED, false);

        if (savedAccount == true) {
            email = accountPreferences.getString(ACCOUNT_EMAIL_TAG, "");
            password = accountPreferences.getString(ACCOUNT_PASSWORD_TAG, "");

            emailText.setText(email);
            passwordText.setText(password);

            createAccountButton.setEnabled(false);
            signinButton.setEnabled(true);

        }

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog = new ProgressDialog(getActivity());
                dialog.setMessage("Please wait..");
                dialog.show();

                if(isEmailValid(emailText.getText().toString()) &&
                        isPasswordValid(passwordText.getText().toString())){

                    accountPrefsEditor.clear();
                    //Save credentials
                    accountPrefsEditor.putBoolean(ACCOUNT_SAVED, true);
                    accountPrefsEditor.putString(ACCOUNT_EMAIL_TAG, emailText.getText().toString());
                    accountPrefsEditor.putString(ACCOUNT_PASSWORD_TAG, passwordText.getText().toString());
                    accountPrefsEditor.commit();

                    sendResult(true);

                    dialog.dismiss();
                    Toast.makeText(getActivity(), R.string.account_created, Toast.LENGTH_SHORT).show();
                }else{
                    dialog.dismiss();
                    Toast.makeText(getActivity(), R.string.invalid_credentials, Toast.LENGTH_SHORT).show();
                }

                //Hide keyboard
                InputMethodManager inputManager = (InputMethodManager)
                        getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });

        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEmailValid(emailText.getText().toString()) &&
                        isPasswordValid(passwordText.getText().toString())){
                    if(email.equalsIgnoreCase(emailText.getText().toString()) &&
                            password.equalsIgnoreCase(passwordText.getText().toString())){
                        sendResult(true);
                    }else{
                        Toast.makeText(getActivity(), R.string.credentials_not_matching, Toast.LENGTH_SHORT).show();
                    }

                }

                //Hide keyboard
                InputMethodManager inputManager = (InputMethodManager)
                        getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });
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

    private boolean isEmailValid(String email){
        if (email == null || email.isEmpty()) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }

    private boolean isPasswordValid(String password){
        if(password == null || password.isEmpty()){
            return false;
        }else{
            return true;
        }
    }


    public interface OnFragmentInteractionListener {
        void onAccountFragmentInteraction(boolean result);
    }

    private void sendResult(boolean flag){
        mListener.onAccountFragmentInteraction(flag);
    }
}
