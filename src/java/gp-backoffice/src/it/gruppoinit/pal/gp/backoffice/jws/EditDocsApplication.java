package it.gruppoinit.pal.gp.backoffice.jws;

import javax.swing.JFrame;

public class EditDocsApplication extends JFrame {

    private static final long serialVersionUID = -3989203462827709896L;
    private static final String appName = "Edit Docs v.2.0";

    public static void main(String[] args) {

	String urlOggetti = args[0].equals("null") ? null : args[0];
	String urlUploadOggetti = args[1].equals("null") ? null : args[1];
	String fileId = args[2].equals("null") ? null : args[2];
	String token = args[3].equals("null") ? null : args[3];
	String labelBtnInviaModifiche = args[4].equals("null") ? "INVIA MODIFICHE" : args[4];
	String labelDownloadCompletato = args[5].equals("null") ? "Caricamento completato." : args[5];
	String _debug = args[6].equals("null") ? null : args[6];
	boolean debug = false;
	if ("true".equals(_debug)) {
	    debug = true;
	}
	String qm1 = "?", qm2 = "?";
	if (urlOggetti.indexOf("?") != -1) {
	    qm1 = "&";
	}
	if (urlUploadOggetti.indexOf("?") != -1) {
	    qm2 = "&";
	}
	urlOggetti += qm1 + "fileId=" + fileId + "&Token=" + token + "&ts=" + System.currentTimeMillis();
	urlUploadOggetti += qm2 + "fileId=" + fileId + "&Token=" + token + "&ts=" + System.currentTimeMillis();
	EditDocsApplication app = new EditDocsApplication();
	app.createGUI(urlOggetti, urlUploadOggetti, labelBtnInviaModifiche, labelDownloadCompletato, debug);
    }

    private void createGUI(String urlOggetti, String urlUploadOggetti, String labelBtnInviaModifiche, String labelDownloadCompletato, boolean debug) {

	EditDocsPanel contentPane = new EditDocsPanel(urlOggetti, urlUploadOggetti, labelBtnInviaModifiche, labelDownloadCompletato, debug, appName);
	contentPane.setOpaque(true);
	setContentPane(contentPane);
	setLocationRelativeTo(null);
	setTitle(appName);
	setDefaultCloseOperation(EXIT_ON_CLOSE);
	pack();
	setVisible(true);
    }
}
