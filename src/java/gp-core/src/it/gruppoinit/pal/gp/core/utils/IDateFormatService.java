package it.gruppoinit.pal.gp.core.utils;

import java.util.Date;

public interface IDateFormatService {

    public Date getDateDDMMYYYY(String data);

    public Date getDate(String data, String format);
}
