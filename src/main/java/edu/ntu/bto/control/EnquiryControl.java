package edu.ntu.bto.control;

import java.util.ArrayList;
import java.util.UUID;

import edu.ntu.bto.model.Applicant;
import edu.ntu.bto.model.Enquiry;
import edu.ntu.bto.model.Project;
import java.util.List;

public class EnquiryControl {
    private List<Enquiry> enquiries;

    public EnquiryControl(List<Enquiry> enquiries) {
        this.enquiries = enquiries;
    }

    public void submitEnquiry(Applicant applicant, Project project, String question) {
        String id = UUID.randomUUID().toString().substring(0, 10);
        Enquiry e = new Enquiry(id, applicant, project, question);
        enquiries.add(e);
        System.out.println("Enquiry submitted.");
    }

    public List<Enquiry> getEnquiriesByApplicant(Applicant applicant) {
        List<Enquiry> result = new ArrayList<>();
        for (Enquiry e : enquiries) {
            if (e.getApplicant().equals(applicant)) {
                result.add(e);
            }
        }
        return result;
    }

    public List<Enquiry> getEnquiriesByProject(Project project) {
        List<Enquiry> result = new ArrayList<>();
        for (Enquiry e : enquiries) {
            if (e.getProject().equals(project)) {
                result.add(e);
            }
        }
        return result;
    }

    public void replyEnquiry(String enquiryId, String response) {
        for (Enquiry e : enquiries) {
            if (e.getId().equals(enquiryId)) {
                e.setResponse(response);
                System.out.println("Response recorded.");
                return;
            }
        }
        System.out.println("Enquiry not found.");
    }

    public void deleteEnquiry(String enquiryId, Applicant applicant) {
        for (Enquiry e : enquiries) {
            if (e.getId().equals(enquiryId) && e.getApplicant().equals(applicant)) {
                enquiries.remove(e);
                System.out.println("Enquiry successfully deleted.");
                return;
            }
        }
        System.out.println("Enquiry not found. Deletion not successful.");
    }

    public void editEnquiry(String enquiryId, String newQuestion, Applicant applicant) {
        for (Enquiry e : enquiries) {
            if (e.getId().equals(enquiryId) && e.getApplicant().equals(applicant)) {
                e.setQuestion(newQuestion);
                System.out.println("Enquiry updated.");
                return;
            }
        }
        System.out.println("Unable to edit enquiry.");
    }

    public List<Enquiry> getAllEnquiries() {
        return enquiries;
    }
}
