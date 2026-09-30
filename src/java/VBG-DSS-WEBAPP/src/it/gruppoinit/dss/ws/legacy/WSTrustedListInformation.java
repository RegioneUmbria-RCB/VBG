package it.gruppoinit.dss.ws.legacy;

import java.util.Date;

public class WSTrustedListInformation {

    private boolean serviceWasFound;
    private String tspName;
    private String tspTradeName;
    private String tspPostalAddress;
    private String tspElectronicAddress;
    private String serviceType;
    private String serviceName;
    private String currentStatus;
    private Date currentStatusStartingDate;
    private String statusAtReferenceTime;
    private Date statusStartingDateAtReferenceTime;

    public WSTrustedListInformation() {}

    public boolean isServiceWasFound() { return serviceWasFound; }
    public void setServiceWasFound(boolean serviceWasFound) { this.serviceWasFound = serviceWasFound; }

    public String getTspName() { return tspName; }
    public void setTspName(String tspName) { this.tspName = tspName; }

    public String getTspTradeName() { return tspTradeName; }
    public void setTspTradeName(String tspTradeName) { this.tspTradeName = tspTradeName; }

    public String getTspPostalAddress() { return tspPostalAddress; }
    public void setTspPostalAddress(String tspPostalAddress) { this.tspPostalAddress = tspPostalAddress; }

    public String getTspElectronicAddress() { return tspElectronicAddress; }
    public void setTspElectronicAddress(String tspElectronicAddress) { this.tspElectronicAddress = tspElectronicAddress; }

    public String getServiceType() { return serviceType; }
    public void setServiceType(String serviceType) { this.serviceType = serviceType; }

    public String getServiceName() { return serviceName; }
    public void setServiceName(String serviceName) { this.serviceName = serviceName; }

    public String getCurrentStatus() { return currentStatus; }
    public void setCurrentStatus(String currentStatus) { this.currentStatus = currentStatus; }

    public Date getCurrentStatusStartingDate() { return currentStatusStartingDate; }
    public void setCurrentStatusStartingDate(Date currentStatusStartingDate) { this.currentStatusStartingDate = currentStatusStartingDate; }

    public String getStatusAtReferenceTime() { return statusAtReferenceTime; }
    public void setStatusAtReferenceTime(String statusAtReferenceTime) { this.statusAtReferenceTime = statusAtReferenceTime; }

    public Date getStatusStartingDateAtReferenceTime() { return statusStartingDateAtReferenceTime; }
    public void setStatusStartingDateAtReferenceTime(Date statusStartingDateAtReferenceTime) { this.statusStartingDateAtReferenceTime = statusStartingDateAtReferenceTime; }
}
