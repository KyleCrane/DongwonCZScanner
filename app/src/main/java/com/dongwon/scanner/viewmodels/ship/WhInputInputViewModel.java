package com.dongwon.scanner.viewmodels.ship;

public class WhInputInputViewModel {
    public String lotId;
    public String partNumber;
    public String partName;
    public String qty;

    public WhInputInputViewModel(String lotId, String partNumber, String partName, String qty) {
        this.lotId = lotId;
        this.partNumber = partNumber;
        this.partName = partName;
        this.qty = qty;
    }
}
