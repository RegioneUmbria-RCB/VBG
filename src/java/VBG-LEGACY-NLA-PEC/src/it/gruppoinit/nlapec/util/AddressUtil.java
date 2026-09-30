package it.gruppoinit.nlapec.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.mail.Address;

public class AddressUtil {

    public static String getAsString(Address[] addresses) {

	StringBuffer sb = new StringBuffer();
	if (addresses != null) {
	    for (int i = 0; i < addresses.length; i++) {
		sb.append(addresses[i].toString()).append(";");
	    }
	}
	return sb.toString();
    }

    public static List<Address> getAsList(Address[] addresses) {

	List<Address> list = new ArrayList<Address>();
	if (addresses != null) {
	    list = Arrays.asList(addresses);
	}
	return list;
    }
}
