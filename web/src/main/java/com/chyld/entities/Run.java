package com.chyld.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table (name = "runs")
@Data
public class Run {

    @Id
    @GeneratedValue
    private int id;

    @Version
    private int version;

    private Date startTime;
    private Date stopTime;

    @ManyToOne
    @JoinColumn(name="device_id")
    @JsonIgnore
    private Device device;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "run")
    List<Position> positions;

    @CreationTimestamp
    private Date created;

    @UpdateTimestamp
    private Date modified;


    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getStopTime() {
        return stopTime;
    }

    public void setStopTime(Date stopTime) {
        this.stopTime = stopTime;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public List<Position> getPositions() {
        return positions;
    }

    public void setPositions(List<Position> positions) {
        this.positions = positions;
    }
}
