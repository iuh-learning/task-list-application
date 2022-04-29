package fit.android.app.model;

public class ItemTaskList {

    private int id;
    private String nameTask;

    // Constructor
    public ItemTaskList(int id, String nameTask) {
        this.id = id;
        this.nameTask = nameTask;
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
