package edu.ntu.bto.model;

public class Registration {
    public enum Status {
        PENDING, APPROVED, REJECTED
    }

    private HDBOfficer officer;
    private Project project;
    private Status status;

    public Registration(HDBOfficer officer, Project project) {
        this.officer = officer;
        this.project = project;
        this.status = Status.PENDING;
    }

    public Status getStatus() { 
        return status; 
    }

    public void setStatus(Status status) { 
        this.status = status; 
    }

    public HDBOfficer getOfficer() { 
        return officer; 
    }

    public Project getProject() { 
        return project; 
    }
}

