package com.pudugaitravels.pojo;

public class Ledger {
	
	
	
	private String ledgerDate;
	private String ledgerDescription;
	private String ledgerDrAmt;
	private String ledgerCrAmt;
	private String trType;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ledgerCrAmt == null) ? 0 : ledgerCrAmt.hashCode());
		result = prime * result + ((ledgerDate == null) ? 0 : ledgerDate.hashCode());
		result = prime * result + ((ledgerDescription == null) ? 0 : ledgerDescription.hashCode());
		result = prime * result + ((ledgerDrAmt == null) ? 0 : ledgerDrAmt.hashCode());
		result = prime * result + ((trType == null) ? 0 : trType.hashCode());
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
		Ledger other = (Ledger) obj;
		if (ledgerCrAmt == null) {
			if (other.ledgerCrAmt != null)
				return false;
		} else if (!ledgerCrAmt.equals(other.ledgerCrAmt))
			return false;
		if (ledgerDate == null) {
			if (other.ledgerDate != null)
				return false;
		} else if (!ledgerDate.equals(other.ledgerDate))
			return false;
		if (ledgerDescription == null) {
			if (other.ledgerDescription != null)
				return false;
		} else if (!ledgerDescription.equals(other.ledgerDescription))
			return false;
		if (ledgerDrAmt == null) {
			if (other.ledgerDrAmt != null)
				return false;
		} else if (!ledgerDrAmt.equals(other.ledgerDrAmt))
			return false;
		if (trType == null) {
			if (other.trType != null)
				return false;
		} else if (!trType.equals(other.trType))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Ledger [ledgerDate=" + ledgerDate + ", ledgerDescription=" + ledgerDescription
				+ ", ledgerDrAmt=" + ledgerDrAmt + ", ledgerCrAmt=" + ledgerCrAmt + ", trType=" + trType + "]";
	}

	
	
	public Ledger(String ledgerDate, String ledgerDescription, String ledgerDrAmt, String ledgerCrAmt,
			String trType) {
		super();
		
		this.ledgerDate = ledgerDate;
		this.ledgerDescription = ledgerDescription;
		this.ledgerDrAmt = ledgerDrAmt;
		this.ledgerCrAmt = ledgerCrAmt;
		this.trType = trType;
	}

	
	public String getLedgerDate() {
		return ledgerDate;
	}

	public void setLedgerDate(String ledgerDate) {
		this.ledgerDate = ledgerDate;
	}

	public String getLedgerDescription() {
		return ledgerDescription;
	}

	public void setLedgerDescription(String ledgerDescription) {
		this.ledgerDescription = ledgerDescription;
	}

	public String getLedgerDrAmt() {
		return ledgerDrAmt;
	}

	public void setLedgerDrAmt(String ledgerDrAmt) {
		this.ledgerDrAmt = ledgerDrAmt;
	}

	public String getLedgerCrAmt() {
		return ledgerCrAmt;
	}

	public void setLedgerCrAmt(String ledgerCrAmt) {
		this.ledgerCrAmt = ledgerCrAmt;
	}

	public String getTrType() {
		return trType;
	}

	public void setTrType(String trType) {
		this.trType = trType;
	}
	
	
	
	
	
	
	

}
