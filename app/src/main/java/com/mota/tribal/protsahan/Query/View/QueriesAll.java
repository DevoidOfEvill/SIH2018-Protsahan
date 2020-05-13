package com.mota.tribal.protsahan.Query.View;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.mota.tribal.protsahan.Login.SQLiteHandler;
import com.mota.tribal.protsahan.Query.Model.Data.QueryData;
import com.mota.tribal.protsahan.Query.Model.MockQueryProvider;
import com.mota.tribal.protsahan.Query.Model.RetrofitQueryProvider;
import com.mota.tribal.protsahan.Query.Presenter.QueryPresenter;
import com.mota.tribal.protsahan.Query.Presenter.QueryPresenterImpl;
import com.mota.tribal.protsahan.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link QueriesAll.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link QueriesAll#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QueriesAll extends Fragment implements QueryView {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;
    private RecyclerView recyclerView;
    private QueryPresenter presenter;
    private ProgressBar progressBar;
    private SQLiteHandler db;
    private String username, token;
    private View view;

    public QueriesAll() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QueriesAll.
     */
    // TODO: Rename and change types and number of parameters
    public static QueriesAll newInstance(String param1, String param2) {
        QueriesAll fragment = new QueriesAll();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void showMessage(String message) {
        Snackbar.make(view.findViewById(R.id.all_queries_rel_layout), message, Snackbar.LENGTH_LONG).
                setAction("Action", null).show();
    }

    @Override
    public void onGettingAllQueries(QueryData data) {
        QueryAdapter adapter = new QueryAdapter(getContext(), data.getQueries());
        recyclerView.setAdapter(adapter);
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
        view = inflater.inflate(R.layout.fragment_queries, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        progressBar = view.findViewById(R.id.progress_bar);

//        presenter = new QueryPresenterImpl(new RetrofitQueryProvider(), this, getContext());
        presenter = new QueryPresenterImpl(new RetrofitQueryProvider(), this, getContext());
        presenter.getAllQueries(username, token);
        db = new SQLiteHandler(getContext());
        String id = db.getUser().get_id();
        token = db.getUser().getToken();
        final SwipeRefreshLayout mySwipeRefreshLayout = new SwipeRefreshLayout(getContext());
        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        presenter.getAllQueries(username, token);
                        mySwipeRefreshLayout.setRefreshing(false);
                    }
                }
        );

        return view;
    }

    @Override
    public void showProgressBar(boolean b) {
        if (b)
            progressBar.setVisibility(View.VISIBLE);
        else
            progressBar.setVisibility(View.GONE);
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
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
