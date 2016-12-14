package edu.wm.as.cs.codeclones.entities;

import java.util.Date;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class Revision {
	private int revisionID;
//	private int projectID;
	private String projectName;
	private String revisionName;
	private String authorName;
	private java.util.Date submitTime;
	
	public Revision() {
	}

	public Revision(int revisionID, String projectName, String revisionName, String authorName, Date submitTime) {
		super();
		this.revisionID = revisionID;
		this.projectName = projectName;
		this.revisionName = revisionName;
		this.authorName = authorName;
		this.submitTime = submitTime;
	}

	public int getRevisionID() {
		return revisionID;
	}

	public void setRevisionID(int revisionID) {
		this.revisionID = revisionID;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getRevisionName() {
		return revisionName;
	}

	public void setRevisionName(String revisionName) {
		this.revisionName = revisionName;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public java.util.Date getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(java.util.Date submitTime) {
		this.submitTime = submitTime;
	}

	@Override
	public String toString() {
		return "Revision [revisionID=" + revisionID + ", projectName=" + projectName + ", revisionName=" + revisionName
				+ ", authorName=" + authorName + ", submitTime=" + submitTime + "]";
	}
	
}
