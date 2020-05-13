package com.mota.tribal.protsahan.SignUp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.mota.tribal.protsahan.Helper.Urls;
import com.mota.tribal.protsahan.R;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignupFragment extends Fragment {

    ProgressBar progressBar;
    private OnFragmentInteractionListener mListener;
    private Button bsignup;
    private EditText username;
    private EditText password;
    private EditText passwordcon;
    private Retrofit retrofit;
    private Api api;

    public SignupFragment() {
        // Required empty public constructor
    }


    public static SignupFragment newInstance(String param1, String param2) {
        SignupFragment fragment = new SignupFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().
                connectTimeout(15000, java.util.concurrent.TimeUnit.SECONDS).
                readTimeout(15000, java.util.concurrent.TimeUnit.SECONDS).
                writeTimeout(15000, java.util.concurrent.TimeUnit.SECONDS).
                addInterceptor(interceptor).build();
        retrofit = new Retrofit.Builder()
                .baseUrl(Urls.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bsignup = getView().findViewById(R.id.bsignup);
        password = getView().findViewById(R.id.password);
        passwordcon = getView().findViewById(R.id.passwordcon);
        username = getView().findViewById(R.id.email);
        progressBar = getView().findViewById(R.id.progress_bar);
        bsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (username.getText().toString().trim().length() != 10) {
                    Snackbar.make(getView().findViewById(R.id.signup), "Please Input a 10 digit mobile number!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else if (password.getText().toString().equals(passwordcon.getText().toString())) {
                    progressBar.setVisibility(View.VISIBLE);
                    api = retrofit.create(Api.class);
                    Call<Data> call = api.getResponse(username.getText().toString(), password.getText().toString());
                    call.enqueue(new Callback<Data>() {
                        @Override
                        public void onResponse(Call<Data> call, Response<Data> response) {

                            Log.d("Ayush", response.toString());
                            if (response.body().isSuccess()) {
                                progressBar.setVisibility(View.GONE);
                                Snackbar.make(getView().findViewById(R.id.signup), getString(R.string.success), Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();

                            } else {
                                progressBar.setVisibility(View.GONE);
                                Snackbar.make(getView().findViewById(R.id.signup), response.body().getMessage(), Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Data> call, Throwable t) {
                            progressBar.setVisibility(View.GONE);
                            Snackbar.make(getView().findViewById(R.id.signup), getString(R.string.failed), Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    });
                } else {
                    Snackbar.make(getView().findViewById(R.id.signup), "Password do not match!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
