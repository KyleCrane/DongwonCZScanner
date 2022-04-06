package com.dongwon.scanner.model.ship;

import com.dongwon.scanner.sql.RetrofitResult;
import com.google.gson.annotations.SerializedName;

public class WhOutputFork extends RetrofitResult {
    @SerializedName("SHIP_NO")
    private String shipNo;
    @SerializedName("LOCT_NAME")
    private  String loctName;
    @SerializedName("ORD_STATUS")
    private String ordrStatus;

    public WhOutputFork(String shipNo,String loctName,String ordrStatus){
        this.loctName = loctName;
        this.ordrStatus = ordrStatus;
        this.shipNo = shipNo;
    }

    public String getShipNo() {  return shipNo;   }
    public void setShipNo(String shipNo) { this.shipNo = shipNo; }

    public String getLoctName() {   return loctName;  }
    public void setLoctName(String loctName) {   this.loctName = loctName;   }

    public String getOrdrStatus() {  return ordrStatus;  }
    public void setOrdrStatus(String ordrStatus) {  this.ordrStatus = ordrStatus;  }
}
