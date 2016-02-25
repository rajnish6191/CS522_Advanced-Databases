package id;

import org.apache.commons.lang3.StringUtils;

public class UniID {

	private String userGivenAcNo = "";
	private String userGivenID1 = "";
	private String userGivenID2 = "";
	private String userGivenID3 = "";
	private String userGivenID4 = "";
	private String userGivenID5 = "";
	private String userGivenID6 = "";
	private String userGivenID7 = "";
	private String userGivenEM1 = "";
	private String userGivenEM2 = "";
	private String userGivenOID = "";
	private String iD1 = "";
	private String iD2 = "";
	private String iD3 = "";
	private String iD4 = "";
	private String iD5 = "";
	private String iD6 = "";
	private String iD7 = "";
	private String email = "";
	private String otherId = "";

	public void setUserGivenAcNo(String userGivenAcNo) {
		this.userGivenAcNo = userGivenAcNo;
	}

	public String getUserGivenAcNo() {
		return userGivenAcNo;
	}

	public void setOtherId(String otherId) {
		if (!(StringUtils.contains(this.otherId, otherId)))
			this.otherId = this.otherId + " " + otherId;
		this.otherId = StringUtils.trim(StringUtils.remove(this.otherId, "@"));
		this.otherId = StringUtils.replaceChars(this.otherId, " ", "^");
	}

	public String getOtherID() {
		return otherId;
	}

	public void setID1(String iD1) {
		if (!(StringUtils.contains(this.iD1, iD1)))
			this.iD1 = this.iD1 + " " + iD1;
		this.iD1 = StringUtils.trim(this.iD1);
		this.iD1 = StringUtils.replaceChars(this.iD1, " ", "^");
	}

	public String getID1() {
		return iD1;
	}

	public void setID2(String iD2) {
		if (!(StringUtils.contains(this.iD2, iD2)))
			this.iD2 = this.iD2 + " " + iD2;
		this.iD2 = StringUtils.trim(this.iD2);
		this.iD2 = StringUtils.replaceChars(this.iD2, " ", "^");
	}

	public String getID2() {
		return iD2;
	}

	public void setID3(String iD3) {
		if (!(StringUtils.contains(this.iD3, iD3)))
			this.iD3 = this.iD3 + " " + iD3;
		this.iD3 = StringUtils.trim(this.iD3);
		this.iD3 = StringUtils.replaceChars(this.iD3, " ", "^");
	}

	public String getID3() {
		return iD3;
	}

	public void setID4(String iD4) {
		if (!(StringUtils.contains(this.iD4, iD4)))
			this.iD4 = this.iD4 + " " + iD4;
		this.iD4 = StringUtils.trim(this.iD4);
		this.iD4 = StringUtils.replaceChars(this.iD4, " ", "^");
	}

	public String getID4() {
		return iD4;
	}

	public void setID5(String iD5) {
		if (!(StringUtils.contains(this.iD5, iD5)))
			this.iD5 = this.iD5 + " " + iD5;
		this.iD5 = StringUtils.trim(this.iD5);
		this.iD5 = StringUtils.replaceChars(this.iD5, " ", "^");
	}

	public String getID5() {
		return iD5;
	}

	public void setID6(String iD6) {
		if (!(StringUtils.contains(this.iD6, iD6)))
			this.iD6 = this.iD6 + " " + iD6;

		this.iD6 = StringUtils.trim(this.iD6);
		this.iD6 = StringUtils.replaceChars(this.iD6, " ", "^");
	}

	public String getID6() {
		return iD6;
	}
	
	public void setID7(String iD7) {
		if (!(StringUtils.contains(this.iD7, iD7)))
			this.iD7 = this.iD7 + " " + iD7;

		this.iD7 = StringUtils.trim(this.iD7);
		this.iD7 = StringUtils.replaceChars(this.iD7, " ", "^");
	}
	
	public String getID7() {
		return iD7;
	}
 
 	public void setEmail(String email) {
		if (!(StringUtils.containsIgnoreCase(this.email, email)))
			this.email = this.email + " " + email;

		this.email = StringUtils.trim(this.email);
		this.email = StringUtils.replaceChars(this.email, " ", "^");
		this.email = this.email.toLowerCase();
	}

	public String getEmail() {
		return email;
	}

	public void setUserGivenID1(String userGivenID1) {
		if (StringUtils.isBlank(userGivenID1))
			this.userGivenID1 = "";
		this.userGivenID1 = userGivenID1;

	}

	public void setUserGivenID2(String userGivenID2) {
		if (StringUtils.isBlank(userGivenID2))
			this.userGivenID2 = "";
		this.userGivenID2 = userGivenID2;
	}

	public void setUserGivenID3(String userGivenID3) {
		if (StringUtils.isBlank(userGivenID3))
			this.userGivenID3 = "";
		this.userGivenID3 = userGivenID3;
	}

	public void setUserGivenID4(String userGivenID4) {
		if (StringUtils.isBlank(userGivenID4))
			this.userGivenID4 = "";
		this.userGivenID4 = userGivenID4;
	}

	public void setUserGivenID5(String userGivenID5) {
		if (StringUtils.isBlank(userGivenID5))
			this.userGivenID5 = "";
		this.userGivenID5 = userGivenID5;
	}

	public void setUserGivenID6(String userGivenID6) {
		if (StringUtils.isBlank(userGivenID6))
			this.userGivenID6 = "";
		this.userGivenID6 = userGivenID6;
	}
	
	public void setUserGivenID7(String userGivenID7) {
		if (StringUtils.isBlank(userGivenID7))
			this.userGivenID7 = "";
		this.userGivenID7 = userGivenID7;
	}
	
	public void setUserGivenEM1(String userGivenEM1) {
		if (StringUtils.isBlank(userGivenEM1))
			this.userGivenEM1 = "";
		this.userGivenEM1 = userGivenEM1;
	}

	public void setUserGivenEM2(String userGivenEM2) {
		if (StringUtils.isBlank(userGivenEM2))
			this.userGivenEM2 = "";
		this.userGivenEM2 = userGivenEM2;
	}

	public void setUserGivenOID(String userGivenOID) {
		if (StringUtils.isBlank(userGivenOID))
			this.userGivenOID = "";
		this.userGivenOID = userGivenOID;
	}

	public String getUserGivenOID() {
		return userGivenOID;
	}

	public String getUserGivenID1() {
		return userGivenID1;
	}

	public String getUserGivenID2() {
		return userGivenID2;
	}

	public String getUserGivenID3() {
		return userGivenID3;
	}

	public String getUserGivenID4() {
		return userGivenID4;
	}

	public String getUserGivenID5() {
		return userGivenID5;
	}

	public String getUserGivenID6() {
		return userGivenID6;
	}

	public String getUserGivenID7() {
		return userGivenID7;
	}
	
	public String getUserGivenEM1() {
		return userGivenEM1;
	}

	public String getUserGivenEM2() {
		return userGivenEM2;
	}

	
	@Override
	public String toString() {
		return userGivenAcNo + "|" + userGivenID1 + "|" + userGivenID2 + "|" + userGivenID3 + "|" + userGivenID4 + "|"
				+ userGivenID5 + "|" + userGivenID6 + "|" + userGivenID7 + "|" + userGivenEM1 + "|" + userGivenEM2 + "|"
				+ userGivenOID + "|" + iD1 + "|" + iD2 + "|" + iD3 + "|" + iD4 + "|" + iD5 + "|" + iD6 + "|" + iD7 + "|"
				+ email + "|" + otherId;
	}

	
	
	

}
