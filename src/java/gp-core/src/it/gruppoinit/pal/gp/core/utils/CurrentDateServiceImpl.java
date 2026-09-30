package it.gruppoinit.pal.gp.core.utils;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class CurrentDateServiceImpl implements ICurrentDateService {

    @Override
    public Date getCurrentDate() {

	return Calendar.getInstance().getTime();
    }
}
