package com.internalaudit.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity

@Table(name = "exception")
public class Exceptions implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7217627195055650063L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "exception_id")
	private int exceptionId;

	@Column(name = "detail")
	private String detail;

	@JoinColumn(name = "jobcreation_id")
	@ManyToOne(fetch = FetchType.EAGER)
	private JobCreation jobCreationId;

	@JoinColumn(name = "responsiblePerson")
	@ManyToOne(fetch = FetchType.EAGER)
	private Employee responsiblePerson;

	@JoinColumn(name = "divisionHead")
	@ManyToOne(fetch = FetchType.EAGER)
	private Employee divisionHead;

	// @JoinColumn(name = "auditStep")
	// @ManyToOne(fetch = FetchType.EAGER)
	@Column(name = "auditStep")
	private int auditStep;

	@Column(name = "dueDate")
	private Date dueDate;

	@Column(name = "jobName")
	private String jobName;

	@Column(name = "implementationDate")
	private Date implementaionDate;

	@Column(name = "managementComments")
	private String managementComments;

	@Column(name = "auditHead")
	private int auditHead;

	@Column(name = "status")
	private String status;

	@Column(name = "finalStatus")
	private String finalStatus;

	@Column(name = "initialStatus")
	private String initialStatus;

	@Column(name = "implementaionComments")
	private String implementaionComments;

	@Column(name = "isImplemented")
	private int isImplemented;

	@Column(name = "isAgreed")
	private int isAgreed;

	@Column(name = "emailSent")
	private int emailSent;

	@Column(name = "year")
	private int year;

	@Column(name = "companyId")
	private int companyId;

	@Column(name = "comments")
	private String comments;

	@Column(name = "recommendations")
	private String recommendations;

	@Column(name = "implication")
	private String implication;

	@Column(name = "implicationRating")
	private String implicationRating;

	@Transient
	private String displayStatus;

	@Transient
	private ArrayList<Recommendation> recommendation;
	
	@Transient
	private AuditEngagement auditEngagement;

	public int getExceptionId() {
		return exceptionId;
	}

	public void setExceptionId(int exceptionId) {
		this.exceptionId = exceptionId;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Employee getResponsiblePerson() {
		return responsiblePerson;
	}

	public void setResponsiblePerson(Employee responsiblePerson) {
		this.responsiblePerson = responsiblePerson;
	}

	public Employee getDivisionHead() {
		return divisionHead;
	}

	public void setDivisionHead(Employee divisionHead) {
		this.divisionHead = divisionHead;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public Date getImplementaionDate() {
		return implementaionDate;
	}

	public void setImplementaionDate(Date implementaionDate) {
		this.implementaionDate = implementaionDate;
	}

	public String getManagementComments() {
		return managementComments;
	}

	public void setManagementComments(String managementComments) {
		this.managementComments = managementComments;
	}

	public int getAuditHead() {
		return auditHead;
	}

	public void setAuditHead(int auditHead) {
		this.auditHead = auditHead;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getEmailSent() {
		return emailSent;
	}

	public void setEmailSent(int emailSent) {
		this.emailSent = emailSent;
	}

	public String getImplementaionComments() {
		return implementaionComments;
	}

	public void setImplementaionComments(String implementaionComments) {
		this.implementaionComments = implementaionComments;
	}

	public int getIsImplemented() {
		return isImplemented;
	}

	public void setIsImplemented(int isImplemented) {
		this.isImplemented = isImplemented;
	}

	public String getFinalStatus() {
		return finalStatus;
	}

	public void setFinalStatus(String finalStatus) {
		this.finalStatus = finalStatus;
	}

	public String getInitialStatus() {
		return initialStatus;
	}

	public void setInitialStatus(String initialStatus) {
		this.initialStatus = initialStatus;
	}

	public int getAuditStep() {
		return auditStep;
	}

	public void setAuditStep(int auditStep) {
		this.auditStep = auditStep;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getRecommendations() {
		return recommendations;
	}

	public void setRecommendations(String recommendations) {
		this.recommendations = recommendations;
	}

	public int getIsAgreed() {
		return isAgreed;
	}

	public void setIsAgreed(int isAgreed) {
		this.isAgreed = isAgreed;
	}

	public String getDisplayStatus() {
		return displayStatus;
	}

	public void setDisplayStatus(String displayStatus) {
		this.displayStatus = displayStatus;
	}

	public JobCreation getJobCreationId() {
		return jobCreationId;
	}

	public void setJobCreationId(JobCreation jobCreationId) {
		this.jobCreationId = jobCreationId;
	}

	public String getImplication() {
		return implication;
	}

	public void setImplication(String implication) {
		this.implication = implication;
	}

	public String getImplicationRating() {
		return implicationRating;
	}

	public void setImplicationRating(String implicationRating) {
		this.implicationRating = implicationRating;
	}

	public ArrayList<Recommendation> getRecommendation() {
		return recommendation;
	}

	public void setRecommendation(ArrayList<Recommendation> recommendation) {
		this.recommendation = recommendation;
	}

	public AuditEngagement getAuditEngagement() {
		return auditEngagement;
	}

	public void setAuditEngagement(AuditEngagement auditEngagement) {
		this.auditEngagement = auditEngagement;
	}

}
