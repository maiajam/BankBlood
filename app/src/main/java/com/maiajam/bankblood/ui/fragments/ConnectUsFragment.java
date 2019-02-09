package com.maiajam.bankblood.ui.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.maiajam.bankblood.R;
import com.maiajam.bankblood.data.model.contactUs.ContactUs;
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

public class ConnectUsFragment extends Fragment {


    @BindView(R.id.ConnectUs_Img_Logo)
    ImageView ConnectUsImgLogo;
    @BindView(R.id.ConnectUs_Txt_PhoneNo)
    TextView ConnectUsTxtPhoneNo;
    @BindView(R.id.ConnectUs_Txt_Email)
    TextView ConnectUsTxtEmail;
    @BindView(R.id.ConnectUs_Img_Gmail)
    ImageView ConnectUsImgGmail;
    @BindView(R.id.ConnectUs_Img_WhatsApp)
    ImageView ConnectUsImgWhatsApp;
    @BindView(R.id.ConnectUs_Img_Instgram)
    ImageView ConnectUsImgInstgram;
    @BindView(R.id.ConnectUs_Img_Youtube)
    ImageView ConnectUsImgYoutube;
    @BindView(R.id.ConnectUs_Img_Twiiter)
    ImageView ConnectUsImgTwiiter;
    @BindView(R.id.ConnectUs_Img_Facebook)
    ImageView ConnectUsImgFacebook;
    @BindView(R.id.ConnectUs_Ed_Title)
    EditText ConnectUsEdTitle;
    @BindView(R.id.ConnectUs_Ed_EmailContentt)
    EditText ConnectUsEdEmailContentt;
    @BindView(R.id.ConnectUs_Button_Send)
    Button ConnectUsButtonSend;
    Unbinder unbinder;

    ApiService apiService;
    @BindView(R.id.SimpleToolBar_Img_Back)
    ImageView SimpleToolBarImgBack;
    @BindView(R.id.SimpleToolBar_Txt_Title)
    TextView SimpleToolBarTxtTitle;
    @BindView(R.id.lin)
    LinearLayout lin;


    public ConnectUsFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        apiService = getClient().create(ApiService.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_connectus, container,false);
        unbinder = ButterKnife.bind(this, v);

        Toolbar toolbar = (Toolbar)v.findViewById(R.id.newToolbar);

        TextView title =(TextView)toolbar.findViewById(R.id.SimpleToolBar_Txt_Title);
        title.setText(getString(R.string.ConnectUs));

        ImageView backImg = (ImageView)toolbar.findViewById(R.id.SimpleToolBar_Img_Back);
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });


        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }
    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }
    @OnClick({R.id.ConnectUs_Img_WhatsApp, R.id.ConnectUs_Img_Instgram,
            R.id.SimpleToolBar_Img_Back, R.id.SimpleToolBar_Txt_Title,
            R.id.ConnectUs_Img_Youtube, R.id.ConnectUs_Img_Twiiter,
            R.id.SimpleToolBar_Img_Notifcation ,
            R.id.ConnectUs_Img_Facebook, R.id.ConnectUs_Button_Send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ConnectUs_Img_WhatsApp:
                break;
            case R.id.ConnectUs_Img_Instgram:
                break;
            case R.id.ConnectUs_Img_Youtube:
                break;
            case R.id.ConnectUs_Img_Twiiter:
                break;
            case R.id.ConnectUs_Img_Facebook:
                break;
            case R.id.ConnectUs_Button_Send:
                SendTheProbelm();
                break;
            case R.id.SimpleToolBar_Img_Back:
                getActivity().onBackPressed();
                break;
            case R.id.SimpleToolBar_B_NotifInd:
                break;
        }
    }

    private void SendTheProbelm() {

        String Title = ConnectUsEdTitle.getText().toString();
        String Content = ConnectUsEdEmailContentt.getText().toString();

        if (TextUtils.isEmpty(Title)) {
            Toast.makeText(getContext(), getResources().getString(R.string.NoTitle), Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(Content)) {
            Toast.makeText(getContext(), getResources().getString(R.string.NoContent), Toast.LENGTH_LONG).show();
            return;
        }

        if (HelperMethodes.checkConnection(getActivity())) {
            apiService.conntactUs(HelperMethodes.getApitToken(getContext()), Title, Content).enqueue(new Callback<ContactUs>() {
                @Override
                public void onResponse(Call<ContactUs> call, Response<ContactUs> response) {
                    Toast.makeText(getContext(), response.body().getMsg(), Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<ContactUs> call, Throwable t) {
                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

        } else {
            Toast.makeText(getContext(), getResources().getString(R.string.NoInternet), Toast.LENGTH_LONG).show();
            return;
        }
    }

}
