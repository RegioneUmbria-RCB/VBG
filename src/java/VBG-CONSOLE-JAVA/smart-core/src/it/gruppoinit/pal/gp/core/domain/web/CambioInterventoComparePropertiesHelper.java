package it.gruppoinit.pal.gp.core.domain.web;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class CambioInterventoComparePropertiesHelper {

    private boolean presente;
    private boolean readonly;
    private boolean checked;

    public boolean isPresente() {

	return presente;
    }

    public void setPresente(boolean presente) {

	this.presente = presente;
    }

    public boolean isReadonly() {

	return readonly;
    }

    public void setReadonly(boolean readonly) {

	this.readonly = readonly;
    }

    public boolean isChecked() {

	return checked;
    }

    public void setChecked(boolean checked) {

	this.checked = checked;
    }

    @Override
    public String toString() {

	return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
