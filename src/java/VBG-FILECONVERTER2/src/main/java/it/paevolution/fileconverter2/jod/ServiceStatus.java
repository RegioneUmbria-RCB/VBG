package it.paevolution.fileconverter2.jod;

public class ServiceStatus {

	public static enum SERVICE_STATUS {
		UP, DOWN, UNDEFINED
	}

	private String pid;
	private String unoUrl;
	private String serviceName;
	private String serviceProperties;
	private SERVICE_STATUS serviceStatus;

	public ServiceStatus() {

	}

	public SERVICE_STATUS getServiceStatus() {

		return serviceStatus;
	}

	public void setServiceStatus(SERVICE_STATUS serviceStatus) {

		this.serviceStatus = serviceStatus;
	}

	public String getServiceName() {

		return serviceName;
	}

	public void setServiceName(String serviceName) {

		this.serviceName = serviceName;
	}

	public String getServiceProperties() {

		return serviceProperties;
	}

	public void setServiceProperties(String serviceProperties) {

		this.serviceProperties = serviceProperties;
	}

	@Override
	public String toString() {

		return getServiceProperties();
	}

	//// SIGAR
//	private ProcCpu procCpu;
//	private ProcMem procMem;
//	private Map procEnv;
//	private ProcExe procExe;
//	private ProcState procState;
//
//	private void inizializeInfos() {
//
//		if (infos != null) {
//			long pid = infos.getPid();
//			if (pid != -1) {
//				Sigar sigar = new Sigar();
//				try {
//					procCpu = sigar.getProcCpu(pid);
//				} catch (SigarException e) {
//				}
//				try {
//					procMem = sigar.getProcMem(pid);
//				} catch (SigarException e) {
//				}
//				try {
//					procEnv = sigar.getProcEnv(pid);
//				} catch (SigarException e) {
//				}
//				try {
//					procExe = sigar.getProcExe(pid);
//				} catch (SigarException e) {
//				}
//				try {
//					procState = sigar.getProcState(pid);
//				} catch (SigarException e) {
//				}
//			}
//		}
//	}

	public String getPid() {

//		if (infos != null) {
//			return infos.getPid() + "";
//		}
		return pid;
	}

	public String getUnoUrl() {

//		if (infos != null) {
//			return infos.getUnoUrl() == null ? "" : infos.getUnoUrl().getAcceptString();
//		}
		return unoUrl;
	}

	//
	public String getUsoCPUPerc() {

//		if (procCpu != null) {
//			return procCpu.getPercent() + " %";
//		}
		return "";
	}

	public String getUsoMemoriaVirtual() {

//		if (procMem != null) {
//			return procMem.getSize() == 0 ? "0" : (procMem.getSize() / 1024) / 1024 + " Mb";
//		}
		return "";
	}

	public String getUsoMemoriaResident() {

//		if (procMem != null) {
//			return procMem.getResident() == 0 ? "0" : (procMem.getResident() / 1024) / 1024 + " Mb";
//		}
		return "";
	}

	public String getStatoProcesso() {

//		if (procState != null) {
//			switch (procState.getState()) {
//			case ProcState.RUN:
//				return "RUN";
//			case ProcState.IDLE:
//				return "IDLE";
//			case ProcState.SLEEP:
//				return "SLEEP";
//			case ProcState.STOP:
//				return "STOP";
//			case ProcState.ZOMBIE:
//				return "ZOMBIE";
//			}
//		}
		return "UNDEFINED";
	}

	public String getNomeProgramma() {

//		if (procState != null) {
//			return procState.getName();
//		}
		return "";
	}

	public String getPidProcessoPadre() {

//		if (procState != null) {
//			return procState.getPpid() + "";
//		}
		return "";
	}

	public String getVariabiliAmbienteProcesso() {

		String result = "";
//		if (procEnv != null) {
//			Set<Entry<String, String>> entries = procEnv.entrySet();
//			result = "[";
//			for (Entry<String, String> entry : entries) {
//				result += entry.getKey() + "=" + entry.getValue() + "\n";
//			}
//			result += "]";
//		}
		return result;
	}
}
