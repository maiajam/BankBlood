
package com.maiajam.bankblood.data.model.newRequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewRequest {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private NewrReqData data;

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

    public NewrReqData getData() {
        return data;
    }

    public void setData(NewrReqData data) {
        this.data = data;
    }

}
