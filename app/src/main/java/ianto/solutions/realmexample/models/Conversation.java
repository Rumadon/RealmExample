package ianto.solutions.realmexample.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Conversation extends RealmObject {
    @Expose
    @PrimaryKey
    @SerializedName("Uid")
    public int uid;

    @Expose
    @SerializedName("CreatedAt")
    public int createdAt;

    @Expose
    @SerializedName("UpdatedAt")
    public int updatedAt;

    @Expose
    @SerializedName("Messages")
    public RealmList<Message> messages = new RealmList<>();

    @Expose
    @SerializedName("UnreadMessageCount")
    public int unreadMessageCount;

    @Expose
    @SerializedName("User1")
    public int user1;

    @Expose
    @SerializedName("User2")
    public int user2;

    public Conversation() {
        super();
    }
}
