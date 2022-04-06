package com.dongwon.scanner.model.ship;

import com.dongwon.scanner.sql.RetrofitResult;
import com.google.gson.annotations.SerializedName;

public class WhInput extends RetrofitResult {
    @SerializedName("LOTID")
    private String lotId;
    @SerializedName("PARTID")
    private String partId;
    @SerializedName("PARTNAME")
    private String partName;
    @SerializedName("QTY")
    private int qty;
    @SerializedName("CELLID")
    private String cellId;
    @SerializedName("AREA_ID")
    private String areaId;
    @SerializedName("BOX_FLAG")
    private Boolean boxFlag;

    public String getLotId() {
        return lotId;
    }

    public void setLotId(String lotId) {
        this.lotId = lotId;
    }

    public String getPartId() {
        return partId;
    }

    public void setPartId(String partId) {
        this.partId = partId;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getCellId() {
        return cellId;
    }

    public void setCellId(String cellId) {
        this.cellId = cellId;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public Boolean getBoxFlag() {
        return boxFlag;
    }

    public void setBoxFlag(Boolean boxFlag) {
        this.boxFlag = boxFlag;
    }
}
