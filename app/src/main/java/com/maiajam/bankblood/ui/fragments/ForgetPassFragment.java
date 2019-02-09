package com.maiajam.bankblood.ui.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.maiajam.bankblood.R;
import com.maiajam.bankblood.data.model.resetPassword.ResetPassWord;
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

public class ForgetPassFragment extends Fragment {


    ApiService apiService;
    @BindView(R.id.ForgetPass_img_Logo)
    ImageView ForgetPassImgLogo;
    @BindView(R.id.ForgetPass_Txt_ForgetPass)
    TextView ForgetPassTxtForgetPass;
    @BindView(R.id.ForgetPass_ET_PhoneNo)
    EditText ForgetPassETPhoneNo;
    @BindView(R.id.ForgetPass_B_Send)
    Button ForgetPassBSend;
    Unbinder unbinder;

    public ForgetPassFragment() {
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiService = getClient().create(ApiService.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_forget_password, container,false);

        unbinder = ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.ForgetPass_B_Send)
    public void onViewClicked() {
        String PhoneNo = ForgetPassETPhoneNo.getText().toString();

        if(TextUtils.isEmpty(PhoneNo))
        {
            Toast.makeText(getContext(),getContext().getResources().getString(R.string.NoPhoneNo),Toast.LENGTH_LONG).show();
            return;
        }else {

            apiService.resetPassword(PhoneNo).enqueue(new Callback<ResetPassWord>() {
                @Override
                public void onResponse(Call<ResetPassWord> call, Response<ResetPassWord> response) {

                    if(response.body().getStatus() == 1)
                    {
                        Toast.makeText(getContext(),response.body().getMsg(),Toast.LENGTH_LONG).show();
                        HelperMethodes.beginTransaction(getFragmentManager().beginTransaction(),new ResetPassFragment(),R.id.MainLogin_Frame,null);
                    }else
                    {
                        Toast.makeText(getContext(),response.body().getMsg(),Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ResetPassWord> call, Throwable t) {

                }
            });
        }
    }


}
