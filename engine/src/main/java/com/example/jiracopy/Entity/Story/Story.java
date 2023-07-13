package com.example.jiracopy.Entity.Story;


import com.example.jiracopy.Entity.Feature.Feature;
import jakarta.persistence.*;

@Entity
@Table(name = "UserStory")
public class Story {

    @Id
    @Column(name = "Title")
    private String title;

    @Column(name = "Functionality")
    private String functionality;

    @Column(name = "Priority")
    private String priority;

    @Column(name = "assignee")
    private String assignee;


    @ManyToOne(fetch = FetchType.LAZY)
    private Feature feature;

    public void setFeature(Feature feature) {
        this.feature = feature;
    }

    public Story() {
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFunctionality() {
        return functionality;
    }

    public void setFunctionality(String functionality) {
        this.functionality = functionality;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    @Override
    public String toString() {
        return "Story{" + "title='" + title + '\'' + ", functionality='" + functionality + '\'' + ", priority='" + priority + '\'' + '}';
    }
}
