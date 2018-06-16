package br.cotemig.ak.fireusersbase.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONObject;

import br.cotemig.ak.fireusersbase.R;
import br.cotemig.ak.fireusersbase.model.User;
import br.cotemig.ak.fireusersbase.model.UserList;

public class UserListAdapter extends BaseAdapter{
    Context context;
    UserList userList;

    public UserListAdapter(Context context, UserList userList){
        this.context = context;
        this.userList = userList;
    }

    @Override
    public int getCount() {
        return userList.getUsers().size();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        User user = (User) getItem(i);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View v = inflater.inflate(R.layout.item_user, null);

        TextView userTextView = v.findViewById(R.id.user);
        userTextView.setText(user.getUserDetails().getUsername());

        return v;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return userList.getUsers().get(i);
    }
}
