package com.awak25.authentificationanddatabase;

import android.os.Parcel;
import android.os.Parcelable;

public class BiodataModel implements Parcelable {

    String id, nama, alamat, gender, pendidikan;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPendidikan() {
        return pendidikan;
    }

    public void setPendidikan(String pendidikan) {
        this.pendidikan = pendidikan;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.nama);
        dest.writeString(this.alamat);
        dest.writeString(this.gender);
        dest.writeString(this.pendidikan);
    }

    public BiodataModel() {
    }

    protected BiodataModel(Parcel in) {
        this.id = in.readString();
        this.nama = in.readString();
        this.alamat = in.readString();
        this.gender = in.readString();
        this.pendidikan = in.readString();
    }

    public static final Parcelable.Creator<BiodataModel> CREATOR = new Parcelable.Creator<BiodataModel>() {
        @Override
        public BiodataModel createFromParcel(Parcel source) {
            return new BiodataModel(source);
        }

        @Override
        public BiodataModel[] newArray(int size) {
            return new BiodataModel[size];
        }
    };
}
