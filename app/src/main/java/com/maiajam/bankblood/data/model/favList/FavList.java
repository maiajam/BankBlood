
package com.maiajam.bankblood.data.model.favList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FavList {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private FavListData data;

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

    public FavListData getData() {
        return data;
    }

    public void setData(FavListData data) {
        this.data = data;
    }

}
