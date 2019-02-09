package com.maiajam.bankblood.ui.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.maiajam.bankblood.data.model.newPass.NewPass;
import com.maiajam.bankblood.data.retrofit.ApiService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.maiajam.bankblood.data.retrofit.ApiServer.getClient;

public class ResetPassFragment extends Fragment {


    ApiService apiService;
    @BindView(R.id.ResetPass_img_Logo)
    ImageView ResetPassImgLogo;
    @BindView(R.id.ResetPass_Txt_ResetPass)
    TextView ResetPassTxtResetPass;
    @BindView(R.id.ResetPass_ET_PinCode)
    EditText ResetPassETPinCode;
    @BindView(R.id.ResetPass_ET_NewPass)
    EditText ResetPassETNewPass;
    @BindView(R.id.ResetPass_ET_ConfirmNewPass)
    EditText ResetPassETConfirmNewPass;
    @BindView(R.id.ResetPass_B_Send)
    Button ResetPassBSend;
    Unbinder unbinder;

    public ResetPassFragment() {

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

        View v = inflater.inflate(R.layout.fragment_reset_password, container,false);
        unbinder = ButterKnife.bind(this, v);
        return v;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }


    @OnClick(R.id.ResetPass_B_Send)
    public void onViewClicked() {
        ResetPass();
    }

    private void ResetPass() {

        String PinCode = ResetPassETPinCode.getText().toString();
        String NewPass = ResetPassETNewPass.getText().toString();
        String ConfirmPass = ResetPassETConfirmNewPass.getText().toString();

        if(TextUtils.isEmpty(PinCode))
        {
            Toast.makeText(getContext(),getResources().getString(R.string.NoPinCode),Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(NewPass))
        {
            Toast.makeText(getContext(),getResources().getString(R.string.NoPass),Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(ConfirmPass))
        {
            Toast.makeText(getContext(),getResources().getString(R.string.NoConfirmPass),Toast.LENGTH_LONG).show();
            return;
        }
        if(!NewPass.equals(ConfirmPass))
        {
            Toast.makeText(getContext(),getResources().getString(R.string.ConfirmPass),Toast.LENGTH_LONG).show();
            return;
        }else {
            apiService.newPassword(NewPass,ConfirmPass,PinCode).enqueue(new Callback<com.maiajam.bankblood.data.model.newPass.NewPass>() {
                @Override
                public void onResponse(Call<com.maiajam.bankblood.data.model.newPass.NewPass> call, Response<com.maiajam.bankblood.data.model.newPass.NewPass> response) {

                    if(response.body().getStatus() == 1)
                    {
                        Toast.makeText(getContext(),response.body().getMsg(),Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(getContext(),response.body().getMsg(),Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<com.maiajam.bankblood.data.model.newPass.NewPass> call, Throwable t) {

                }
            });
        }
    }
}
