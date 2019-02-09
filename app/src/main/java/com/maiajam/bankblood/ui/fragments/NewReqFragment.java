package com.maiajam.bankblood.ui.fragments;


import android.location.Address;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.maiajam.bankblood.R;
import com.maiajam.bankblood.data.model.listOfCities.ListOfCities;
import com.maiajam.bankblood.data.model.listOfCities.ListOfCitiyDatum;
import com.maiajam.bankblood.data.model.listOfgovernorates.Datum;
import com.maiajam.bankblood.data.model.listOfgovernorates.ListOfgovernorates;
import com.maiajam.bankblood.data.retrofit.ApiService;
import com.maiajam.bankblood.helper.GPSTracker;
import com.maiajam.bankblood.helper.HelperMethodes;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.maiajam.bankblood.data.retrofit.ApiServer.getClient;

public class NewReqFragment extends Fragment {


    ApiService apiService;
    @BindView(R.id.NewReq_name)
    EditText NewReqName;
    @BindView(R.id.NewReq_Age)
    EditText NewReqAge;
    @BindView(R.id.NewReq_TxtBloodType)
    EditText NewReqTxtBloodType;
    @BindView(R.id.NewReq_BloodType)
    EditText NewReqBloodType;
    @BindView(R.id.NewReq_TxtNoNeeds)
    EditText NewReqTxtNoNeeds;
    @BindView(R.id.NewReq_NoNeeds)
    EditText NewReqNoNeeds;
    @BindView(R.id.NewReq_txtHospName)
    EditText NewReqTxtHospName;
    @BindView(R.id.NewReq_Hosp)
    EditText NewReqHosp;
    @BindView(R.id.NewReq_Sp_Mohafza)
    Spinner NewReqSpMohafza;
    @BindView(R.id.NewReq_Sp_City)
    Spinner NewReqSpCity;
    @BindView(R.id.NewReq_PhoneNo)
    EditText NewReqPhoneNo;
    @BindView(R.id.NewReqsNotes)
    EditText NewReqsNotes;
    @BindView(R.id.button)
    Button button;
    Unbinder unbinder;

    private List<ListOfCitiyDatum> ListOfCities;
    private ArrayList<String> CitiesList;
    private List<Datum> ListOfGovernorates;
    private ArrayList<String> GovernoratesList;
    private ArrayAdapter _adpater_Spinner2;
    private List<Integer> GovernoratesIdList;
    private List<Integer> CitiesIdList;

    public NewReqFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        apiService = getClient().create(ApiService.class);
//

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_new_request, container,false);
        unbinder = ButterKnife.bind(this, v);

        if(HelperMethodes.checkConnection(getActivity()))
        {
            getGonvernateList();
        }else
        {
            Toast.makeText(getContext(),getResources().getString(R.string.NoInternet),Toast.LENGTH_LONG).show();
        }

        return v;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick(R.id.button)
    public void onViewClicked() {

        String PatientName = NewReqName.getText().toString();
        String PatientAge =NewReqAge.getText().toString();
        String BloodType = NewReqBloodType.getText().toString();
        String bagNo = NewReqNoNeeds.getText().toString();
        String HospName = NewReqTxtHospName.getText().toString();

        GPSTracker gpsTracker = new GPSTracker(getActivity(),getActivity());
        List<Address> location = gpsTracker.getGeocoderAddress(getContext());
        //String HospAdress= New;
      //  apiService.newReqCreat(HelperMethodes.getApitToken(),PatientName,Integer.valueOf(PatientAge),BloodType,bagNo,HospName,HospAdress)
    }

    private void getGonvernateList() {

        apiService.ListOfgovernorates().enqueue(new Callback<ListOfgovernorates>() {
            @Override
            public void onResponse(Call<ListOfgovernorates> call, Response<ListOfgovernorates> response) {
                if(response.body().getStatus()== 1)
                {
                    ListOfGovernorates = response.body().getData();

                    String name = null;
                    GovernoratesList = new ArrayList<>();

                    GovernoratesList.add(0, "المحافظة");
                    for (int i = 1; i < ListOfGovernorates.size(); i++) {
                        name = ListOfGovernorates.get(i).getName();
                        GovernoratesList.add(name);
                        _adpater_Spinner2 = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item,GovernoratesList);
                        NewReqSpMohafza.setAdapter(_adpater_Spinner2);

                        Integer id = ListOfGovernorates.get(i).getId();
                        GovernoratesIdList.add(id);
                    }

                    NewReqSpMohafza.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            getCityList(GovernoratesIdList.get(position));
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                }else {

                }
            }

            @Override
            public void onFailure(Call<ListOfgovernorates> call, Throwable t) {
                Toast.makeText(getContext(),t.getMessage().toString(),Toast.LENGTH_LONG).show();
            }
        });
    }


    private void getCityList(Integer Gov_Id) {

        apiService.ListOfCities(Gov_Id).enqueue(new Callback<ListOfCities>() {
            private ArrayAdapter _adpater_Spinner;

            @Override
            public void onResponse(Call<ListOfCities> call, Response<ListOfCities> response) {
                if(response.body().getStatus()== 1)
                {
                    ListOfCities = response.body().getData();

                    String name = null;
                    CitiesList = new ArrayList<>();

                    CitiesList.add(0, "المدينة");
                    for (int i = 1; i < ListOfCities.size(); i++) {
                        name = ListOfCities.get(i).getName();
                        CitiesList.add(name);
                        _adpater_Spinner = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item,CitiesList);
                        NewReqSpCity.setAdapter(_adpater_Spinner);

                        Integer id = ListOfCities.get(i).getId();
                        CitiesIdList.add(id);
                    }
                }else
                {
                    Toast.makeText(getContext(),response.body().getMsg(),Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<ListOfCities> call, Throwable t) {
                Toast.makeText(getContext(),t.getMessage().toString(),Toast.LENGTH_LONG).show();
            }
        });

    }
}
