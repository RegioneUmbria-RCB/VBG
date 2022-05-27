package com.paevolution.appiogateway.connector.ioitalia.service;

import java.util.Map;

public interface IOConnectorService extends IOBaseConnectorService {

    public Map<String, String> httpHeaders(String ocpApim);
}
