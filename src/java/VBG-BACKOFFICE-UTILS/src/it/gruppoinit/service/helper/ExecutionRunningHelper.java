package it.gruppoinit.service.helper;

public class ExecutionRunningHelper {

    private static boolean executionIsRunning = false;

    public static boolean isExecutionIsRunning() {

	return executionIsRunning;
    }

    public static void setExecutionIsRunning(boolean executionIsRunning) {

	ExecutionRunningHelper.executionIsRunning = executionIsRunning;
    }
}
