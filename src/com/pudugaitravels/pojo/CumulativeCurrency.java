package com.pudugaitravels.pojo;

public class CumulativeCurrency implements Comparable<CumulativeCurrency>{
	
	private String ctryValue;
	private String currValue;
	
	
	
	
	
	
	
	
	
	public CumulativeCurrency(String ctryValue, String currValue) {
		super();
		this.ctryValue = ctryValue;
		this.currValue = currValue;
	}

	public CumulativeCurrency() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "CumulativeCurrency [ctryValue=" + ctryValue + ", currValue=" + currValue + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ctryValue == null) ? 0 : ctryValue.hashCode());
		result = prime * result + ((currValue == null) ? 0 : currValue.hashCode());
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
		CumulativeCurrency other = (CumulativeCurrency) obj;
		if (ctryValue == null) {
			if (other.ctryValue != null)
				return false;
		} else if (!ctryValue.equals(other.ctryValue))
			return false;
		if (currValue == null) {
			if (other.currValue != null)
				return false;
		} else if (!currValue.equals(other.currValue))
			return false;
		return true;
	}
	
	public String getCtryValue() {
		return ctryValue;
	}
	public void setCtryValue(String ctryValue) {
		this.ctryValue = ctryValue;
	}
	public String getCurrValue() {
		return currValue;
	}
	public void setCurrValue(String currValue) {
		this.currValue = currValue;
	}

	@Override
	public int compareTo(CumulativeCurrency o) {
		// TODO Auto-generated method stub
		return this.getCtryValue().compareTo(o.getCtryValue());
	}
	
	

}
