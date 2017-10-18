package ianto.solutions.realmexample.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Message extends RealmObject {
    @Expose
    @PrimaryKey
    @SerializedName("Uid")
    int uid;

    @Expose
    @SerializedName("CreatedAt")
    int createdAt;

    @Expose
    @SerializedName("Unread")
    boolean unread;

    @Expose
    @SerializedName("SentBy")
    int sentBy;

    @Expose
    @SerializedName("SentTo")
    int sentTo;

    @Expose
    @SerializedName("Body")
    String body;
}
