package fit.android.app.database;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import fit.android.app.dao.ItemDetailListDAO;
import fit.android.app.dao.ItemTaskListDAO;
import fit.android.app.dao.UserDAO;
import fit.android.app.model.ItemDetailList;
import fit.android.app.model.ItemTaskList;
import fit.android.app.model.User;

@Database(entities = {User.class, ItemTaskList.class, ItemDetailList.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDAO userDAO();
    public abstract ItemTaskListDAO itemTaskListDAO();
    public abstract ItemDetailListDAO itemDetailListDAO();
}
