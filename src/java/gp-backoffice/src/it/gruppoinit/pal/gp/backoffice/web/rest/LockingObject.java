package it.gruppoinit.pal.gp.backoffice.web.rest;

import java.io.Serializable;

public class LockingObject implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1642672065273166029L;

    public LockingObject(String lockName) {

	this.lockName = lockName;
    }

    private String lockName;

    public String getLockName() {

	return lockName;
    }

    public void setLockName(String lockName) {

	this.lockName = lockName;
    }
}
