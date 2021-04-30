package com.tolstenkov.lab3;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Date;
import java.util.UUID;

public class Inventory implements Parcelable {
    private UUID mId; // ідентифікатор інвентарю
    private String mTitle; // назва
    private Date mDate; // дата внесення
    private boolean mSolved; // статус
    public Inventory(){
        mId = UUID.randomUUID();
        mTitle = "";
        mDate = new Date();
        mSolved = false;
    }

    protected Inventory(Parcel in) {
        mTitle = in.readString();
        mDate = new Date(in.readString());
        mSolved = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
        dest.writeString(mDate.toString());
        dest.writeByte((byte) (mSolved ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Inventory> CREATOR = new Creator<Inventory>() {
        @Override
        public Inventory createFromParcel(Parcel in) {
            return new Inventory(in);
        }

        @Override
        public Inventory[] newArray(int size) {
            return new Inventory[size];
        }
    };

    public UUID getmId() {
        return mId;
    }
    public String getmTitle() {
        return mTitle;
    }
    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }
    public Date getmDate() {
        return mDate;
    }
    public void setmDate(Date mDate) {
        this.mDate = mDate;
    }
    public boolean ismSolved() {
        return mSolved;
    }
    public void setmSolved(boolean mSolved) {
        this.mSolved = mSolved;
    }
}
