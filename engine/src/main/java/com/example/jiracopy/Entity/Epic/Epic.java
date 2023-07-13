package com.example.jiracopy.Entity.Epic;

import com.example.jiracopy.Entity.Feature.Feature;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "Epics")
public class Epic {

    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID epicId;

    @Column(name = "Name")
    private String name;

    @Column(name = "assignee")
    private String assignee;

    @OneToMany(mappedBy = "epic", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Feature> features = new ArrayList<>();

    public Epic() {
    }

    public UUID getEpicId() {
        return epicId;
    }

    public List<Feature> getFeatures() {
        return features;
    }


    public void setEpicId(UUID epicId) {
        this.epicId = epicId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public void setFeatures(List<Feature> features) {
        this.features = features;
    }

    @Override
    public String toString() {
        return "Epic{" + "epicId=" + epicId + '}';
    }
}

