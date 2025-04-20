package edu.ntu.bto.model;

public class Application {
    public enum Status {
        PENDING, SUCCESSFUL, UNSUCCESSFUL, BOOKED
    }

    private Applicant applicant;
    private Project project;
    private Status status;
    private String flatType;

    public Application(Applicant applicant, Project project, String flatType) {
        this.applicant = applicant;
        this.project = project;
        this.flatType = flatType;
        this.status = Status.PENDING;
    }

    public Status getStatus() { 
        return status; 
    }

    public void setStatus(Status status) { 
        this.status = status; 
    }

    public String getFlatType() { 
        return flatType; 
    }

    public Applicant getApplicant() { 
        return applicant; 
    }

    public Project getProject() { 
        return project; 
    }
}
