package main.java.app.javache.enums;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public enum HeaderPriority {
    LOW("Low", 3), MEDIUM("Medium", 2), HIGH("High", 1);

    private String priorityString;
    private int priority;

    HeaderPriority(String priorityString, int priority) {
        this.priorityString = priorityString;
        this.priority = priority;
    }

    public String getPriorityString() {
        return priorityString;
    }

    public int getPriority() {
        return priority;
    }
}
