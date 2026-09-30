package it.gruppoinit.pal.firma;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionManager {

    private static final Map<String, Map<String, FileInfo>> map = new ConcurrentHashMap<String, Map<String, FileInfo>>();
    private static final Logger log = LoggerFactory.getLogger(SessionManager.class.getName());

    public static void put(FileInfo fileInfo) {

	log.info("put: fileInfo={}", fileInfo);
	if (map.containsKey(fileInfo.getSessionId())) {
	    map.get(fileInfo.getSessionId()).put(fileInfo.getFileId(), fileInfo);
	} else {
	    Map<String, FileInfo> fileMap = new HashMap<String, FileInfo>();
	    fileMap.put(fileInfo.getFileId(), fileInfo);
	    map.put(fileInfo.getSessionId(), fileMap);
	}
	log.info("current map content: {}", map);
    }

    public static FileInfo get(String sessionId, String fileId) {

	Map<String, FileInfo> fileMap = map.get(sessionId);
	FileInfo fileInfo = (FileInfo) fileMap.get(fileId);
	log.info("get: sessionId={} return fileInfo={}", sessionId, fileInfo);
	return fileInfo;
    }

    public static void checkSession(String sessionId) {

	boolean success = map.containsKey(sessionId);
	log.info("checkSession: sessionId={} return {}", sessionId, success);
	if (!success) {
	    log.warn("checkSession: sessionId={} not present! throw exception", sessionId);
	    throw new RuntimeException("Session not found: " + sessionId);
	}
    }

    public static void removeSession(String sessionId) {

	log.info("removeSession: sessionId={}", sessionId);
	try {
	    map.remove(sessionId);
	} catch (Exception e) {
	    log.error("removeSession: sessionId={}", sessionId, e);
	}
    }

    public static String startNewSession() {

	String sessionId = UUID.randomUUID().toString();
	log.info("startNewSession: sessionId={}", sessionId);
	map.put(sessionId, new HashMap<String, FileInfo>());
	return sessionId;
    }

    public static Map<String, FileInfo> list(String sessionId) {

	boolean success = (map != null && StringUtils.isNotBlank(sessionId) && map.containsKey(sessionId));
	Map<String, FileInfo> sessionInfo = null;
	if (success) {
	    sessionInfo = map.get(sessionId);
	}
	log.debug("list: sessionId={} return {}", sessionId, sessionInfo);
	return sessionInfo;
    }
}
