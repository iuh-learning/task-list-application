package fit.android.app.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "email")
    private String email;

    @ColumnInfo(name = "full_name")
    private String fullName;

    @ColumnInfo(name = "password")
    private String password;

    public User(@NonNull String email, String fullName, String password) {
        this.email = email;
        this.fullName = fullName;
        this.password = password;
    }

    public User() {
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User[" +
                "email='" + email + '\'' +
                ", fullName='" + fullName + '\'' +
                ", password='" + password + '\'' +
                ']';
    }
}
