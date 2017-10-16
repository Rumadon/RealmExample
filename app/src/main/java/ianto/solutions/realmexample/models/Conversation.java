package ianto.solutions.realmexample.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import io.realm.RealmObject;

public class Conversation extends RealmObject {
    @Expose
    @SerializedName("Uid")
    int uid;

    @Expose
    @SerializedName("CreatedAt")
    int createdAt;

    @Expose
    @SerializedName("UpdatedAt")
    int updatedAt;

    @Expose
    @SerializedName("Messages")
    ArrayList<Integer> messages;

    @Expose
    @SerializedName("UnreadMessageCount")
    int unreadMessageCount;

    @Expose
    @SerializedName("OtherUser")
    int otherUser;

    public Conversation() {
        super();
    }
}
