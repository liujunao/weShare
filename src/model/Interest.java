package model;

/**
 *
 * @author lenovo
 * @date 2018/6/28
 */
public class Interest {

    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Interest(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Interest() {
    }

    @Override
    public String toString() {
        return "Interest{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
