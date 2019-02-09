
package com.maiajam.bankblood.data.model.donationDet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DonationDet {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private DonatinDetData data;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DonatinDetData getData() {
        return data;
    }

    public void setData(DonatinDetData data) {
        this.data = data;
    }

}
