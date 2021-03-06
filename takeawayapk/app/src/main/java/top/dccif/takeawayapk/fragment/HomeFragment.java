package top.dccif.takeawayapk.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONObject;

import java.util.List;

import top.dccif.takeawayapk.R;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

//    private LoadMoreRecyclerView loadMoreRecyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
//    private ItemRecyclerViewAdapter myItemRecyclerViewAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment HomeFragment.
     */
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_widget);

//        loadMoreRecyclerView = (LoadMoreRecyclerView)view.findViewById(R.id.recycler_view);
//
//        myItemRecyclerViewAdapter = new ItemRecyclerViewAdapter(ShopUtils.ShopList.initialContents);
//
//        loadMoreRecyclerView.setAdapter(myItemRecyclerViewAdapter);
//        loadMoreRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        loadMoreRecyclerView.setAutoLoadMoreEnable(true);
//        loadMoreRecyclerView.setHeaderEnable(true);
//        loadMoreRecyclerView.setHasFixedSize(true);
//        loadMoreRecyclerView.addHeaderView(R.layout.homepage_entries);
//        loadMoreRecyclerView.setLoadMoreListener(new LoadMoreRecyclerView.LoadMoreListener() {
//            @Override
//            public void onLoadMore() {
//                loadMoreRecyclerView.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        LoadMoreTask loadMoreTask = new LoadMoreTask();
//                        loadMoreTask.execute();
//                    }
//                },100);
//            }
//        });
//        myItemRecyclerViewAdapter.setOnItemClickListener(new ItemRecyclerViewAdapter.OnRecyclerViewItemClickListener() {
//            @Override
//            public void onItemClick(View view, JSONObject data) {
//                Intent intent = new Intent(getActivity(),ShopActivity.class);
//                intent.putExtra("json",data.toString());
//                startActivity(intent);
//            }
//        });
//        loadMoreRecyclerView.notifyMoreFinish(ShopUtils.ShopList.mHasMore);
        return view;
    }
    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class LoadMoreTask extends AsyncTask<Void, Void, List<JSONObject>> {

        boolean hasMore = false;

        LoadMoreTask() {
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<JSONObject> doInBackground(Void... params) {
            List<JSONObject> result=null;
            try {
//                result = ShopUtils.ShopList.getShops();
//                hasMore = ShopUtils.ShopList.hasMore();
            } catch (Exception e) {
                return null;
            }
            return result;
        }

        @Override
        protected void onPostExecute(final List<JSONObject> result) {

            if (result!=null) {
                swipeRefreshLayout.setRefreshing(false);
//                myItemRecyclerViewAdapter.addData(result);
//                loadMoreRecyclerView.notifyMoreFinish(hasMore);
            }
        }

        @Override
        protected void onCancelled() {
        }
    }
}
