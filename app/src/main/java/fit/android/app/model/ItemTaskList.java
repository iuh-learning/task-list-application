package fit.android.app.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "item_task_list")
public class ItemTaskList {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name_task")
    private String nameTask;

    // Constructor
    public ItemTaskList(int id, String nameTask) {
        this.id = id;
        this.nameTask = nameTask;
    }

    public ItemTaskList() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameTask() {
        return nameTask;
    }

    public void setNameNameTask(String nameTask) {
        this.nameTask = nameTask;
    }
}
