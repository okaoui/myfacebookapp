package com.android.tutotials.facebooklogintest;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AlbumFragment extends Fragment {


    private OnFragmentInteractionListener mListener;

    private ListView albumListView;
    private AlbumListAdapter albumListAdapter;
    private ArrayList<FacebookAlbum> fbAlbums;


    public AlbumFragment() {
        // Required empty public constructor
    }


    public static AlbumFragment newInstance(String param1, String param2) {
        AlbumFragment fragment = new AlbumFragment();
        Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFacebookAlbums();
        clearAlbumsOnLogout();


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_album, container, false);
        albumListView = (ListView)v.findViewById(R.id.albums_list);
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


    private void getFacebookAlbums(){

        if(AccessToken.getCurrentAccessToken() != null) {

            new GraphRequest(
                    AccessToken.getCurrentAccessToken(),
                    "/" + AccessToken.getCurrentAccessToken().getUserId() + "/albums",
                    null,
                    HttpMethod.GET,
                    new GraphRequest.Callback() {
                        public void onCompleted(GraphResponse response) {
                                    /* handle the result */
                            //Log.i("Result",response.getJSONObject().toString());
                            try {
                                if (response.getError() == null) {
                                    JSONObject jsObject = response.getJSONObject();
                                    if (jsObject.has("data")) {
                                        JSONArray jaData = jsObject.optJSONArray("data");
                                        fbAlbums = new ArrayList<>();
                                        for (int i = 0; i < jaData.length(); i++) {
                                            JSONObject jobject = jaData.getJSONObject(i);
                                            FacebookAlbum fbAlbum = new FacebookAlbum(jobject.getLong("id"),
                                                    UtilityClass.getDate(jobject.getString("created_time")),
                                                    jobject.getString("name"));

                                            Log.i("Size: ", "" + fbAlbum.toString());
                                            fbAlbums.add(fbAlbum);
                                        }

                                        albumListAdapter = new AlbumListAdapter(getActivity(), fbAlbums);
                                        albumListView.setAdapter(albumListAdapter);

                                    } else {
                                        Toast.makeText(getActivity(), R.string.no_albums_were_found, Toast.LENGTH_SHORT).show();
                                    }
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
            ).executeAsync();
        }
    }

    private void clearAlbumsOnLogout(){
        new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {

                if (currentAccessToken == null){
                    fbAlbums.clear();
                    albumListAdapter.notifyDataSetInvalidated();
                }
            }
        };
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onAlbumFragmentInteraction(boolean result);
    }

    private void sendResult(boolean flag){
        mListener.onAlbumFragmentInteraction(flag);
    }
}
