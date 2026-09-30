package it.gruppoinit.pal.gp.backoffice.jws;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URI;
import java.util.concurrent.ExecutionException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

public class EditDocsPanel extends JPanel implements ActionListener {

    private static final long serialVersionUID = -5169625749273474100L;
    private JProgressBar progressBar;
    private JLabel statusLabel;
    private JButton btnInviaModifiche;
    private TransferUtils2 transferUtils;
    private boolean debug;
    private String labelBtnInviaModifiche;
    private String labelDownloadCompletato;
    private String urlOggetti;
    private String urlUploadOggetti;
    private String filePath;

    public EditDocsPanel(String urlOggetti, String urlUploadOggetti, String labelBtnInviaModifiche, String labelDownloadCompletato, boolean debug,
	    String appName) {

	System.out.println(appName);
	this.urlOggetti = urlOggetti;
	this.urlUploadOggetti = urlUploadOggetti;
	this.labelBtnInviaModifiche = labelBtnInviaModifiche;
	this.labelDownloadCompletato = labelDownloadCompletato;
	this.debug = debug;
	//
	createGUI();
	//Set listener for download progressbar update
	progressBarDownloadTask.addPropertyChangeListener(new PropertyChangeListener() {

	    public void propertyChange(PropertyChangeEvent evt) {

		if ("progress".equals(evt.getPropertyName())) {
		    progressBar.setValue((Integer) evt.getNewValue());
		}
	    }
	});
	//Start loading the file in the background.
	statusLabel.setForeground(Color.BLACK);
	statusLabel.setText("Caricamento in corso...");
	task.execute();
	progressBarDownloadTask.execute();
    }

    private void createGUI() {

	setLayout(new GridLayout(2, 1));
	setBackground(Color.WHITE);
	JPanel statusPanel = new JPanel();
	JPanel labelPanel = new JPanel();
	statusPanel.setBackground(Color.WHITE);
	labelPanel.setBackground(Color.WHITE);
	statusLabel = new JLabel();
	progressBar = new JProgressBar(0, 100);
	statusPanel.add(statusLabel);
	statusPanel.add(progressBar);
	btnInviaModifiche = new JButton(labelBtnInviaModifiche);
	btnInviaModifiche.addActionListener(this);
	btnInviaModifiche.setEnabled(false);
	labelPanel.add(statusLabel);
	statusPanel.add(btnInviaModifiche);
	add(labelPanel);
	add(statusPanel);
	transferUtils = new TransferUtils2(debug);
    }

    public void actionPerformed(ActionEvent e) {

	if (!task.isDone()) {
	    return;
	}
	statusLabel.setForeground(Color.BLACK);
	statusLabel.setText("Salvataggio in corso...");
	btnInviaModifiche.setEnabled(false);
	//Set listener for upload progressbar update
	progressBarUploadTask.addPropertyChangeListener(new PropertyChangeListener() {

	    public void propertyChange(PropertyChangeEvent evt) {

		if ("progress".equals(evt.getPropertyName())) {
		    progressBar.setValue((Integer) evt.getNewValue());
		}
	    }
	});
	uploadTask.execute();
	progressBarUploadTask.execute();
    }

    private String downloadFileAndSave() throws Exception {

	log("downloadFile(urlOggetti=" + urlOggetti + ")");
	return transferUtils.downloadFile(urlOggetti);
    }

    private void uploadFile() throws Exception {

	log("uploadFile(urlUploadOggetti=" + urlUploadOggetti + ",filePath=" + filePath + ")");
	transferUtils.uploadFile(urlUploadOggetti, filePath);
    }

    private void log(String messaggio) {

	if (debug) {
	    System.out.println("DEBUG: " + messaggio);
	}
    }

    private void displayError(String error) {

	Toolkit.getDefaultToolkit().beep();
	JOptionPane.showMessageDialog(null, error, "Errore.", JOptionPane.ERROR_MESSAGE);
	System.err.println(error);
    }

    //Background task for loading file.     
    SwingWorker<String, Integer> task = new SwingWorker<String, Integer>() {

	@Override
	public String doInBackground() {

	    String path = "";
	    try {
		path = downloadFileAndSave();
		filePath = path;
		if ("".equals(filePath)) {
		    throw new Exception("File non trovato.");
		}
		filePath = filePath.replaceAll("\\\\", "/");
		Desktop.getDesktop().browse(new URI(filePath));
		statusLabel.setForeground(Color.decode("#016C10"));
		statusLabel.setText(labelDownloadCompletato);
		btnInviaModifiche.setEnabled(true);
	    } catch (Exception e) {
		statusLabel.setForeground(Color.decode("#B61218"));
		statusLabel.setText("Errore durante il caricamento.");
		displayError(e.getMessage());
		e.printStackTrace();
	    }
	    return path;
	}

	@Override
	protected void done() {

	    progressBarDownloadTask.cancel(true);
	}
    };
    //Background task for loading file.     
    SwingWorker<String, Integer> uploadTask = new SwingWorker<String, Integer>() {

	@Override
	public String doInBackground() {

	    try {
		uploadFile();
		statusLabel.setForeground(Color.decode("#016C10"));
		statusLabel.setText("Salvataggio completato!");
		return "";
	    } catch (Exception e1) {
		displayError(e1.getMessage());
		statusLabel.setForeground(Color.decode("#B61218"));
		statusLabel.setText("Errore durante il salvataggio.");
		e1.printStackTrace();
	    }
	    return null;
	}

	@Override
	protected void done() {

	    try {
		String uploadresult = get();
		if (uploadresult != null) {
		    closeApp();
		}
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    } catch (ExecutionException e) {
		e.printStackTrace();
	    }
	}
    };

    private void closeApp() {

	System.exit(0);
    }

    //Background task for updating progress bar during file download.     
    SwingWorker<String, Integer> progressBarDownloadTask = new SwingWorker<String, Integer>() {

	@Override
	public String doInBackground() {

	    double progress = 0;
	    while (progress < 100) {
		if (transferUtils != null) {
		    if (transferUtils.getContentLength() > 0) {
			double tot = transferUtils.getContentLength();
			progress = 100 * (double) transferUtils.getContentLoaded() / (double) tot;
			//log("download: " + progress + "%");
			setProgress((int) progress);
		    }
		}
	    }
	    return "";
	}

	@Override
	protected void done() {

	}
    };
    //Background task for updating progress bar during file upload.     
    SwingWorker<String, Integer> progressBarUploadTask = new SwingWorker<String, Integer>() {

	@Override
	public String doInBackground() {

	    double progress = 0;
	    while (progress < 100) {
		if (transferUtils != null) {
		    if (transferUtils.getContentLength() > 0) {
			double tot = transferUtils.getContentLength();
			progress = 100 * (double) transferUtils.getContentLoaded() / (double) tot;
			//log("upload: " + progress + "%");
			setProgress((int) progress);
		    }
		}
	    }
	    return "";
	}

	@Override
	protected void done() {

	}
    };
}
