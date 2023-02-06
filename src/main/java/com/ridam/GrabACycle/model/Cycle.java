package com.ridam.GrabACycle.model;

import com.ridam.GrabACycle.enums.CycleStatus;
import com.ridam.GrabACycle.enums.UserRole;

import javax.persistence.*;

@Entity

public class Cycle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long cycleId;
    private String cycleName;
    private String cycleType;
    private String cycleModel;
    private int price;
    private boolean isDeleted;

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public String getCycleName() {
        return cycleName;
    }

    public long getCycleId() {
        return cycleId;
    }

    public void setCycleName(String cycleName) {
        this.cycleName = cycleName;
    }

    public String getCycleType() {
        return cycleType;
    }

    public void setCycleType(String cycleType) {
        this.cycleType = cycleType;
    }

    public String getCycleModel() {
        return cycleModel;
    }

    public void setCycleModel(String cycleModel) {
        this.cycleModel = cycleModel;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public CycleStatus getCycleStatus() {
        return cycleStatus;
    }

    public void setCycleStatus(CycleStatus cycleStatus) {
        this.cycleStatus = cycleStatus;
    }

    @Enumerated(EnumType.ORDINAL)
    private CycleStatus cycleStatus;

}
