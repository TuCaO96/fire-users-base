package br.cotemig.ak.fireusersbase.app;

import android.app.Application;

import com.google.firebase.FirebaseApp;

import br.cotemig.ak.fireusersbase.service.RestService;
import br.cotemig.ak.fireusersbase.service.UserService;

/**
 * Created by SONY on 09/06/2018.
 */

public class FireUsersBaseApplication extends Application {
    private static String URL = "https://app-firebase-f41d4.firebaseio.com/";

    private UserService userService;

    private static FireUsersBaseApplication instance;

    @Override
    public void onCreate() {
        FirebaseApp.initializeApp(this);
        super.onCreate();
        instance = this;
        createServices();
    }

    public void createServices(){
        userService = (new RestService(URL).getService(UserService.class));
    }

    public UserService getUserService() {
        return userService;
    }

    public static FireUsersBaseApplication getInstance() {
        return instance;
    }
}
