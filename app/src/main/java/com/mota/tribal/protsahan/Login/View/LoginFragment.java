package com.mota.tribal.protsahan.Login.View;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.mota.tribal.protsahan.Helper.ConnectivityReceiver;
import com.mota.tribal.protsahan.Helper.Urls;
import com.mota.tribal.protsahan.Login.Api.FCM_Api;
import com.mota.tribal.protsahan.Login.Model.Data.ResponseData;
import com.mota.tribal.protsahan.Login.Model.Data.UserInfo;
import com.mota.tribal.protsahan.Login.Model.RetrofitLogin;
import com.mota.tribal.protsahan.Login.Presenter.LoginPresenter;
import com.mota.tribal.protsahan.Login.Presenter.LoginPresenterImpl;
import com.mota.tribal.protsahan.Login.SQLiteHandler;
import com.mota.tribal.protsahan.Profile.View.ProfileActivity;
import com.mota.tribal.protsahan.R;
import com.mota.tribal.protsahan.Upload.UploadActivity;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginFragment extends Fragment implements LoginView {

    private SessionManager session;
    private EditText etUsername;
    private EditText etPassword;
    private Button bLogin;
    private TextView forgotpassword;
    private SQLiteHandler db;
    private LoginPresenter presenter;
    private ProgressBar progressBar;
    private ConnectivityReceiver connectivityReceiver;
    private ProgressDialog pDialog;
    private OnFragmentInteractionListener mListener;
    private Retrofit retrofit;

    public LoginFragment() {
    }

    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
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

        progressBar = getView().findViewById(R.id.progress_bar);
        connectivityReceiver = new ConnectivityReceiver();
        /*if (!connectivityReceiver.isConnected())
            Snackbar.make(findViewById(R.id.login), "Connection Error", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();*/
        session = new SessionManager(getContext());
        pDialog = new ProgressDialog(getContext());

        session.setLogin(false);
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            SQLiteHandler sqLiteHandler = new SQLiteHandler(getContext());
            UserInfo user = sqLiteHandler.getUser();
            Intent intent = new Intent(getActivity(), UploadActivity.class);
            startActivity(intent);
        }
        etUsername = getView().findViewById(R.id.etUsername);
        etPassword = getView().findViewById(R.id.etPassword);
        bLogin = getView().findViewById(R.id.bLogin);
        forgotpassword = getView().findViewById(R.id.forgotpassword);

        /*forgotpassword.setOnClickListener(new QueryView.OnClickListener() {
            @Override
            public void onClick(QueryView v) {
                final String email = etUsername.getText().toString().trim();
                setForgotpassword();

            }
        });*/
        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final String email = etUsername.getText().toString().trim();
                final String password = etPassword.getText().toString().trim();
                Log.d("Ayush", email + "    " + password);
                if (email.length() == 10) {
                    presenter = new LoginPresenterImpl(new RetrofitLogin(), LoginFragment.this, getContext());
                    presenter.getResponse(email, password);
                } else
                    showMessage("Please Input a 10 digit Mobile Number");
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
       /* if (context instanceof OnFragmentInteractionListener) {
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

    @Override
    public void showProgressBar(boolean b) {
        if (b)
            progressBar.setVisibility(View.VISIBLE);
        else
            progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showMessage(String message) {
        Snackbar.make(getView().findViewById(R.id.login), message, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Override
    public void showUserDetails(UserInfo data) {

        db = new SQLiteHandler(getContext());
        db.deleteUsers();
        db.addUser(data.getUsername(), data.getToken(), data.get_id());
        session = new SessionManager(getContext());
        session.setLogin(true);
        SQLiteHandler db = new SQLiteHandler(getContext());
        UserInfo userInfo = db.getUser();
        String token = FirebaseInstanceId.getInstance().getToken();
        FCM_Api api;
        api = retrofit.create(FCM_Api.class);
        Call<ResponseData> call = api.getResponse(userInfo.getUsername(), token);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                if (response.body().isSuccess()) {
                    progressBar.setVisibility(View.GONE);
                    Intent intent = new Intent(getActivity().getApplication(), ProfileActivity.class);
                    startActivity(intent);

                } else {
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
            }
        });
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
