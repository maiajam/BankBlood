package com.maiajam.bankblood.ui.fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.maiajam.bankblood.R;
import com.maiajam.bankblood.adapter.MyPagerAdapter;
import com.maiajam.bankblood.data.retrofit.ApiService;
import com.maiajam.bankblood.helper.Constant;
import com.maiajam.bankblood.helper.HelperMethodes;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.maiajam.bankblood.data.retrofit.ApiServer.getClient;

public class ArticalsAndRequestTabsFragment extends Fragment {


    ApiService apiService;


    MyPagerAdapter myPagerAdapter;
    int ForArticals = 0;
    @BindView(R.id.PageTabs)
    TabLayout PageTabs;
    @BindView(R.id.tab_viewPager)
    ViewPager tabViewPager;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    Unbinder unbinder;


    public ArticalsAndRequestTabsFragment() {
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        apiService = getClient().create(ApiService.class);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tabs, container, false);
        unbinder = ButterKnife.bind(this, v);

        myPagerAdapter = new MyPagerAdapter(getActivity().getSupportFragmentManager());


        myPagerAdapter.addPager(new ArticlesAndDonateReqFragment(), "المقالات");
        myPagerAdapter.addPager(new ArticlesAndDonateReqFragment(), getString(R.string.tabNameReq));

        tabViewPager.setAdapter(myPagerAdapter);
        PageTabs.setupWithViewPager(tabViewPager);




        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    public void setForArticals() {
        ForArticals = Constant.IsArticlas;
    }

    @OnClick(R.id.fab)
    public void onViewClicked() {
        HelperMethodes.beginTransaction(getFragmentManager().beginTransaction(), new NewReqFragment(), R.id.HomeActivity_Frame, null);
    }


}
