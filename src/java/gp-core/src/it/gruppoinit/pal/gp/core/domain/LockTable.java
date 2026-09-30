package it.gruppoinit.pal.gp.core.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "LOCK_TABLE")
public class LockTable {

    private String lockName;

    @Id
    @Column(name = "LOCK_NAME", unique = true, nullable = false, length = 255)
    public String getLockName() {

	return lockName;
    }

    public void setLockName(String lockName) {

	this.lockName = lockName;
    }
}