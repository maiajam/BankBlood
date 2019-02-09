package com.maiajam.bankblood.adapter;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.maiajam.bankblood.R;
import com.maiajam.bankblood.data.model.donatesRequest.DonReqDatum;


import com.maiajam.bankblood.helper.HelperMethodes;
import com.maiajam.bankblood.ui.fragments.ReqDetialsFragment;

import java.util.List;

public class DonateRequestAdapter extends RecyclerView.Adapter<DonateRequestAdapter.myHolder> {


    Context context ;
   List<DonReqDatum> ReqList;

    public DonateRequestAdapter(Context cont, List<DonReqDatum> reqList) {
        ReqList = reqList ;
        context = cont;
    }

    @NonNull
    @Override
    public myHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_donate_request, parent, false);
        return new myHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull myHolder holder, int position) {

        final DonReqDatum Req_obj = ReqList.get(position);
        holder.DonateReqItem_TV_BloodType.setText(Req_obj.getBloodType());
        holder.DonateReqItem_TV_City.setText(Req_obj.getCity().getName());
        holder.DonateReqItem_TV_Hosp.setText(Req_obj.getHospitalName());
        holder.DonateReqItem_TV_Name.setText(Req_obj.getPatientName());

        holder.DonateReqItem_B_Call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.DonateReqItem_B_Detial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = ((FragmentActivity) context).getSupportFragmentManager();
                ReqDetialsFragment f = new ReqDetialsFragment();
                f.setId(Req_obj.getId());
                f.sendObjReq(Req_obj);
                HelperMethodes.beginTransaction(manager.beginTransaction(),new ReqDetialsFragment(),R.id.HomeActivity_Frame,null);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ReqList.size();
    }

    public class myHolder extends RecyclerView.ViewHolder{
        TextView DonateReqItem_TV_Name,DonateReqItem_TV_Hosp,
                DonateReqItem_TV_City,DonateReqItem_TV_BloodType;

        Button DonateReqItem_B_Call,DonateReqItem_B_Detial;
        public myHolder(View itemView) {
            super(itemView);

            DonateReqItem_B_Call = (Button)itemView.findViewById(R.id.DonateReq_Item_B_Call);
            DonateReqItem_B_Detial = (Button)itemView.findViewById(R.id.DonateReq_Item_B_Detials);

            DonateReqItem_TV_Name = (TextView)itemView.findViewById(R.id.DonateReq_Item_TV_Name);
            DonateReqItem_TV_Hosp = (TextView)itemView.findViewById(R.id.DonateReq_Item_TV_HospName);
            DonateReqItem_TV_City = (TextView)itemView.findViewById(R.id.DonateReq_Item_TV_CityName);
            DonateReqItem_TV_BloodType = (TextView)itemView.findViewById(R.id.DonateReq_Item_TV_BloodType);


        }
    }
}
