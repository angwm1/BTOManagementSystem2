package edu.ntu.bto.model;

/**
 * Represents an enquiry submitted by a user.
 */
public class Enquiry {
    private String id;
    private Applicant applicant;
    private Project project;
    private String question;
    private String response;
    
    public Enquiry(String id, Applicant applicant, Project project, String question) {
        this.id = id;
        this.applicant = applicant;
        this.project = project;
        this.question = question;
        this.response = null;
    }
    
    public String getId() { 
        return id; 
    }

    public String getQuestion() { 
        return question; 
    }

    public void setQuestion(String newQ) { 
        question = newQ; 
    }

    public Applicant getApplicant() { 
        return applicant; 
    }

    public Project getProject() { 
        return project; 
    }

    public String getResponse() { 
        return response; 
    }

    public void setResponse(String response) { 
        this.response = response; 
    }
    
    @Override
    public String toString() {
        return "Enquiry from " + applicant.getNric() + ": " + question;
    }
}
