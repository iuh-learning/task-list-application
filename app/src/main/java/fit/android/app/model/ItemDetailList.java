package fit.android.app.model;

public class ItemDetailList {
    private  int id;
    private     String nameDetail;
    private int taskID;

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
