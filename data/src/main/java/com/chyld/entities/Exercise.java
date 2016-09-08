package com.chyld.entities;

import com.chyld.enums.ExerciseEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "exercises")
@Data
public class Exercise {
    private int id;
    private int version;
    private ExerciseEnum type;
    private int quantity;
    private int calories;
    private int duration;
    private User user;
    private Date created;
    private Date modified;

    @Id
    @GeneratedValue
    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    @Version
    public int getVersion() {return version;}
    public void setVersion(int version) {this.version = version;}

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('RUN', 'SWIM', 'BIKE', 'LIFT')")
    public ExerciseEnum getType() {return type;}
    public void setType(ExerciseEnum type) {this.type = type;}

    @ManyToOne
    @JoinColumn(name="user_id")
    @JsonIgnore
    public User getUser() {return user;}
    public void setUser(User user) {this.user = user;}

    @CreationTimestamp
    public Date getCreated() {return created;}
    public void setCreated(Date created) {this.created = created;}

    @UpdateTimestamp
    public Date getModified() {return modified;}
    public void setModified(Date modified) {this.modified = modified;}
}
