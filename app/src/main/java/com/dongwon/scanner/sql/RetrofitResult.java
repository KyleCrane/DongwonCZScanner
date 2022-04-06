package com.dongwon.scanner.sql;

import com.google.gson.annotations.SerializedName;

public class RetrofitResult {
    @SerializedName("errorMessage")
    private String errorMessage;
    @SerializedName("hasError")
    private Boolean hasError = false;
    @SerializedName("outputMessage")
    private String outputMessage;

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    public void setHasError(Boolean hasError) {
        this.hasError = hasError;
    }
    public void setOutputMessage(String outputMessage) {
        this.outputMessage = outputMessage;
    }
    public String getErrorMessage() {
        return errorMessage;
    }
    public String getOutputMessage() {
        return outputMessage;
    }
    public Boolean getHasError() {
        return hasError;
    }
}
