package it.gruppoinit.pal.gp.core.service.exception;

import org.apache.commons.lang.StringUtils;

public class ExceptionHelper {

    private String nome;
    private StackTraceElement valore;

    public String getNome() {

	return nome;
    }

    public void setNome(String nome) {

	this.nome = nome;
    }

    public StackTraceElement getValore() {

	return valore;
    }

    public void setValore(StackTraceElement valore) {

	this.valore = valore;
    }

    @Override
    public int hashCode() {

	final int prime = 31;
	int result = 1;
	result = prime * result + ((nome == null) ? 0 : nome.hashCode());
	result = prime * result + ((valore == null) ? 0 : valore.hashCode());
	return result;
    }

    @Override
    public String toString() {

	if (StringUtils.isNotBlank(nome)) {
	    return nome;
	} else {
	    if (valore != null) {
		StringBuffer buf = new StringBuffer();
		buf.append(valore.getClassName().substring(valore.getClassName().lastIndexOf(".") + 1));
		buf.append(" - ");
		buf.append(valore.getMethodName());
		buf.append(" - ");
		buf.append(valore.getLineNumber());
		return buf.toString();
	    }
	}
	return "";
    }

    @Override
    public boolean equals(Object obj) {

	if (this == obj) {
	    return true;
	}
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	ExceptionHelper other = (ExceptionHelper) obj;
	if (nome == null) {
	    if (other.nome != null) {
		return false;
	    }
	} else if (!nome.equals(other.nome)) {
	    return false;
	}
	if (valore == null) {
	    if (other.valore != null) {
		return false;
	    }
	} else if (!valore.equals(other.valore)) {
	    return false;
	}
	return true;
    }
}
