package com.mota.tribal.protsahan.Query.View;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.mota.tribal.protsahan.Login.SQLiteHandler;
import com.mota.tribal.protsahan.Query.Model.Data.QueryData;
import com.mota.tribal.protsahan.Query.Model.RetrofitQueryProvider;
import com.mota.tribal.protsahan.Query.Presenter.QueryPresenter;
import com.mota.tribal.protsahan.Query.Presenter.QueryPresenterImpl;
import com.mota.tribal.protsahan.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AskQuestion.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AskQuestion#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AskQuestion extends Fragment implements QueryView {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private ProgressBar progressBar;
    private Button submitButton;
    private QueryPresenter presenter;
    private String question;
    private SQLiteHandler handler;
    private EditText query;

    public AskQuestion() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AskQuestion.
     */
    // TODO: Rename and change types and number of parameters
    public static AskQuestion newInstance(String param1, String param2) {
        AskQuestion fragment = new AskQuestion();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ask_question, container, false);
        handler = new SQLiteHandler(getActivity());
        progressBar = view.findViewById(R.id.progress_bar);
        submitButton = view.findViewById(R.id.submit_query);
        query = view.findViewById(R.id.asked_question);

        presenter = new QueryPresenterImpl(new RetrofitQueryProvider(), this, getActivity());
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (query.getText().toString().equals(""))
                    showMessage(getString(R.string.error_when_no_question));
                else
                    presenter.askQuery(handler.getUser().get_id(), handler.getUser().getToken(), query.getText().toString().trim());
            }
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
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
        Snackbar.make(getView().findViewById(R.id.rel_layout_ask_query), message, Snackbar.LENGTH_LONG).
                setAction("Action", null).show();
    }

    @Override
    public void onGettingAllQueries(QueryData data) {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
