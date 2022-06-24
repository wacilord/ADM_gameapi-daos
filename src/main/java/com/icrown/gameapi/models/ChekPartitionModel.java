package com.icrown.gameapi.models;

public class ChekPartitionModel {
    private boolean checkAndAddSuccess;
    private boolean autoCreateSuccess;

    public boolean isCheckAndAddSuccess() {
        return checkAndAddSuccess;
    }

    public void setCheckAndAddSuccess(boolean checkAndAddSuccess) {
        this.checkAndAddSuccess = checkAndAddSuccess;
    }

    public boolean isAutoCreateSuccess() {
        return autoCreateSuccess;
    }

    public void setAutoCreateSuccess(boolean autoCreateSuccess) {
        this.autoCreateSuccess = autoCreateSuccess;
    }

}
