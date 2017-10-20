package com.android.tutotials.facebooklogintest;

import android.content.Intent;
import android.net.Uri;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

public class MainActivity extends AppCompatActivity
        implements AlbumFragment.OnFragmentInteractionListener,
                   AccountFragment.OnFragmentInteractionListener,
                    FacebookFragment.OnFragmentInteractionListener{

    private static final String ACCOUNT_FRAGMENT_TAG = "fragments.user.account";
    private static final String ALBUMS_FRAGMENT_TAG = "fragments.user.albums";
    private static final String FB_FRAGMENT_TAG = "fragments.facebook";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragmentContainer);
        if(fragment == null) {
            fragment = new AccountFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.fragmentContainer, fragment, ACCOUNT_FRAGMENT_TAG)
                    .commit();
        }
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

    }

    @Override
    protected void onResume() {
        super.onResume();

        new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {

                if (currentAccessToken == null){
                    final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

                    ft.replace(R.id.fragmentContainer, new AccountFragment());
                    ft.commit();

                    setTitle(R.string.app_name);


                    Fragment fragment = getSupportFragmentManager().findFragmentByTag(FB_FRAGMENT_TAG);
                    if(fragment != null)
                        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                }else{
                    Fragment fragment = getSupportFragmentManager().findFragmentByTag(ACCOUNT_FRAGMENT_TAG);
                    if(fragment != null)
                        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                }
            }
        };
    }

    @Override
    public void onFBFragmentInteraction(boolean result) {
        final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        ft.replace(R.id.fragmentContainer, new AlbumFragment(),ALBUMS_FRAGMENT_TAG);
        ft.commit();

        setTitle(R.string.albums_title);
    }

    @Override
    public void onAlbumFragmentInteraction(boolean result) {

    }

    @Override
    public void onAccountFragmentInteraction(boolean result) {
        if(result){
            final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

            ft.replace(R.id.fbContainer, new FacebookFragment(),FB_FRAGMENT_TAG);
            ft.commit();

            setTitle(R.string.app_name);

        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }
}
