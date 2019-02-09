package com.maiajam.bankblood.ui.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.maiajam.bankblood.R;
import com.maiajam.bankblood.data.model.donatesRequest.DonReqDatum;
import com.maiajam.bankblood.data.model.donationDet.DonationDet;
import com.maiajam.bankblood.data.retrofit.ApiService;
import com.maiajam.bankblood.helper.HelperMethodes;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.maiajam.bankblood.data.retrofit.ApiServer.getClient;

public class ReqDetialsFragment extends Fragment implements OnMapReadyCallback {


    Unbinder unbinder;
    @BindView(R.id.ReqDetial_Txtname)
    TextView ReqDetialTxtname;
    @BindView(R.id.ReqDetial_name)
    TextView ReqDetialName;
    @BindView(R.id.ReqDetial_TxtAge)
    TextView ReqDetialTxtAge;
    @BindView(R.id.ReqDetial_Age)
    TextView ReqDetialAge;
    @BindView(R.id.ReqDetial_TxtBloodType)
    TextView ReqDetialTxtBloodType;
    @BindView(R.id.ReqDetial_BloodType)
    TextView ReqDetialBloodType;
    @BindView(R.id.ReqDetial_TxtNoNeeds)
    TextView ReqDetialTxtNoNeeds;
    @BindView(R.id.ReqDetial_NoNeeds)
    TextView ReqDetialNoNeeds;
    @BindView(R.id.ReqDetial_txtHospName)
    TextView ReqDetialTxtHospName;
    @BindView(R.id.ReqDetial_Hosp)
    TextView ReqDetialHosp;
    @BindView(R.id.ReqDetial_txtHospAdress)
    TextView ReqDetialTxtHospAdress;
    @BindView(R.id.ReqDetial_HospAdrees)
    TextView ReqDetialHospAdrees;
    @BindView(R.id.ReqDetial_txtphoneNo)
    TextView ReqDetialTxtphoneNo;
    @BindView(R.id.ReqDetial_PhoneNo)
    TextView ReqDetialPhoneNo;
    @BindView(R.id.button)
    Button Call_b;
    Unbinder unbinder1;
    @BindView(R.id.ReqDetialsNotes)
    TextView ReqDetialsNotes;
    @BindView(R.id.mapView)
    MapView mapView;
    private Integer Req_Id;
    GoogleMap mGoogleMap ;
    ApiService apiService;
    LatLng hospLocation;
    private DonReqDatum ReqObj;


    public ReqDetialsFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        apiService = getClient().create(ApiService.class);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_req_detial, container, false);
        unbinder1 = ButterKnife.bind(this, view);

        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        if (HelperMethodes.checkConnection(getActivity())) {
            getRequestDetials();
        } else {
            Toast.makeText(getContext(), getContext().getResources().getString(R.string.NoInternet), Toast.LENGTH_LONG).show();
        }

        setData();

        return view;
    }

    private void setData() {


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder1.unbind();

    }

    @OnClick(R.id.button)
    public void onViewClicked() {
        //call
        if(ReqDetialPhoneNo != null )
        {
            Intent PhoneCall = new Intent(Intent.ACTION_CALL);
            PhoneCall.setData(Uri.parse("tel:0377778888"));
        }else
        {
            Toast.makeText(getContext(),getContext().getResources().getString(R.string.NoInternet),Toast.LENGTH_LONG).show();
        }
    }

    private void getRequestDetials() {

        apiService.getReqDetials(HelperMethodes.getApitToken(getContext()), String.valueOf(Req_Id)).enqueue(new Callback<DonationDet>() {
            @Override
            public void onResponse(Call<DonationDet> call, Response<DonationDet> response) {
                if (response.body().getStatus() == 1) {

                    ReqDetialName.setText(response.body().getData().getPatientName());
                    ReqDetialAge.setText(response.body().getData().getPatientAge());
                    ReqDetialBloodType.setText(response.body().getData().getBloodType());
                    ReqDetialHosp.setText(response.body().getData().getHospitalName());
                    ReqDetialNoNeeds.setText(response.body().getData().getBagsNum());
                    ReqDetialHospAdrees.setText(response.body().getData().getHospitalAddress());
                    ReqDetialPhoneNo.setText(response.body().getData().getPhone());
                    ReqDetialsNotes.setText(response.body().getData().getNotes());

                   hospLocation = new LatLng(Double.parseDouble(response.body().getData().getLatitude()),
                           Double.parseDouble(response.body().getData().getLongitude()));

                } else {
                    Toast.makeText(getContext(), response.body().getMsg(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<DonationDet> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void setId(Integer id) {
        this.Req_Id = id;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        mGoogleMap.addMarker(new MarkerOptions().position(hospLocation));
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hospLocation, 10));
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    public void sendObjReq(DonReqDatum req_obj) {

     ReqObj = req_obj ;
    }
}
