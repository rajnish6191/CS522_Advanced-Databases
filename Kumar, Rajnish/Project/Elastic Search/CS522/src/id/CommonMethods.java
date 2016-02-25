package id;

import org.apache.commons.lang3.StringUtils;

public class CommonMethods {

	public String cleanseMail(String cleanID_Email) {
		cleanID_Email = cleanID_Email.replaceAll(
				"[^\\w^\\@^\\.^\\_^\\%^\\+^\\-]", "");
		cleanID_Email = StringUtils.remove(cleanID_Email, ' ');
		return StringUtils.trim(cleanID_Email).toUpperCase();
	}

	public String cleanse(String cleanID) {
		cleanID = cleanID.replaceAll("[^\\w]", "");
		cleanID = StringUtils.remove(cleanID, ' ');
		return StringUtils.trim(cleanID).toUpperCase();
	}

}