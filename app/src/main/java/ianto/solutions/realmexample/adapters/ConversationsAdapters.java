package ianto.solutions.realmexample.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Locale;

import ianto.solutions.realmexample.R;
import ianto.solutions.realmexample.models.Conversation;
import ianto.solutions.realmexample.models.User;
import ianto.solutions.realmexample.util.WebHandler;
import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmRecyclerViewAdapter;

public class ConversationsAdapters extends RealmRecyclerViewAdapter<Conversation, ConversationsAdapters.ConversationViewHolder> {
    public ConversationsAdapters(Context context, OrderedRealmCollection<Conversation> data, boolean autoUpdate) {
        super(context, data, autoUpdate);
    }

    @Override
    public ConversationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item_conversation, parent, false);
        return new ConversationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ConversationViewHolder holder, int position) {
        Conversation conversation = getData().get(position);
        Realm realm = Realm.getDefaultInstance();
        User user = realm.where(User.class)
                .beginGroup().equalTo("uid", conversation.user1).or().equalTo("uid", conversation.user2).endGroup()
                .notEqualTo("uid", WebHandler.USER_ID)
                .findFirst();
        if (user != null) {
            holder.name.setText(user.name);
        }

        int unread = conversation.messages.where().equalTo("unread", true).findAll().size();
        holder.unreadCount.setText(String.format(Locale.getDefault(),"%d", unread));
    }

    public class ConversationViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView unreadCount;

        public ConversationViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.list_item_conversation_name);
            unreadCount = itemView.findViewById(R.id.list_item_conversation_unread_count);
        }
    }

}
