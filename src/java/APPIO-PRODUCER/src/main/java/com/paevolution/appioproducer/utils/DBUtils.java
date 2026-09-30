package com.paevolution.appioproducer.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DBUtils {

    private static final String ORACLE = "oracle";
    private static final String MYSQL = "mysql";
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    /**
     * Restituisce la query della funzione di estrazione della data, in base al tipo di DBMS.
     * Es. Nei database la funzione 
     * 
     * @param dateColumn
     *            - campo di tipo temporale del quale si vuole estrarre la data.
     * @return
     */
    public String dateFunction(String dateColumn) {

	StringBuilder builder = new StringBuilder();
	if (StringUtils.contains(this.driverClassName, ORACLE)) {
	    builder = builder.append("TRUNC(").append(dateColumn).append(") = TRUNC(SYSDATE) ");
	    log.info("dateFunction# Oracle Database TRUNC function: {}", new String(builder));
	} else if (StringUtils.contains(this.driverClassName, MYSQL)) {
	    builder = builder.append("DATE(").append(dateColumn).append(") = DATE(SYSDATE()) ");
	    log.info("dateFunction# MySQL Database DATE function: {}", new String(builder));
	}
	return new String(builder);
    }
}
