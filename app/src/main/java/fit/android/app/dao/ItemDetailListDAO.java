package fit.android.app.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import fit.android.app.model.ItemDetailList;
import fit.android.app.model.ItemTaskList;

@Dao
public interface ItemDetailListDAO {
    //get all item detail task list by id task
    @Query("SELECT * FROM item_detail_list WHERE task_id LIKE :task_id")
    List<ItemDetailList> getAll(int task_id);

    //find user by name task
    @Query("SELECT * FROM item_detail_list WHERE name_detail LIKE :name")
    ItemDetailList findByNameTask(String name);

    //insert item
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(ItemDetailList itemDetailList);

    //update item
    @Update
    void update(ItemDetailList itemDetailList);

    //delete all detail of one task by id
    @Query("DELETE FROM item_detail_list WHERE task_id LIKE :task_id")
    void deleteAllByIdTask(int task_id);

    //delete item
    @Delete
    void delete(ItemDetailList itemDetailList);
}
