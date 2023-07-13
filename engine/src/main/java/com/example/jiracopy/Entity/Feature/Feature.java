package com.example.jiracopy.Entity.Feature;


import com.example.jiracopy.Entity.Epic.Epic;
import com.example.jiracopy.Entity.Story.Story;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Feature")
public class Feature {

    @Id
    @Column(name = "Name")
    private String name;

    @Column(name = "Description")
    private String description;

    @Column(name = "Status")
    private String status;


    @Column(name = "Owner")
    private String owner;

    @Column(name = "assignee")
    private String assignee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "epic_id")
    private Epic epic;

    @OneToMany(targetEntity = Story.class, cascade = CascadeType.ALL)
    private List<Story> stories = new ArrayList<>();

    public List<Story> getStories(){
        return (List<Story>) stories;
    }
    public Feature() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String isStatus() {
        return status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = String.valueOf(status);
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setEpic(Epic epic) {
        this.epic = epic;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    @Override
    public String toString() {
        return "Feature{" + "name='" + name + '\'' + ", description='" + description + '\'' + ", status=" + status + ", owner='" + owner + '\'' + ", epic=" + epic + '}';
    }


}
