package com.maiajam.bankblood.ui.fragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.maiajam.bankblood.R;
import com.maiajam.bankblood.data.model.login.Login;
import com.maiajam.bankblood.data.retrofit.ApiService;
import com.maiajam.bankblood.helper.HelperMethodes;
import com.maiajam.bankblood.ui.activites.HomeActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.maiajam.bankblood.data.retrofit.ApiServer.getClient;

public class LoginFragment extends Fragment {


    @BindView(R.id.Login_ET_PhoneNo)
    EditText LoginETPhoneNo;
    @BindView(R.id.Login_ET_Passworde)
    EditText LoginETPassworde;
    @BindView(R.id.Login_ChBox_RemberMe)
    CheckBox LoginChBoxRemberMe;
    @BindView(R.id.Login_TextView_ForgetPass)
    TextView LoginTextViewForgetPass;
    @BindView(R.id.Login_B_Login)
    Button LoginBLogin;
    @BindView(R.id.Login_B_Reg)
    Button LoginBReg;
    Unbinder unbinder;
    @BindView(R.id.Login_img_Logo)
    ImageView LoginImgLogo;


    ApiService apiService ;
    public LoginFragment() {

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

        View v = inflater.inflate(R.layout.fragment_login, container,false);
        unbinder = ButterKnife.bind(this, v);
        return v;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.Login_ChBox_RemberMe, R.id.Login_B_Login, R.id.Login_B_Reg,R.id.Login_TextView_ForgetPass})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.Login_ChBox_RemberMe:
                break;
            case R.id.Login_TextView_ForgetPass:
                HelperMethodes.beginTransaction(getFragmentManager().beginTransaction(),new ForgetPassFragment(),R.id.MainLogin_Frame,null);
                break;
            case R.id.Login_B_Login:
                if (HelperMethodes.checkConnection(getActivity())) {
                    Login();
                } else {
                    Toast.makeText(getContext(), getResources().getString(R.string.NoInternet), Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.Login_B_Reg:
                if(HelperMethodes.checkConnection(getActivity()))
                {
                    HelperMethodes.beginTransaction(getFragmentManager().beginTransaction(),new RegisterFragment(),R.id.MainLogin_Frame,null);
                }else
                {
                    Toast.makeText(getContext(),getResources().getString(R.string.NoInternet),Toast.LENGTH_LONG).show();
                }
                break;
        }
    }


    private void Login() {
        String Email = LoginETPhoneNo.getText().toString();
        String Pass = LoginETPassworde.getText().toString();

        if(!TextUtils.isEmpty(Email))
        {
            if(!TextUtils.isEmpty(Pass))
            {
                if(LoginChBoxRemberMe.isChecked())
                {
                    HelperMethodes.remeberMe(getContext(),Email,Pass);
                }
                apiService.Login(Email,Pass).enqueue(new Callback<Login>() {
                    @Override
                    public void onResponse(Call<Login> call, Response<Login> response) {
                        if(response.body().getStatus() == 1)
                        {

                            HelperMethodes.setApiToken(response.body().getData().getApiToken(),getContext());
                            startActivity(new Intent(getContext(),HomeActivity.class));
                          //  HelperMethodes.beginTransaction(getFragmentManager().beginTransaction(),new ArticalsAndRequestTabsFragment(),R.id.MainLogin_Frame,null);

                        }else
                        {
                            Toast.makeText(getContext(),response.body().getMsg(),Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<Login> call, Throwable t) {
                    }
                });
            }else
            {
                Toast.makeText(getContext(),getResources().getString(R.string.NoInternet),Toast.LENGTH_LONG).show();
                return;
            }

        }else {
            Toast.makeText(getContext(),getResources().getString(R.string.EnterEmail),Toast.LENGTH_LONG).show();
            return;
        }
    }
}
