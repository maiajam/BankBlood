package com.maiajam.bankblood.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.maiajam.bankblood.R;
import com.maiajam.bankblood.adapter.ArticalsAdapter;
import com.maiajam.bankblood.adapter.DonateRequestAdapter;
import com.maiajam.bankblood.adapter.FavAdapter;
import com.maiajam.bankblood.adapter.MyPagerAdapter;
import com.maiajam.bankblood.data.model.donatesRequest.DonReqDatum;
import com.maiajam.bankblood.data.model.donatesRequest.DonateReqData;
import com.maiajam.bankblood.data.model.donatesRequest.DonateRequest;
import com.maiajam.bankblood.data.model.favList.FavList;
import com.maiajam.bankblood.data.model.posts.Datum;
import com.maiajam.bankblood.data.model.posts.Posts;
import com.maiajam.bankblood.data.retrofit.ApiService;
import com.maiajam.bankblood.helper.Constant;
import com.maiajam.bankblood.helper.HelperMethodes;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.maiajam.bankblood.data.retrofit.ApiServer.getClient;

public class ArticlesAndDonateReqFragment extends Fragment {


    private  int IsArticlas;
    @BindView(R.id.ArticalsDonate_Sp_SelectBloodType)
    Spinner ArticalsDonateSpSelectBloodType;
    @BindView(R.id.ArticalsDonate_Sp_SelectCity)
    Spinner ArticalsDonateSpSelectCity;
    @BindView(R.id.ArticalsDonate_SV_search)
    SearchView ArticalsDonateSVSearch;
    @BindView(R.id.ArticalsDonate_Lin_TopArticals)
    LinearLayout ArticalsDonateLinTopArticals;
    @BindView(R.id.ArticalsDonate_SV_searchD)
    SearchView ArticalsDonateSVSearchD;
    @BindView(R.id.ArticalsDonate_Lin_DonateReq)
    LinearLayout ArticalsDonateLinDonateReq;
    @BindView(R.id.ArticalsDonate_Rec)
    RecyclerView ArticalsDonateRec;
    Unbinder unbinder;

    ApiService apiService ;
    private List<Datum> ArticalsList;
    private ArticalsAdapter ArticalsAdapter;
    private List<DonReqDatum> ReqList;
    private DonateRequestAdapter ReqAdapter;
    private int ViewMyFavList;
    private List<com.maiajam.bankblood.data.model.favList.Datum> MyFavList;
    private FavAdapter FavAdapter;

    public ArticlesAndDonateReqFragment() {

    }

    public void isArticlas()
    {
        this.IsArticlas = Constant.IsArticlas;
    }

    public void myFavList()
    {
        this.ViewMyFavList = Constant.ViewMyFavList;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        apiService = getClient().create(ApiService.class);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_articls_donate, container,false);
        unbinder = ButterKnife.bind(this, v);
        if(IsArticlas == 1)
        {
            ArticalsDonateLinDonateReq.setVisibility(View.GONE);
            ArticalsDonateLinTopArticals.setVisibility(View.VISIBLE);

            getArticals(Constant.ViewMyFavList);
        }else if(ViewMyFavList == 1)
        {
            ArticalsDonateLinDonateReq.setVisibility(View.GONE);
            ArticalsDonateLinTopArticals.setVisibility(View.VISIBLE);
            getArticals(Constant.ViewMyFavList);
        }else
        {
            ArticalsDonateLinDonateReq.setVisibility(View.VISIBLE);
            ArticalsDonateLinTopArticals.setVisibility(View.GONE);
            getReq();
        }
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    private void getReq() {
        apiService.getDonatesReq(HelperMethodes.getApitToken(getContext())).enqueue(new Callback<DonateRequest>() {
            @Override
            public void onResponse(Call<DonateRequest> call, Response<DonateRequest> response) {
                if(response.body().getStatus()==1)
                {
                    ReqList = response.body().getData().getData();
                    ReqAdapter = new DonateRequestAdapter(getContext(),ReqList);
                    ArticalsDonateRec.setAdapter(ReqAdapter);
                    ArticalsDonateRec.setLayoutManager(new LinearLayoutManager(getContext()));
                    ReqAdapter.notifyDataSetChanged();
                }else {
                    Toast.makeText(getContext(),response.body().getMsg(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<DonateRequest> call, Throwable t) {
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getArticals(final int viewMyFavList) {

        if(ViewMyFavList == Constant.ViewMyFavList)
        {
         
            apiService.getFavList(HelperMethodes.getApitToken(getContext())).enqueue(new Callback<FavList>() {
                @Override
                public void onResponse(Call<FavList> call, Response<FavList> response) {
                    if(response.body().getStatus()== 1)
                    {
                        MyFavList =response.body().getData().getData();
                        FavAdapter = new FavAdapter(getContext(),MyFavList);
                        ArticalsDonateRec.setAdapter(ArticalsAdapter);
                        ArticalsDonateRec.setLayoutManager(new LinearLayoutManager(getContext()));
                        FavAdapter.notifyDataSetChanged();

                    }else {
                        Toast.makeText(getContext(),response.body().getMsg(),Toast.LENGTH_LONG).show();
                    }   
                }

                @Override
                public void onFailure(Call<FavList> call, Throwable t) {

                }
            });
        }else {
            
        }
        apiService.getArticlas(HelperMethodes.getApitToken(getContext()),1).enqueue(new Callback<Posts>() {
            @Override
            public void onResponse(Call<Posts> call, Response<Posts> response) {
                if(response.body().getStatus()== 1)
                {
                    ArticalsList =response.body().getData().getData();
                    ArticalsAdapter = new ArticalsAdapter(getContext(),ArticalsList);
                    ArticalsDonateRec.setAdapter(ArticalsAdapter);
                    ArticalsDonateRec.setLayoutManager(new LinearLayoutManager(getContext()));
                    ArticalsAdapter.notifyDataSetChanged();

                }else {
                    Toast.makeText(getContext(),response.body().getMsg(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Posts> call, Throwable t) {
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
}
