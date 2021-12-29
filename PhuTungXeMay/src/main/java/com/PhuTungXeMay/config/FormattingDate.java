package com.PhuTungXeMay.config;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormattingDate {
	public static Date StringToDate(String dob) throws ParseException {
	      SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	      Date date = formatter.parse(dob);
	      return date;
	}
}
