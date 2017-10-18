package ianto.solutions.realmexample.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.AsyncTask;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import ianto.solutions.realmexample.models.Conversation;
import ianto.solutions.realmexample.models.Message;
import ianto.solutions.realmexample.models.User;
import io.realm.Realm;
import retrofit.Callback;
import retrofit.ErrorHandler;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.android.MainThreadExecutor;
import retrofit.client.OkClient;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;
import retrofit.http.GET;
import timber.log.Timber;

public class WebHandler {
    public static final int USER_ID = 0;
    private static final String SERVER_ENDPOINT = "http://76.171.116.76:8080";
    private static RestAdapter restAdapter;
    private static WebHandlerInterface webHandlerInterface;
    private static SimpleDateFormat rubyDateTimeFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.PRC);
    private static String versionName = "";
    private static Gson gson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .excludeFieldsWithoutExposeAnnotation()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
            .setPrettyPrinting()
            .create();

    public static void setupRetrofit(Context context) {
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            versionName = pInfo.versionName;
        } catch (Exception e) {
            Timber.e(e, "Package Name Not Found");
        }
        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(SERVER_ENDPOINT)
                .setClient(new OkClient())
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setLog(new RestAdapter.Log() {
                    @Override
                    public void log(String message) {
                        Timber.d(message);
                    }
                })
                .setConverter(new GsonConverter(gson))
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestInterceptor.RequestFacade request) {
//                        if (AuthManager.isSignedIn()) {
//                            request.addHeader("Authorization", AuthManager.getHeaderToken());
//                        }
                        request.addHeader("Content-Type", "application/json");
                        request.addHeader("Accept", "application/json");
                        request.addHeader("User-Agent", String.format(Locale.US, "CoachUp/%s (Android %s)", versionName, System.getProperty("http.agent")));
                    }
                })
                .setErrorHandler(new ErrorHandler() {
                    @Override
                    public Throwable handleError(RetrofitError cause) {
                        Timber.e(cause, "Retrofit Error");
                        return cause;
                    }
                });
        if (false) {//useAsyncTaskThreadpool) {
            builder.setExecutors(AsyncTask.THREAD_POOL_EXECUTOR,
                    new MainThreadExecutor());
        }
        restAdapter = builder.build();
        webHandlerInterface = restAdapter.create(WebHandlerInterface.class);
    }

    public static Gson getGson() {
        return gson;
    }

    public static void updateUsers() {
        Callback<List<User>> callback = new Callback<List<User>>() {
            @Override
            public void success(List<User> users, Response response) {
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                realm.copyToRealmOrUpdate(users);
                realm.commitTransaction();
            }

            @Override
            public void failure(RetrofitError error) {
                Timber.e(error);
            }
        };

        webHandlerInterface.getAllUsers(callback);
    }

    public static void updateConversations() {
        Callback<List<Conversation>> callback = new Callback<List<Conversation>>() {
            @Override
            public void success(List<Conversation> conversations, Response response) {
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                realm.copyToRealmOrUpdate(conversations);
                realm.commitTransaction();
            }

            @Override
            public void failure(RetrofitError error) {
                Timber.e(error);
            }
        };

        webHandlerInterface.getAllConversations(callback);
    }

    public static void updateMessages() {
        Callback<List<Message>> callback = new Callback<List<Message>>() {
            @Override
            public void success(List<Message> messages, Response response) {
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                realm.copyToRealmOrUpdate(messages);
                realm.commitTransaction();
            }

            @Override
            public void failure(RetrofitError error) {
                Timber.e(error);
            }
        };

        webHandlerInterface.getAllMessages(callback);
    }

//    public static void getConversations(final Callback<ConversationListWrapper> callback) {
//        Realm realm = Realm.getDefaultInstance();
//        final Date updatedAt = realm.where(Conversation.class).maximumDate("updatedAt");
//        if (updatedAt == null || updatedAt.before(new Date(1))) {
//            webHandlerInterface.getAllConversations(callback);
//        } else {
//            String dateString = rubyDateTimeFormat.format(updatedAt);
//            webHandlerInterface.getNewConversations(dateString, callback);
//        }
//    }
//
//    public static void getMessages(Callback<MessageListWrapper> callback) {
//        Realm realm = Realm.getDefaultInstance();
//        final Date updatedAt = realm.where(Message.class).maximumDate("updatedAt");
//        if (updatedAt == null || updatedAt.before(new Date(1))) {
//            webHandlerInterface.getAllMessages(callback);
//        } else {
//            Timber.w("LastMessageDate", updatedAt);
//            String dateString = rubyDateTimeFormat.format(updatedAt);
//            webHandlerInterface.getNewMessages(dateString, callback);
//        }
//        realm.close();
//    }

    interface WebHandlerInterface {
        @GET("/user")
        void getAllUsers(Callback<List<User>> callback);

        @GET("/conversation")
        void getAllConversations(Callback<List<Conversation>> callback);

        @GET("/message")
        void getAllMessages(Callback<List<Message>> callback);
    }
}
