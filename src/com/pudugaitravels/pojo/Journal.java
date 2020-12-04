package com.pudugaitravels.pojo;

public class Journal {
	
	private String journalId;
	private String journalDt;
	private String journalDesc;
	private String journalAcc;
	private String journalDrAmt;
	private String journalCrAmt;
	private String journalDrCrType;
	
	
	
	@Override
	public String toString() {
		return "Journal [journalId=" + journalId + ", journalDt=" + journalDt + ", journalDesc=" + journalDesc
				+ ", journalAcc=" + journalAcc + ", journalDrAmt=" + journalDrAmt + ", journalCrAmt=" + journalCrAmt
				+ ", journalDrCrType=" + journalDrCrType + "]";
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((journalAcc == null) ? 0 : journalAcc.hashCode());
		result = prime * result + ((journalCrAmt == null) ? 0 : journalCrAmt.hashCode());
		result = prime * result + ((journalDesc == null) ? 0 : journalDesc.hashCode());
		result = prime * result + ((journalDrAmt == null) ? 0 : journalDrAmt.hashCode());
		result = prime * result + ((journalDrCrType == null) ? 0 : journalDrCrType.hashCode());
		result = prime * result + ((journalDt == null) ? 0 : journalDt.hashCode());
		result = prime * result + ((journalId == null) ? 0 : journalId.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Journal other = (Journal) obj;
		if (journalAcc == null) {
			if (other.journalAcc != null)
				return false;
		} else if (!journalAcc.equals(other.journalAcc))
			return false;
		if (journalCrAmt == null) {
			if (other.journalCrAmt != null)
				return false;
		} else if (!journalCrAmt.equals(other.journalCrAmt))
			return false;
		if (journalDesc == null) {
			if (other.journalDesc != null)
				return false;
		} else if (!journalDesc.equals(other.journalDesc))
			return false;
		if (journalDrAmt == null) {
			if (other.journalDrAmt != null)
				return false;
		} else if (!journalDrAmt.equals(other.journalDrAmt))
			return false;
		if (journalDrCrType == null) {
			if (other.journalDrCrType != null)
				return false;
		} else if (!journalDrCrType.equals(other.journalDrCrType))
			return false;
		if (journalDt == null) {
			if (other.journalDt != null)
				return false;
		} else if (!journalDt.equals(other.journalDt))
			return false;
		if (journalId == null) {
			if (other.journalId != null)
				return false;
		} else if (!journalId.equals(other.journalId))
			return false;
		return true;
	}



	public Journal() {
		super();
		// TODO Auto-generated constructor stub
	}



	public Journal(String journalId, String journalDt, String journalDesc, String journalAcc, String journalDrAmt,
			String journalCrAmt, String journalDrCrType) {
		super();
		this.journalId = journalId;
		this.journalDt = journalDt;
		this.journalDesc = journalDesc;
		this.journalAcc = journalAcc;
		this.journalDrAmt = journalDrAmt;
		this.journalCrAmt = journalCrAmt;
		this.journalDrCrType = journalDrCrType;
	}



	public String getJournalId() {
		return journalId;
	}



	public void setJournalId(String journalId) {
		this.journalId = journalId;
	}



	public String getJournalDt() {
		return journalDt;
	}



	public void setJournalDt(String journalDt) {
		this.journalDt = journalDt;
	}



	public String getJournalDesc() {
		return journalDesc;
	}



	public void setJournalDesc(String journalDesc) {
		this.journalDesc = journalDesc;
	}



	public String getJournalAcc() {
		return journalAcc;
	}



	public void setJournalAcc(String journalAcc) {
		this.journalAcc = journalAcc;
	}



	public String getJournalDrAmt() {
		return journalDrAmt;
	}



	public void setJournalDrAmt(String journalDrAmt) {
		this.journalDrAmt = journalDrAmt;
	}



	public String getJournalCrAmt() {
		return journalCrAmt;
	}



	public void setJournalCrAmt(String journalCrAmt) {
		this.journalCrAmt = journalCrAmt;
	}



	public String getJournalDrCrType() {
		return journalDrCrType;
	}



	public void setJournalDrCrType(String journalDrCrType) {
		this.journalDrCrType = journalDrCrType;
	}
	
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
