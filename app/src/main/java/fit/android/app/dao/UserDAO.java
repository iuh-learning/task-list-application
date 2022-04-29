package fit.android.app.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import fit.android.app.model.User;

@Dao
public interface UserDAO {
    //get all users
    @Query("SELECT * FROM users")
    List<User> getAll();

    //find user by email
    @Query("SELECT * FROM users WHERE email LIKE :email")
    User findByEmail(String email);

    //insert user
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(User user);

    //update user
    @Update
    void update(User user);
}
