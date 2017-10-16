package ianto.solutions.realmexample.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class User extends RealmObject {
    @Expose
    @SerializedName("Uid")
    int uid;

    @Expose
    @SerializedName("Name")
    int name;
}
