package it.alveo.ricalcoloaree.bean;

import java.util.Objects;

public class DbConfBean {

    private String username;
    private String pwd;
    private String connectionString;
    private String provider;

    public DbConfBean() {

        super();
        // TODO Auto-generated constructor stub
    }

    public DbConfBean(String username, String pwd, String connectionString, String provider) {

        super();
        this.username = username;
        this.pwd = pwd;
        this.connectionString = connectionString;
        this.provider = provider;
    }

    public String getUsername() {

        return username;
    }

    public void setUsername(String username) {

        this.username = username;
    }

    public String getPwd() {

        return pwd;
    }

    public void setPwd(String pwd) {

        this.pwd = pwd;
    }

    public String getConnectionString() {

        return connectionString;
    }

    public void setConnectionString(String connectionString) {

        this.connectionString = connectionString;
    }

    public String getProvider() {

        return provider;
    }

    public void setProvider(String provider) {

        this.provider = provider;
    }

    @Override
    public int hashCode() {

        return 1;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DbConfBean other = (DbConfBean) obj;
        return Objects.equals(connectionString, other.connectionString) && Objects.equals(provider, other.provider) && Objects.equals(pwd, other.pwd)
                && Objects.equals(username, other.username);
    }
}
