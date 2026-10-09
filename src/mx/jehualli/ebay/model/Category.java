package mx.jehualli.ebay.model;

/** A node in the two-level category tree. Leaves hold the items. */
public class Category {

    private final int id;
    private final String name;
    private final int parentId;     // 0 for a top-level category

    public Category(int id, String name, int parentId) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
    }

    public int getId()       { return id; }
    public String getName()  { return name; }
    public int getParentId() { return parentId; }
    public boolean isTop()   { return parentId == 0; }
}
