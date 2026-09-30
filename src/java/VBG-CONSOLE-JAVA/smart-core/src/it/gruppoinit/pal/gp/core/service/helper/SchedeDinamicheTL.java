package it.gruppoinit.pal.gp.core.service.helper;

public class SchedeDinamicheTL {

    private static ThreadLocal<Boolean> renderForPrintTL = new ThreadLocal<Boolean>();

    public static Boolean getRenderForPrint() {

	return renderForPrintTL.get();
    }

    public static void setRenderForPrint(Boolean renderForPrint) {

	renderForPrintTL.set(renderForPrint);
    }

    public static void resetTL() {

	SchedeDinamicheTL.setRenderForPrint(null);
    }
}
