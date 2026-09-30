package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.OggettiFileSystemDAO;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.domain.web.OggettiFileSystemStatusBean;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.OggettiFileSystemService;

import java.util.List;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OggettiFileSystemServiceImpl extends BaseServiceImpl<Oggetti, PkId> implements OggettiFileSystemService {

    @Autowired
    private VerticalizzazioniService verticalizzazioniService;
    @Autowired
    private OggettiFileSystemDAO oggettiFSDAO;
    private OggettiFileSystemStatusBean status;

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.service.BaseService#update(java.lang.Object)
     */
    @Override
    public void update(Oggetti entity) {

	// TODO Auto-generated method stub
    }

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.service.BaseService#findById(java.io.Serializable)
     */
    @Override
    public Oggetti findById(PkId id) {

	// TODO Auto-generated method stub
	return null;
    }

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.service.OggettiFileSystemService#ottimizzaFileSystem()
     */
    @Override
    public int ottimizzaFileSystem(int objectCount) {

	int countMoved = 0;
	// §§§BEGIN§§§
	String rootPath = getFileRepositoryPath();
	initStatus();
	if (StringUtils.isNotEmpty(rootPath)) {
	    if (objectCount < 0) {
		objectCount = countDocumentsAtRoot();
	    }
	    this.status.setRunning(true);
	    this.status.setRootPath(rootPath);
	    this.status.setCountTotal(objectCount);
	    countMoved = oggettiFSDAO.ottimizzaFileSystem(rootPath, this.status);
	    oggettiFSDAO.verificaIncongruenze(status);
	    this.status.setRunning(false);
	} else {
	    throw new RuntimeException(
		    "Impossibile procedre all'ottimizzazione del file system degli oggetti perchè non è impostato il percorso root per l'archiviazione dei documenti.");
	}
	// §§§END§§§
	return countMoved;
    }

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.service.OggettiFileSystemService#ottimizzaFileSystem()
     */
    @Override
    public int spostaBlobSuFileSystem(Boolean setBlobNull, Integer objectCount, Integer maxDocs, Integer maxMinutes, Boolean verificaIncongruenze,
	    Integer minCodiceOggetto) {

	int countMoved = 0;
	// §§§BEGIN§§§
	String rootPath = getFileRepositoryPath();
	initStatus();
	if (StringUtils.isNotEmpty(rootPath)) {
	    this.status.setRunning(true);
	    if (objectCount == null || objectCount < 0) {
		objectCount = countDocumentsInBlob();
	    }
	    int numDocsElab = objectCount;
	    if (maxDocs != null && maxDocs > 0) {
		numDocsElab = Math.min(objectCount, maxDocs);
	    }
	    if (maxMinutes == null) {
		maxMinutes = 0;
	    }
	    this.status.setCountTotal(numDocsElab);
	    this.status.setRootPath(rootPath);
	    countMoved = oggettiFSDAO.spostaBlobSuFileSystem(rootPath, setBlobNull, numDocsElab, maxMinutes, this.status, minCodiceOggetto);
	    if (BooleanUtils.isTrue(verificaIncongruenze)) {
		oggettiFSDAO.verificaIncongruenze(status);
	    }
	    this.status.setRunning(false);
	} else {
	    throw new RuntimeException(
		    "Impossibile procedre allo spostamento dei BLOB su file system degli oggetti perchè non è impostato il percorso root per l'archiviazione dei documenti.");
	}
	// §§§END§§§
	return countMoved;
    }

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.service.OggettiFileSystemService#countDocumentsAtRoot()
     */
    @Override
    public int countDocumentsAtRoot() {

	return oggettiFSDAO.countDocumentsAtRoot();
    }

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.service.OggettiFileSystemService#countDocumentsAtRoot()
     */
    @Override
    public int countDocumentsInBlob() {

	return oggettiFSDAO.countDocumentsInBlob();
    }

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.service.impl.BaseServiceImpl#getEntityClass()
     */
    @Override
    protected Class<Oggetti> getEntityClass() {

	return Oggetti.class;
    }

    @Override
    public void insert(Oggetti entity) {

	// TODO Auto-generated method stub
    }

    @Override
    public void delete(Oggetti entity) {

	// TODO Auto-generated method stub
    }

    @Override
    public List<Oggetti> findAll(Integer firstResult, Integer maxResult) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public String getFileRepositoryPath() {

	String path = null;
	// §§§BEGIN§§§
	Verticalizzazioniparametri verticalizzazioniparametri = verticalizzazioniService.getVerticalizzazioniparametri(
		WebConstants.VERTICALIZZAZIONE_FILESYSTEM, WebConstants.VERTICALIZZAZIONE_FILESYSTEM_DIRECTORY_LOCALE);
	if (verticalizzazioniparametri != null) {
	    path = verticalizzazioniparametri.getValore();
	}
	// §§§END§§§
	return path;
    }

    @Override
    public OggettiFileSystemStatusBean getStatus() {

	return status;
    }

    private OggettiFileSystemStatusBean initStatus() {

	this.status = new OggettiFileSystemStatusBean();
	return this.status;
    }
}
