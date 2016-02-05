package com.elasticbeanstalk.laciecool.first;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lacie on 1/19/16.
 */
public class Options implements Parcelable{
    private int id;
    private int group;
    private int qnsId;
    private String option;
    private String units;
    private int numOfInput;
    private int optionValue;

    public Options() {

    }

    public void setAttributes(int id, int group, int qnsId, String option, String units, int numOfInput, int optionValue) {
        this.id = id;
        this.group = group;
        this.qnsId = qnsId;
        this.option = option;
        this.units = units;
        this.numOfInput = numOfInput;
        this.optionValue = optionValue;
    }

    public String getOption() {
        return this.option;
    }

    public int getNumOfInput() {
        return this.numOfInput;
    }

    public int getValue() {
        return this.optionValue;
    }

    public String getUnits() {
        return !this.units.equals(null)?this.units:"";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags){
        out.writeInt(id);
        out.writeInt(group);
        out.writeInt(qnsId);
        out.writeString(option);
        out.writeString(units);
        out.writeInt(numOfInput);
        out.writeInt(optionValue);
    }

    public static final Parcelable.Creator<Options> CREATOR = new Parcelable.Creator<Options>() {
        public Options createFromParcel(Parcel in) {
            Options o = new Options();
            o.id = in.readInt();
            o.group = in.readInt();
            o.qnsId = in.readInt();
            o.option = in.readString();
            o.units = in.readString();
            o.numOfInput = in.readInt();
            o.optionValue = in.readInt();
            return o;
        }

        public Options[] newArray(int size) {
            return new Options[size];
        }
    };

}
