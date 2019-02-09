package com.maiajam.bankblood.ui.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.maiajam.bankblood.R;
import com.maiajam.bankblood.data.model.listOfCities.ListOfCities;
import com.maiajam.bankblood.data.model.listOfCities.ListOfCitiyDatum;
import com.maiajam.bankblood.data.model.listOfgovernorates.Datum;
import com.maiajam.bankblood.data.model.listOfgovernorates.ListOfgovernorates;
import com.maiajam.bankblood.data.model.register.Register;
import com.maiajam.bankblood.data.retrofit.ApiService;
import com.maiajam.bankblood.helper.Constant;
import com.maiajam.bankblood.helper.HelperMethodes;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.maiajam.bankblood.data.retrofit.ApiServer.getClient;

public class RegisterFragment extends Fragment {

    @BindView(R.id.RegisterFragment_ET_Name)
    EditText RegisterFragmentETName;
    @BindView(R.id.RegisterFragment_ET_Email)
    EditText RegisterFragmentETEmail;
    @BindView(R.id.RegisterFragment_ET_BirthDay)
    EditText RegisterFragmentETBirthDay;
    @BindView(R.id.RegisterFragment_ET_BloodType)
    EditText RegisterFragmentETBloodType;
    @BindView(R.id.RegisterFragment_ET_LastDate)
    EditText RegisterFragmentETLastDate;
    @BindView(R.id.RegisterFragment_Sp_Region)
    Spinner RegisterFragmentSpRegion;
    @BindView(R.id.RegisterFragment_Sp_City)
    Spinner RegisterFragmentSpCity;
    @BindView(R.id.RegisterFragment_ET_PhoneNo)
    EditText RegisterFragmentETPhoneNo;
    @BindView(R.id.RegisterFragment_ET_Pass)
    EditText RegisterFragmentETPass;
    @BindView(R.id.RegisterFragment_ET_ConfiPass)
    EditText RegisterFragmentETConfiPass;
    @BindView(R.id.RegisterFragment_B_Reg)
    Button RegisterFragmentBReg;
    Unbinder unbinder;

    ApiService apiService;


    Calendar myCalendar = Calendar.getInstance();
    @BindView(R.id.SimpleToolBar_Img_Back)
    ImageView SimpleToolBarImgBack;
    @BindView(R.id.SimpleToolBar_Txt_Title)
    TextView SimpleToolBarTxtTitle;
    @BindView(R.id.SimpleToolBar_Img_Notifcation)
    ImageView SimpleToolBarImgNotifcation;
    @BindView(R.id.SimpleToolBar_B_NotifInd)
    Button SimpleToolBarBNotifInd;
    @BindView(R.id.SimpleToolBar_ReL_Notification)
    RelativeLayout SimpleToolBarReLNotification;
    @BindView(R.id.simpleToolbar)
    Toolbar simpleToolbar;


    private List<ListOfCitiyDatum> ListOfCities;
    private List<Datum> ListOfGovernorates;
    private List<Integer> GovernateIdList;
    private List<String> CitiesList;
    private ArrayAdapter _adpater_Spinner;
    private ArrayList<String> GovernoratesList;
    private int cityId;
    int isEditIndfo;
    private int pos;
    private String selectedCity;
    private Integer selectedRegion;
    private List<Integer> CityListId = new ArrayList<>();
    private Integer CityId;
    private int regionid;

    public void SetEditInfo(ApiService apiService) {
        this.isEditIndfo = Constant.IsEditInfo;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiService = getClient().create(ApiService.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_register, container, false);
        unbinder = ButterKnife.bind(this, v);
        if (HelperMethodes.checkConnection(getActivity())) {
            getGonvernateList();

            if (isEditIndfo == Constant.IsEditInfo) {
                getUserInfo();
                RegisterFragmentBReg.setText(getString(R.string.update));
            }
        } else {
            Toast.makeText(getContext(), getContext().getResources().getString(R.string.NoInternet), Toast.LENGTH_LONG).show();
        }


        return v;
    }

    private void getUserInfo() {

      //     apiService.getUserInfo(HelperMethodes.getApitToken())
    }


    private void getGonvernateList() {

        apiService.ListOfgovernorates().enqueue(new Callback<ListOfgovernorates>() {
            @Override
            public void onResponse(Call<ListOfgovernorates> call, Response<ListOfgovernorates> response) {
                if (response.body().getStatus() == 1) {
                    ListOfGovernorates = response.body().getData();

                    String name = null;
                    GovernoratesList = new ArrayList<>();
                    GovernateIdList = new ArrayList<>();
                    GovernoratesList.add(0, "المحافظة");
                    for (int i = 0; i < ListOfGovernorates.size(); i++) {

                        name = ListOfGovernorates.get(i).getName();
                        GovernoratesList.add(name);
                        _adpater_Spinner = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, GovernoratesList);
                        RegisterFragmentSpRegion.setAdapter(_adpater_Spinner);
                            Integer id = ListOfGovernorates.get(i).getId();
                            GovernateIdList.add(id);
                    }
                    RegisterFragmentSpRegion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                            getCityList(position);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                } else {

                }
            }

            @Override
            public void onFailure(Call<ListOfgovernorates> call, Throwable t) {
                Log.d("List of governorate", t.getMessage());
                Toast.makeText(getContext(), t.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getCityList(int pos) {

        if(pos!= 0) {

            regionid = GovernateIdList.get(pos - 1) ;

            apiService.ListOfCities(regionid).enqueue(new Callback<ListOfCities>() {
                @Override
                public void onResponse(Call<ListOfCities> call, Response<ListOfCities> response) {
                    if (response.body().getStatus() == 1) {
                        ListOfCities = response.body().getData();

                        String name = null;
                        CitiesList = new ArrayList<>();

                        CitiesList.add(0, "المدينة");
                        for (int i = 0; i < ListOfCities.size(); i++) {
                            name = ListOfCities.get(i).getName();
                            CitiesList.add(name);
                            _adpater_Spinner = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, CitiesList);
                            RegisterFragmentSpCity.setAdapter(_adpater_Spinner);

                            Integer id = ListOfCities.get(i).getId();
                            CityListId.add(id);
                        }
                    } else {
                        Toast.makeText(getContext(), response.body().getMsg(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ListOfCities> call, Throwable t) {

                    Toast.makeText(getContext(), t.getMessage().toString(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.RegisterFragment_ET_BirthDay, R.id.RegisterFragment_ET_LastDate,R.id.SimpleToolBar_Img_Back,R.id.RegisterFragment_B_Reg})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.RegisterFragment_ET_BirthDay:
                new DatePickerDialog(getActivity(), BirthDate, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.RegisterFragment_ET_LastDate:
                new DatePickerDialog(getActivity(), LastDate, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.SimpleToolBar_Img_Back :
                getActivity().onBackPressed();
                break;
            case R.id.RegisterFragment_B_Reg :
                Register();
                break;

        }
    }

    private void Register() {
        String Name = RegisterFragmentETName.getText().toString();
        String Email = RegisterFragmentETEmail.getText().toString();
        String BirthDay = RegisterFragmentETBirthDay.getText().toString();
        String BloodType = RegisterFragmentETBloodType.getText().toString();
        String LastDate = RegisterFragmentETLastDate.getText().toString();

        String Gover = RegisterFragmentSpRegion.getSelectedItem().toString();
        String City = RegisterFragmentSpCity.getSelectedItem().toString();
        String PhoneNo = RegisterFragmentETPhoneNo.getText().toString();
        //String
        String Pass = RegisterFragmentETPass.getText().toString();
        String Confirm = RegisterFragmentETConfiPass.getText().toString();

        if (TextUtils.isEmpty(Name)) {
            Toast.makeText(getContext(), getContext().getResources().getString(R.string.NoName), Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(Email)) {
            Toast.makeText(getContext(), getContext().getResources().getString(R.string.NoEmail), Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(BirthDay)) {
            Toast.makeText(getContext(), getContext().getResources().getString(R.string.NoBirthDay), Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(BloodType)) {
            Toast.makeText(getContext(), getContext().getResources().getString(R.string.NoBloodType), Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(LastDate)) {
            Toast.makeText(getContext(), getContext().getResources().getString(R.string.NoName), Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(BloodType)) {
            Toast.makeText(getContext(), getContext().getResources().getString(R.string.NoName), Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(BloodType)) {
            Toast.makeText(getContext(), getContext().getResources().getString(R.string.NoName), Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(BloodType)) {
            Toast.makeText(getContext(), getContext().getResources().getString(R.string.NoName), Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(Gover )) {
            Toast.makeText(getContext(), getContext().getResources().getString(R.string.NoSelectedRegion), Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(City)) {
            Toast.makeText(getContext(), getContext().getResources().getString(R.string.NoSelectedCity), Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(BloodType)) {
            Toast.makeText(getContext(), getContext().getResources().getString(R.string.NoName), Toast.LENGTH_LONG).show();
            return;
        }

        if (Pass.equals(Confirm)) {

            CityId = CityListId.get(RegisterFragmentSpCity.getSelectedItemPosition());
            apiService.Register(Name, Email, BirthDay, cityId, PhoneNo, LastDate, Pass, Confirm, BloodType).enqueue(new Callback<Register>() {
                @Override
                public void onResponse(Call<Register> call, Response<Register> response) {

                    Toast.makeText(getContext(), response.body().getStatus(), Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<Register> call, Throwable t) {
                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();

                }
            });
        } else {
            Toast.makeText(getContext(), getContext().getResources().getString(R.string.Confirm), Toast.LENGTH_LONG).show();
            return;
        }


    }

    DatePickerDialog.OnDateSetListener BirthDate = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String myFormat = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

            RegisterFragmentETBirthDay.setText(sdf.format(myCalendar.getTime()));
        }

    };

    DatePickerDialog.OnDateSetListener LastDate = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String myFormat = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

            RegisterFragmentETLastDate.setText(sdf.format(myCalendar.getTime()));
        }

    };


}
