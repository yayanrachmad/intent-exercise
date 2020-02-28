package model;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

    private String fullname;
    private String email;
    private String homepage;
    private String aboutyou;

    public User(String fullname, String email, String homepage, String aboutyou) {
        this.fullname = fullname;
        this.email = email;
        this.homepage = homepage;
        this.aboutyou = aboutyou;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public void setAboutyou(String aboutyou) {
        this.aboutyou = aboutyou;
    }

    public String getFullname() {
        return fullname;
    }

    public String getEmail() {
        return email;
    }

    public String getHomepage() {
        return homepage;
    }

    public String getAboutyou() {
        return aboutyou;
    }

    protected User(Parcel in) {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
