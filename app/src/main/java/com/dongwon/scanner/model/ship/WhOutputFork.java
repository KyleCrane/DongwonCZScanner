package com.dongwon.scanner.model.ship;

import com.dongwon.scanner.sql.RetrofitResult;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class WhOutputFork extends RetrofitResult implements Serializable {
    @SerializedName("SHIP_NO")
    private String shipNo;
    @SerializedName("LOCT_NAME")
    private  String loctName;
    @SerializedName("ORD_STATUS")
    private String ordrStatus;
    @SerializedName("CAR_NO")
    private String carNo;
    @SerializedName("LOCT_CODE")
    private String loctCode;

    public WhOutputFork(String shipNo,String loctName,String ordrStatus,String carNo,String loctCode){
        this.loctName = loctName;
        this.ordrStatus = ordrStatus;
        this.shipNo = shipNo;
        this.carNo = carNo;
        this.loctCode = loctCode;
    }

    public String getShipNo() {  return shipNo;   }
    public void setShipNo(String shipNo) { this.shipNo = shipNo; }

    public String getLoctName() {   return loctName;  }
    public void setLoctName(String loctName) {   this.loctName = loctName;   }

    public String getOrdrStatus() {  return ordrStatus;  }
    public void setOrdrStatus(String ordrStatus) {  this.ordrStatus = ordrStatus;  }

    public String getCarNo() {return carNo;}
    public void setCarNo(String carNo) {this.carNo = carNo;}

    public String getLoctCode() {return loctCode;}
    public void setLoctCode(String loctCode) {this.loctCode = loctCode;}
}
