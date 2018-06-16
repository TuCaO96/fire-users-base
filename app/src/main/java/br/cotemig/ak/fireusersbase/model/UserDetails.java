package br.cotemig.ak.fireusersbase.model;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by SONY on 09/06/2018.
 */

// [START blog_user_class]
@IgnoreExtraProperties
public class UserDetails {
    public String username;
    public String email;

    public UserDetails() {
        // Default constructor required for calls to DataSnapshot.getValue(UserDetails.class)
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserDetails(String username, String email) {
        this.username = username;
        this.email = email;
    }
}
// [END blog_user_class]