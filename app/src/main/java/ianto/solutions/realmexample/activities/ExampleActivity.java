package ianto.solutions.realmexample.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

import ianto.solutions.realmexample.R;
import ianto.solutions.realmexample.adapters.ConversationsAdapters;
import ianto.solutions.realmexample.models.Conversation;
import ianto.solutions.realmexample.models.Message;
import ianto.solutions.realmexample.util.WebHandler;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class ExampleActivity extends AppCompatActivity {
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        realm = Realm.getDefaultInstance();

        WebHandler.updateUsers();

        setContentView(R.layout.activity_example);
        RecyclerView recyclerView = findViewById(R.id.conversations_recycler_view);

        RealmResults<Conversation> conversations = realm.where(Conversation.class).findAll();
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new ConversationsAdapters(this, conversations, true));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WebHandler.updateConversations();
            }
        });
        final TextView text = findViewById(R.id.activity_message);
        RealmResults<Message> unread = realm.where(Message.class).equalTo("unread", true).findAll();
        text.setText(String.format(Locale.getDefault(), "Unread: %d", unread.size()));
        unread.addChangeListener(new RealmChangeListener<RealmResults<Message>>() {
            @Override
            public void onChange(RealmResults<Message> messages) {
                text.setText(String.format(Locale.getDefault(), "Unread: %d", messages.size()));
            }
        });
    }

    @Override
    protected void onDestroy() {
        realm.close();
        super.onDestroy();

    }
}
