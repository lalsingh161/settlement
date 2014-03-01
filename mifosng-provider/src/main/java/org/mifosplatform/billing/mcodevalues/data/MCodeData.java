package org.mifosplatform.billing.mcodevalues.data;

public class MCodeData {

	private Long id;
	private String mCodeValue;
	private String type;

	public MCodeData() {
		// TODO Auto-generated constructor stub
	}

	public MCodeData(final Long id, final String mCodeValue) {
		this.id = id;
		this.mCodeValue = mCodeValue;

	}

	public MCodeData(Long id, String codeValue, String type) {
		this.id = id;
		this.mCodeValue = codeValue;
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getmCodeValue() {
		return mCodeValue;
	}

	public void setmCodeValue(String mCodeValue) {
		this.mCodeValue = mCodeValue;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}