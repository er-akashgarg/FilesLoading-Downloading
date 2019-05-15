package com.akashgarg.sample.utils;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Akash Garg on 15-05-2019.
 */

public class Download implements Parcelable {

    public static final Creator<Download> CREATOR = new Creator<Download>() {
        public Download createFromParcel(Parcel in) {
            return new Download(in);
        }

        public Download[] newArray(int size) {
            return new Download[size];
        }
    };
    private int progress;
    private int currentFileSize;
    private int totalFileSize;

    public Download() {

    }

    private Download(Parcel in) {

        progress = in.readInt();
        currentFileSize = in.readInt();
        totalFileSize = in.readInt();
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getCurrentFileSize() {
        return currentFileSize;
    }

    public void setCurrentFileSize(int currentFileSize) {
        this.currentFileSize = currentFileSize;
    }

    public int getTotalFileSize() {
        return totalFileSize;
    }

    public void setTotalFileSize(int totalFileSize) {
        this.totalFileSize = totalFileSize;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(progress);
        dest.writeInt(currentFileSize);
        dest.writeInt(totalFileSize);
    }
}
