package com.example.login;

public class ItemProgress {
    private int upImageResource;
    private String upName;
    int progressAll;
    int progressDone;

    ItemProgress(int _upImageResource, String _upName, int _progressAll, int _progressDone){
        upImageResource = _upImageResource;
        upName = _upName;
        progressAll = _progressAll;
        progressDone = _progressDone;
    }

    public void setUpImageResource(int upImageResource) {
        this.upImageResource = upImageResource;
    }

    public int getUpImageResource() {
        return upImageResource;
    }

    public void setUpName(String upName) {
        this.upName = upName;
    }

    public String getUpName() {
        return upName;
    }

    public void setProgressAll(int progressAll) {
        this.progressAll = progressAll;
    }

    public int getProgressAll() {
        return progressAll;
    }

    public void setProgressDone(int progressDone) {
        this.progressDone = progressDone;
    }

    public int getProgressDone() {
        return progressDone;
    }
}
