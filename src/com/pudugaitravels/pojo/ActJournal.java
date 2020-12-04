package com.pudugaitravels.pojo;

public class ActJournal {
	
	
	private String journalId;
	private String journalDrId;
	private String journalDt;
	private String journalDesc;
	private String journalAcc;
	private String journalAmt;
	private String journalRate;
	
	
	private String journalDrAmt;
	private String journalCrAmt;
	private String journalDrCrType;
	
	public String getJournalId() {
		return journalId;
	}
	public void setJournalId(String journalId) {
		this.journalId = journalId;
	}
	public String getJournalDrId() {
		return journalDrId;
	}
	public void setJournalDrId(String journalDrId) {
		this.journalDrId = journalDrId;
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
	public String getJournalAmt() {
		return journalAmt;
	}
	public void setJournalAmt(String journalAmt) {
		this.journalAmt = journalAmt;
	}
	public String getJournalRate() {
		return journalRate;
	}
	public void setJournalRate(String journalRate) {
		this.journalRate = journalRate;
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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((journalAcc == null) ? 0 : journalAcc.hashCode());
		result = prime * result + ((journalAmt == null) ? 0 : journalAmt.hashCode());
		result = prime * result + ((journalCrAmt == null) ? 0 : journalCrAmt.hashCode());
		result = prime * result + ((journalDrId == null) ? 0 : journalDrId.hashCode());
		result = prime * result + ((journalDesc == null) ? 0 : journalDesc.hashCode());
		result = prime * result + ((journalDrAmt == null) ? 0 : journalDrAmt.hashCode());
		result = prime * result + ((journalDrCrType == null) ? 0 : journalDrCrType.hashCode());
		result = prime * result + ((journalDt == null) ? 0 : journalDt.hashCode());
		result = prime * result + ((journalId == null) ? 0 : journalId.hashCode());
		result = prime * result + ((journalRate == null) ? 0 : journalRate.hashCode());
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
		ActJournal other = (ActJournal) obj;
		if (journalAcc == null) {
			if (other.journalAcc != null)
				return false;
		} else if (!journalAcc.equals(other.journalAcc))
			return false;
		if (journalAmt == null) {
			if (other.journalAmt != null)
				return false;
		} else if (!journalAmt.equals(other.journalAmt))
			return false;
		if (journalCrAmt == null) {
			if (other.journalCrAmt != null)
				return false;
		} else if (!journalCrAmt.equals(other.journalCrAmt))
			return false;
		if (journalDrId == null) {
			if (other.journalDrId != null)
				return false;
		} else if (!journalDrId.equals(other.journalDrId))
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
		if (journalRate == null) {
			if (other.journalRate != null)
				return false;
		} else if (!journalRate.equals(other.journalRate))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "ActJournal [journalId=" + journalId + ", journalDrId=" + journalDrId + ", journalDt=" + journalDt
				+ ", journalDesc=" + journalDesc + ", journalAcc=" + journalAcc + ", journalAmt=" + journalAmt
				+ ", journalRate=" + journalRate + ", journalDrAmt=" + journalDrAmt + ", journalCrAmt=" + journalCrAmt
				+ ", journalDrCrType=" + journalDrCrType + "]";
	}
	
	public ActJournal(String journalId, String journalDrId, String journalDt, String journalDesc, String journalAcc,
			String journalAmt, String journalRate, String journalDrAmt, String journalCrAmt, String journalDrCrType) {
		super();
		this.journalId = journalId;
		this.journalDrId = journalDrId;
		this.journalDt = journalDt;
		this.journalDesc = journalDesc;
		this.journalAcc = journalAcc;
		this.journalAmt = journalAmt;
		this.journalRate = journalRate;
		this.journalDrAmt = journalDrAmt;
		this.journalCrAmt = journalCrAmt;
		this.journalDrCrType = journalDrCrType;
	}
	
	public ActJournal() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
