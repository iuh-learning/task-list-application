package fit.android.app.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import fit.android.app.model.ItemTaskList;

@Dao
public interface ItemTaskListDAO {
    //get all item task list
    @Query("SELECT * FROM item_task_list WHERE email LIKE :email")
    List<ItemTaskList> getAll(String email);

    //find user by name task
    @Query("SELECT * FROM item_task_list WHERE name_task LIKE :name")
    ItemTaskList findByNameTask(String name);

    //insert item
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(ItemTaskList itemTaskList);

    //update item
    @Update
    void update(ItemTaskList itemTaskList);

    //delete item
    @Delete
    void delete(ItemTaskList itemTaskList);
}
