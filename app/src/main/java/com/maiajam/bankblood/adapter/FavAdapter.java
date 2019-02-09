package com.maiajam.bankblood.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.maiajam.bankblood.R;
import com.maiajam.bankblood.data.model.favList.Datum;
import com.maiajam.bankblood.data.model.toggleFav.TogglePost;
import com.maiajam.bankblood.data.retrofit.ApiService;
import com.maiajam.bankblood.helper.Constant;
import com.maiajam.bankblood.helper.HelperMethodes;
import com.maiajam.bankblood.ui.fragments.PostDetailsFragment;
import com.maiajam.bankblood.ui.fragments.ReqDetialsFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.maiajam.bankblood.data.retrofit.ApiServer.getClient;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.myHolder> {

    Context context;
    List<Datum> FavList;
    ApiService apiService ;

    public FavAdapter(Context con, List<Datum> articalsList) {

        context = con;
        FavList = articalsList;
    }

    @NonNull
    @Override
    public myHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.item_articals, parent, false);
        myHolder holder = new myHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final myHolder holder, int position) {

        final Datum FavObject = FavList.get(position);
      

        String Title = FavObject.getTitle();


        if(FavObject.getThumbnail()!= null)
        {
            Glide.with(context).load(FavObject.getThumbnail()).apply(new RequestOptions().override(50,50).centerCrop()).into(holder.ArticalsReq_Img_Photo);
        }

        holder.ArticalsReq_Img_Fav.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_favlove));


        holder.ArticalsReq_Img_Fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                apiService.togglePost(String.valueOf(FavObject.getId()),HelperMethodes.getApitToken(context)).enqueue(new Callback<TogglePost>() {
                    @Override
                    public void onResponse(Call<TogglePost> call, Response<TogglePost> response) {

                        if(response.body().getStatus() == 1)
                        {
                            Toast.makeText(context,response.body().getMsg(),Toast.LENGTH_LONG).show();
                            holder.ArticalsReq_Img_Fav.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_favlove));
                            notifyDataSetChanged();
                        }else {
                            Toast.makeText(context,response.body().getMsg(),Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<TogglePost> call, Throwable t) {
                        Toast.makeText(context,t.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });

            }
        });

        holder.ArticalsReq_Img_Photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager manager = ((FragmentActivity) context).getSupportFragmentManager();
                PostDetailsFragment f = new PostDetailsFragment();
                f.setPostId(FavObject.getId());
                HelperMethodes.beginTransaction(manager.beginTransaction(),new ReqDetialsFragment(),R.id.HomeActivity_Frame,null);

            }
        });
    }

    @Override
    public int getItemCount() {
        return FavList.size();
    }

    public class myHolder extends RecyclerView.ViewHolder {

        ImageView ArticalsReq_Img_Fav,ArticalsReq_Img_Photo;
        TextView ArticalsReq_Txt_Title;

        public myHolder(View itemView) {
            super(itemView);

            ArticalsReq_Img_Fav = (ImageView)itemView.findViewById(R.id.Articals_Item_Img_fav);
            ArticalsReq_Img_Photo =  (ImageView)itemView.findViewById(R.id.Articalst_Item_Img_Photo);
            ArticalsReq_Txt_Title =  (TextView) itemView.findViewById(R.id.Articals_Item_Txt_Title);

        }
    }
}
