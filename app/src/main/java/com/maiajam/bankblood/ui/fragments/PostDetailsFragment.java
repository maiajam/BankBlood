package com.maiajam.bankblood.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.maiajam.bankblood.R;
import com.maiajam.bankblood.data.model.postDetails.PostDetails;
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

public class PostDetailsFragment extends Fragment {


    @BindView(R.id.PostDetails_Img_Photo)
    ImageView PostDetailsImgPhoto;
    @BindView(R.id.PostDetals_Txt_PostTitle)
    TextView PostDetalsTxtPostTitle;
    @BindView(R.id.PostDetals_Img_Fav)
    ImageView PostDetalsImgFav;
    Unbinder unbinder;

    TextView PostContentTxt;


    @BindView(R.id.PostDetails_Txt_PostContent)
    TextView PostDetailsTxtPostContent;

    private Integer PostId;
    ApiService apiService;
    View subView;
    private String postTitle;

    public PostDetailsFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        apiService = getClient().create(ApiService.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_post_details, container, false);
        unbinder = ButterKnife.bind(this, v);

        Toolbar toolbar = (Toolbar)v.findViewById(R.id.toolbar);

        TextView title =(TextView)toolbar.findViewById(R.id.SimpleToolBar_Txt_Title);
        title.setText(postTitle);

        ImageView backImg = (ImageView)toolbar.findViewById(R.id.SimpleToolBar_Img_Back);
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        subView = v.findViewById(R.id.PostDetails_ScrollingText);
        PostContentTxt = (TextView) subView.findViewById(R.id.PostDetails_Txt_PostContent);

        if (HelperMethodes.checkConnection(getActivity())) {
            getPostDetails();
        } else {
            Toast.makeText(getContext(), getContext().getResources().getString(R.string.NoInternet), Toast.LENGTH_LONG).show();
        }
        return v;
    }

    public void setPostId(Integer id) {
        this.PostId = id;
    }

    public void setPostTitle(String title) {
        postTitle = title;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }

    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
    }

    @OnClick(R.id.PostDetals_Img_Fav)
    public void onViewClicked() {
    }

    private void getPostDetails() {

        apiService.getPostDetails(HelperMethodes.getApitToken(getContext()), PostId).enqueue(new Callback<PostDetails>() {
            @Override
            public void onResponse(Call<PostDetails> call, Response<PostDetails> response) {

                if (response.body().getStatus() == 1) {
                    Glide.with(getContext()).load(response.body().getData().getThumbnailFullPath()).apply(new RequestOptions().override(200, 100)).into(PostDetailsImgPhoto);
                    PostDetalsTxtPostTitle.setText(response.body().getData().getTitle());
                    if (response.body().getData().getIsFavourite()) {
                        PostDetalsImgFav.setImageDrawable(getResources().getDrawable(R.drawable.ic_favlove));
                    } else {
                        PostDetalsImgFav.setImageDrawable(getResources().getDrawable(R.drawable.ic_fav));
                    }


                    PostContentTxt.setText(response.body().getData().getContent());

                } else {
                    Toast.makeText(getContext(), response.body().getMsg(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<PostDetails> call, Throwable t) {

                Toast.makeText(getContext(), t.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });

    }


}
