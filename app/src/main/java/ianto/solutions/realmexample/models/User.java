package ianto.solutions.realmexample.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class User extends RealmObject {
    @Expose
    @PrimaryKey
    @SerializedName("Uid")
    public int uid;

    @Expose
    @SerializedName("Name")
    public String name;
}
