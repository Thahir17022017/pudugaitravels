package com.pudugaitravels.pojo;

public class TrialBal {
	
	private String accName="";
	private String debitBal="";
	private String creditBal="";
	
	
	public TrialBal(String accName, String debitBal, String creditBal) {
		super();
		this.accName = accName;
		this.debitBal = debitBal;
		this.creditBal = creditBal;
	}

	public String getAccName() {
		return accName;
	}

	public void setAccName(String accName) {
		this.accName = accName;
	}

	public String getDebitBal() {
		return debitBal;
	}

	public void setDebitBal(String debitBal) {
		this.debitBal = debitBal;
	}

	public String getCreditBal() {
		return creditBal;
	}

	public void setCreditBal(String creditBal) {
		this.creditBal = creditBal;
	}

	@Override
	public String toString() {
		return "TrialBal [accName=" + accName + ", debitBal=" + debitBal + ", creditBal=" + creditBal + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accName == null) ? 0 : accName.hashCode());
		result = prime * result + ((creditBal == null) ? 0 : creditBal.hashCode());
		result = prime * result + ((debitBal == null) ? 0 : debitBal.hashCode());
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
		TrialBal other = (TrialBal) obj;
		if (accName == null) {
			if (other.accName != null)
				return false;
		} else if (!accName.equals(other.accName))
			return false;
		if (creditBal == null) {
			if (other.creditBal != null)
				return false;
		} else if (!creditBal.equals(other.creditBal))
			return false;
		if (debitBal == null) {
			if (other.debitBal != null)
				return false;
		} else if (!debitBal.equals(other.debitBal))
			return false;
		return true;
	}
	
	
	
	
	
	

}
