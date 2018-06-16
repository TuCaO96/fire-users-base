package br.cotemig.ak.fireusersbase.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import br.cotemig.ak.fireusersbase.R;
import br.cotemig.ak.fireusersbase.app.FireUsersBaseApplication;
import br.cotemig.ak.fireusersbase.model.User;
import br.cotemig.ak.fireusersbase.model.UserDetails;
import br.cotemig.ak.fireusersbase.model.UserList;
import br.cotemig.ak.fireusersbase.ui.adapter.UserListAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.list)
    ListView users_list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getUsers();
    }

    @OnItemClick(R.id.list)
    public void itemClick(int p){
        User itemSelected = (User) users_list.getItemAtPosition(p);
        String content = "Usuário: " + itemSelected.getUserDetails().getUsername() +
                "\nUID: " + itemSelected.getUid();
        new MaterialDialog.Builder(MainActivity.this)
                .title(R.string.username)
                .content(content)
                .positiveText(R.string.ok)
                .show();
    }

    private final static Gson GSON = new GsonBuilder().create();

    public static <T> T fromJSON(String json, Class<T> clazz) {
        try {
            return GSON.fromJson(json, clazz);
        } catch (JsonSyntaxException e) {
            System.out.println("Could not deserialize object: "+ e.getMessage());
        }
        return null;
    }

    protected void getUsers(){
        Call<String> userListCall = FireUsersBaseApplication.getInstance().getUserService().getUsers();
        final Context context = this;

        userListCall.enqueue(new Callback<String>() {
             @Override
             public void onResponse(Call<String> call, Response<String> response) {
                 if(response.code() == 200){
                     UserList userList = new UserList();
                     List<User> users = new ArrayList<>();

                     JsonObject result = fromJSON(response.body(), JsonObject.class);

                     Set<String> jsonKeys = result.keySet();

                     Iterator<String>iterator = jsonKeys.iterator();

                     while (iterator.hasNext()){
                         User user = new User();

                         String uid = iterator.next();

                         JsonObject obj = result.get(uid).getAsJsonObject();

                         user.setUid(uid);

                         UserDetails userDetails = new UserDetails();
                         userDetails.setEmail(obj.get("email").toString());
                         userDetails.setUsername(obj.get("username").toString());
                         user.setUserDetails(userDetails);
                         users.add(user);
                     }
                     userList.setUsers(users);

                     UserListAdapter adapter = new UserListAdapter(context, userList);
                     users_list.setAdapter(adapter);
                 }
             }

             @Override
             public void onFailure(Call<String> call, Throwable t) {
                 new MaterialDialog.Builder(MainActivity.this)
                         .title(R.string.error)
                         .content(t.getMessage())
                         .positiveText(R.string.ok)
                         .show();
             }
        });
    }

    @OnClick(R.id.logout_button)
    public void logoutClick(){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signOut();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
