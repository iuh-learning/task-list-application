package fit.android.app.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "item_detail_list")
public class ItemDetailList {
    @PrimaryKey(autoGenerate = true)
    private  int id;

    @ColumnInfo(name = "name_detail")
    private  String nameDetail;

    @ColumnInfo(name = "task_id")
    private int taskID;

    public ItemDetailList() {
    }

    public ItemDetailList(int id, String nameDetail, int taskID) {
        this.id = id;
        this.nameDetail = nameDetail;
        this.taskID = taskID;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameDetail() {
        return nameDetail;
    }

    public void setNameDetail(String nameDetail) {
        this.nameDetail = nameDetail;
    }


}
