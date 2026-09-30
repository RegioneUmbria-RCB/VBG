package it.gruppoinit.visualizer;

import it.gruppoinit.sigeprosecurity.schema.AmbienteType;
import it.gruppoinit.sigeprosecurity.schema.CheckTokenRequest;
import it.gruppoinit.sigeprosecurity.schema.CheckTokenResponse;
import it.gruppoinit.sigeprosecurity.schema.ContestoType;
import it.gruppoinit.sigeprosecurity.schema.GetDbConnectionInfoRequest;
import it.gruppoinit.sigeprosecurity.schema.GetDbConnectionInfoResponse;
import it.gruppoinit.sigeprosecurity.schema.LoginRequest;
import it.gruppoinit.sigeprosecurity.schema.LoginResponse;
import it.gruppoinit.sigeprosecurity.schema.TokenInfoType;
import it.gruppoinit.sigeprosecurity.ws.SigeproSecurity;
import it.gruppoinit.sigeprosecurity.ws.SigeproSecurityService;
import it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceLocator;
import it.gruppoinit.sigeprosecurity.ws.SigeproSecuritySoap11Stub;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.swing.tree.TreeNode;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.rpc.ServiceException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.axis.EngineConfiguration;
import org.apache.axis.configuration.FileProvider;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jgraph.JGraph;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphModel;
import org.jgraph.graph.GraphConstants;
import org.jgraph.plugins.layouts.JGraphLayoutAlgorithm;
import org.jgraph.plugins.layouts.SugiyamaLayoutAlgorithm;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class Main {

    private static Logger logger = Logger.getLogger(Main.class.getName());

    public byte[] generaGrafico(String alias, String codiceprocedura, String color) throws Exception {

	Properties p = getDeployProperties();
	SigeproSecurity sigeproSecurity = this.getSigeproSecurityPort(p);
	LoginRequest loginRequest = new LoginRequest(alias, ContestoType.APP, "", "", "");
	LoginResponse loginResponse = sigeproSecurity.login(loginRequest);
	return generaGraficoDaToken(loginResponse.getToken(), Integer.valueOf(codiceprocedura), color);
    }

    public byte[] generaGraficoDaToken(String token, int codiceprocedura, String color) throws Exception {

	if (StringUtils.isBlank(token)) {
	    throw new ServletException("Attenzione! Per la generazione del grafico è necessario un token.");
	}
	Properties p = getDeployProperties();
	SigeproSecurity sigeproSecurity = this.getSigeproSecurityPort(p);
	CheckTokenResponse checkTokenResponse = sigeproSecurity.checkToken(new CheckTokenRequest(token, true));
	if (checkTokenResponse == null) {
	    throw new ServletException("method checkToken for token=" + token + " return CheckTokenResponse=null!");
	}
	TokenInfoType tokenInfoType = checkTokenResponse.getTokenInfo();
	if (tokenInfoType == null) {
	    throw new ServletException("method checkToken for token=" + token + " return TokenInfoType=null!");
	}
	String alias = tokenInfoType.getAlias();
	// recupero le informazioni per la connessione al db
	GetDbConnectionInfoResponse info = sigeproSecurity.getDbConnectionInfo(new GetDbConnectionInfoRequest(alias, AmbienteType.JAVA));
	if (info == null) {
	    throw new ServletException(
		    "method getDbConnectionInfo for idcomunealias=" + alias + " and token=" + token + " return GetDbConnectionInfoResponse=null!");
	}
	// recupero la stringa del provider per selezionare il driver
	String provider = info.getProvider();
	if (StringUtils.isBlank(provider)) {
	    throw new ServletException("method getDbConnectionInfo for idcomunealias=" + alias + " and token=" + token + " return provider=null!");
	}
	Properties dbProperties = this.getDbProperties();
	String dbDriver = dbProperties.getProperty("db.driver." + provider);
	if (StringUtils.isBlank(dbDriver)) {
	    throw new ServletException("driver non found in db.properties: " + dbDriver);
	}
	if (StringUtils.isBlank(color)) {
	    color = p.getProperty("graph.color");
	}
	Map map = readProcedura(info.getIdComune(), info.getConnectionString(), dbDriver, info.getDbUser(), info.getDbPassword(), codiceprocedura,
		color);
	return execute(map);
    }

    private SigeproSecurity getSigeproSecurityPort(Properties p) throws MalformedURLException, ServiceException {

	InputStream is = Main.class.getClassLoader().getResourceAsStream("deploy_client.wsdd");
	EngineConfiguration config = new FileProvider(is);
	SigeproSecurityService service = new SigeproSecurityServiceLocator(config);
	SigeproSecuritySoap11Stub port;
	URL sigeprosecurtyUrl = new URL(p.getProperty("ws.token.url"));
	port = (SigeproSecuritySoap11Stub) service.getsigeproSecuritySoap11(sigeprosecurtyUrl);
	port.setUsername(p.getProperty("ws.token.user"));
	port.setPassword(p.getProperty("ws.token.pwd"));
	port.setTimeout(5000);
	return port;
    }

    public Properties getDeployProperties() {

	InputStream in = null;
	try {
	    Properties p = new Properties();
	    in = Main.class.getClassLoader().getResourceAsStream("deploy.properties");
	    p.load(in);
	    return p;
	} catch (Exception e) {
	    logger.error("getDeployProperties(): error loading deploy.properties " + e.getMessage());
	    throw new RuntimeException("Errore durante il caricamento del file di configurazione: " + e);
	} finally {
	    if (in != null) {
		try {
		    in.close();
		} catch (IOException e) {
		}
	    }
	}
    }

    public Properties getDbProperties() {

	InputStream in = null;
	try {
	    Properties p = new Properties();
	    in = Main.class.getClassLoader().getResourceAsStream("db.properties");
	    p.load(in);
	    return p;
	} catch (Exception e) {
	    logger.error("getDbProperties(): error loading db.properties " + e.getMessage());
	    throw new RuntimeException("Errore durante il caricamento del file di configurazione: " + e);
	} finally {
	    if (in != null) {
		try {
		    in.close();
		} catch (IOException e) {
		}
	    }
	}
    }

    private byte[] execute(Map map) {

	// Remove DAG
	Vector edgeRemoved = removeDAG(map);
	// Create JGraph object
	Grafico g = new Grafico();
	JGraph graph = g.generateGraph(map);
	SugiyamaLayoutAlgorithm sugi = new SugiyamaLayoutAlgorithm();
	setLayout(sugi, graph);
	calculateEdgePoints(edgeRemoved, graph);
	// Show Frame
	//new GraficoFrame(graph, file);
	// Save JPG
	/*
	        Object[] cells = DefaultGraphModel.getAll(graph.getModel());
	        SugiyamaLayoutAlgorithm sugi = new SugiyamaLayoutAlgorithm();
	        sugi.run(graph, cells, null);
	        Dimension d = graph.getPreferredSize();
	        Dimension d1 = new Dimension((int)d.getWidth()+100,(int)d.getHeight()+100);
	        graph.setSize(d1);
	        
	        m.saveGraphAsJPG(file, graph);
	*/
	return saveGraphAsPNG(graph);
    }

    private Map readProcedura(String idcomune, String url, String driver, String user, String pwd, int codiceprocedura, String color)
	    throws Exception {

	logger.debug("reading mov for: idcomune=" + idcomune + ", codiceprocedura=" + codiceprocedura);
	if (StringUtils.isBlank(color)) {
	    color = "113,213,234";
	}
	TipiMovimentoReader tpr = new TipiMovimentoReader(driver, url, user, pwd);
	tpr.setCodiceProcedura(codiceprocedura);
	tpr.setIdcomune(idcomune);
	tpr.setColor(color);
	Nodo mov = tpr.getMovimentoAvvioDefault();
	if (mov == null) {
	    throw new RuntimeException("Nessun movimento di avvio. [codiceprocedura=" + codiceprocedura + ", idcomune=" + idcomune + "]");
	}
	tpr.addMovDefault(mov);
	tpr.visitaCMov(mov);
	logger.debug("NODI");
	logger.debug(tpr.getMovimentiVisitati());
	logger.debug("RAMI");
	logger.debug(tpr.getRamiCreati());
	Map m = new HashMap();
	m.put("node", tpr.getMovimentiVisitati());
	m.put("edge", tpr.getRamiCreati());
	return m;
    }

    private String readFile(String file) throws IOException {

	File f = new File(file);
	StringBuffer fileData = new StringBuffer(1000);
	BufferedReader reader = new BufferedReader(new FileReader(f));
	char[] buf = new char[1024];
	int numRead = 0;
	while ((numRead = reader.read(buf)) != -1) {
	    String readData = String.valueOf(buf, 0, numRead);
	    fileData.append(readData);
	    buf = new char[1024];
	}
	reader.close();
	return fileData.toString();
    }

    private Map parse(String xmlString) throws SAXException, IOException, ParserConfigurationException, XPathExpressionException {

	logger.debug("parse");
	Map m = new HashMap();
	Vector nodeVector = new Vector();
	Vector edgeVector = new Vector();
	Vector keyVector = new Vector();
	m.put("node", nodeVector);
	m.put("edge", edgeVector);
	logger.debug("validazione xml...");
	//validate(xmlString);
	logger.debug("validazione xml...ok!");
	//eseguo il parsing dell'xmlBody
	DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
	builderFactory.setValidating(false);
	builderFactory.setNamespaceAware(false);
	DocumentBuilder builder = builderFactory.newDocumentBuilder();
	Document document = builder.parse(new InputSource(new StringReader(xmlString)));
	//estraggo i valori dei nodi
	logger.debug("estrazione valori dall'xml...");
	XPath xpath = XPathFactory.newInstance().newXPath();
	String expression = "/graphml/key";
	NodeList key = (NodeList) xpath.evaluate(expression, document, XPathConstants.NODESET);
	Key k;
	for (int i = 0; i < key.getLength(); i++) {
	    k = creaChiave(key.item(i));
	    keyVector.add(k);
	}
	expression = "/graphml/graph/node";
	NodeList node = (NodeList) xpath.evaluate(expression, document, XPathConstants.NODESET);
	Nodo n;
	for (int i = 0; i < node.getLength(); i++) {
	    n = creaNodo(node.item(i), keyVector);
	    nodeVector.add(n);
	}
	expression = "/graphml/graph/edge";
	NodeList edge = (NodeList) xpath.evaluate(expression, document, XPathConstants.NODESET);
	Ramo r;
	for (int i = 0; i < edge.getLength(); i++) {
	    r = creaRamo(edge.item(i), keyVector);
	    edgeVector.add(r);
	}
	logger.debug("estrazione valori dall'xml...ok!");
	return m;
    }

    private Key creaChiave(Node node) {

	NamedNodeMap attrs = node.getAttributes();
	NodeList nl = node.getChildNodes();
	String _default = null;
	for (int i = 0; i < nl.getLength(); i++) {
	    if (nl.item(i).getNodeType() == Node.ELEMENT_NODE) {
		Node def = nl.item(i).getFirstChild();
		if (def != null) {
		    _default = def.getNodeValue();
		}
	    }
	}
	Key k = new Key(attrs.getNamedItem("id").getNodeValue(), attrs.getNamedItem("for").getNodeValue(),
		attrs.getNamedItem("attr.name").getNodeValue(), attrs.getNamedItem("attr.type").getNodeValue(), _default);
	return k;
    }

    private Nodo creaNodo(Node node, Vector keys) {

	NamedNodeMap attrs = node.getAttributes();
	Map m = getData(node);
	String name = null;
	String color = null;
	String id = attrs.getNamedItem("id").getNodeValue();
	String k;
	k = resolveType(keys, "name");
	name = (String) m.get(k);
	if (name == null) {
	    name = id;
	}
	k = resolveType(keys, "color");
	color = (String) m.get(k);
	if (color == null) {
	    color = getDefault(keys, k);
	    if (color == null) {
		color = "255,255,255";
	    }
	}
	Nodo n = new Nodo(id, name, color);
	return n;
    }

    private Ramo creaRamo(Node node, Vector keys) {

	NamedNodeMap attrs = node.getAttributes();
	Map m = getData(node);
	String label = "";
	label = (String) m.get(resolveType(keys, "name"));
	Ramo r = new Ramo(attrs.getNamedItem("source").getNodeValue(), attrs.getNamedItem("target").getNodeValue(), label);
	return r;
    }

    private Map getData(Node n) {

	HashMap m = new HashMap();
	NodeList nl = n.getChildNodes();
	for (int i = 0; i < nl.getLength(); i++) {
	    if (nl.item(i).getNodeType() == Node.ELEMENT_NODE) {
		Node d = nl.item(i);
		m.put(d.getAttributes().getNamedItem("key").getNodeValue(), d.getFirstChild().getNodeValue());
	    }
	}
	return m;
    }

    private String resolveType(Vector keys, String name) {

	String type = null;
	for (int i = 0; i < keys.size(); i++) {
	    Key k = (Key) keys.get(i);
	    if (k.get_name().equals(name)) {
		type = k.get_id();
		break;
	    }
	}
	return type;
    }

    private String getDefault(Vector keys, String id) {

	String def = null;
	for (int i = 0; i < keys.size(); i++) {
	    Key k = (Key) keys.get(i);
	    if (k.get_id().equals(id)) {
		def = k.get_default();
		break;
	    }
	}
	return def;
    }

    private boolean validate(String xmlString) throws SAXException, IOException {

	// 1. Lookup a factory for the W3C XML Schema language
	SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
	// 2. Compile the schema. 
	// Here the schema is loaded from a java.io.File, but you could use 
	// a java.net.URL or a javax.xml.transform.Source instead.
	//File schemaLocation = new File("D:\\eclipse3.1_workspace\\JGraph\\graphml.xsd");
	URL schemaLocation = new URL("http://graphml.graphdrawing.org/xmlns/1.0/graphml.xsd");
	Schema schema = factory.newSchema(schemaLocation);
	// 3. Get a validator from the schema.
	Validator validator = schema.newValidator();
	// 4. Parse the document you want to check.
	Source source = new StreamSource(new StringReader(xmlString));
	// 5. Check the document
	validator.validate(source);
	return true;
    }

    //    private void saveGraphAsJPG(String file, JGraph graph) {
    //
    //	try {
    //	    BufferedImage img = null;
    //	    Object cells[] = graph.getRoots();
    //	    if (cells.length > 0) {
    //		Rectangle bounds = graph.getCellBounds(cells).getBounds();
    //		graph.toScreen(bounds);
    //		Dimension d = bounds.getSize();
    //		img = new BufferedImage(d.width + 100, d.height + 100, 1);
    //		java.awt.Graphics2D graphics = img.createGraphics();
    //		graph.paint(graphics);
    //	    }
    //	    FileOutputStream fos = new FileOutputStream(file);
    //	    JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(fos);
    //	    encoder.encode(img);
    //	    fos.flush();
    //	    fos.close();
    //	} catch (Exception e) {
    //	    e.printStackTrace();
    //	}
    //    }
    private byte[] saveGraphAsPNG(JGraph graph) {

	File f = null;
	String file = Calendar.getInstance().getTimeInMillis() + "-" + (int) (Math.random() * 10) + ".png";
	try {
	    f = File.createTempFile("grafico_", file);
	    Color bg = null;
	    graph.setSize(graph.getPreferredSize());
	    bg = graph.getBackground();
	    BufferedImage img = graph.getImage(bg, 100);
	    ImageIO.write(img, "png", f);
	    logger.debug("save png image to:" + file);
	    return getBytesFromFile(f);
	} catch (FileNotFoundException e) {
	    logger.error(e);
	} catch (IOException e) {
	    logger.error(e);
	}
	return null;
    }

    private void setLayout(JGraphLayoutAlgorithm layout, JGraph graph) {

	Object[] cells = DefaultGraphModel.getAll(graph.getModel());
	JGraphLayoutAlgorithm.applyLayout(graph, layout, cells);
	logger.debug("Applying layout: " + layout.toString());
    }

    private Vector removeDAG(Map m) {

	Vector nodeVector = (Vector) m.get("node");
	Vector edgeVector = (Vector) m.get("edge");
	Vector edgeRemoved = new Vector();
	for (int i = 0; i < nodeVector.size(); i++) {
	    Nodo nodo = (Nodo) nodeVector.get(i);
	    String id = nodo.getId();
	    int counter = 0;
	    for (int j = 0; j < edgeVector.size(); j++) {
		Ramo r = (Ramo) edgeVector.get(j);
		if (r.getDestinazione().equals(id)) {
		    if (counter > 0) {
			edgeRemoved.add(r);
		    }
		    counter++;
		}
	    }
	    edgeVector.removeAll(edgeRemoved);
	}
	logger.debug("RAMI RIMOSSI: " + edgeRemoved);
	return edgeRemoved;
    }

    private void calculateEdgePoints(Vector edgeRemoved, JGraph graph) {

	Object[] cells = DefaultGraphModel.getAll(graph.getModel());
	Point source = null;
	Point target = null;
	for (int i = 0; i < edgeRemoved.size(); i++) {
	    String origine = ((Ramo) edgeRemoved.get(i)).getOrigine();
	    String destinazione = ((Ramo) edgeRemoved.get(i)).getDestinazione();
	    for (int j = 0; j < cells.length; j++) {
		if (cells[j] instanceof VertexWrapper) {
		    String id = ((VertexWrapper) cells[j]).getId();
		    if (id.equals(origine)) {
			Rectangle2D rect = (Rectangle2D) ((VertexWrapper) cells[j]).getAttributes().get(GraphConstants.BOUNDS);
			source = new Point((int) rect.getCenterX(), (int) rect.getCenterY());
		    } else if (id.equals(destinazione)) {
			Rectangle2D rect = (Rectangle2D) ((VertexWrapper) cells[j]).getAttributes().get(GraphConstants.BOUNDS);
			target = new Point((int) rect.getCenterX(), (int) rect.getCenterY());
		    } else {
			logger.debug("cella da scartare");
		    }
		}
	    }
	    DefaultEdge edge = createRemovedEdge((Ramo) edgeRemoved.get(i), calculateExtraPoints(source, target), graph);
	    graph.getGraphLayoutCache().insert(edge);
	    graph.removeSelectionCell(edge);
	}
    }

    private DefaultEdge createRemovedEdge(Ramo r, List extraPoints, JGraph graph) {

	Object[] cells = DefaultGraphModel.getAll(graph.getModel());
	DefaultEdge edge = new DefaultEdge(r.getLabel());
	// Fetch the ports from the new vertices, 
	// and connect them with the edge
	edge.setSource(getCellFromVertexId(cells, r.getOrigine()));
	edge.setTarget(getCellFromVertexId(cells, r.getDestinazione()));
	// Set Style for edge	
	GraphConstants.setLineEnd(edge.getAttributes(), GraphConstants.ARROW_CLASSIC);
	GraphConstants.setEndFill(edge.getAttributes(), true);
	GraphConstants.setLabelAlongEdge(edge.getAttributes(), false);
	Point2D p = new Point2D.Double(GraphConstants.PERMILLE / 10, 20);
	GraphConstants.setLabelPosition(edge.getAttributes(), p);
	GraphConstants.setPoints(edge.getAttributes(), extraPoints);
	//GraphConstants.setLineStyle(edge.getAttributes(), GraphConstants.STYLE_BEZIER);
	return edge;
    }

    private TreeNode getCellFromVertexId(Object[] cells, String vertexId) {

	logger.debug("getCellFromVertexId:" + vertexId);
	VertexWrapper c;
	String id;
	for (int i = 0; i < cells.length; i++) {
	    if (cells[i] instanceof VertexWrapper) {
		c = (VertexWrapper) cells[i];
		id = c.getId();
		if (id.equals(vertexId)) {
		    return ((VertexWrapper) cells[i]).getChildAt(0);
		}
	    }
	}
	return null;
    }

    private List calculateExtraPoints(Point p0, Point p1) {

	if (p1 == null)
	    p1 = (Point) p0.clone();
	logger.debug("calculateExtraPoints: p0=" + p0 + ", p1=" + p1);
	int nodoW = 150;
	int nodoH = 100;
	int spacingX = 250;
	int spacingY = 150;
	Point A = null;
	Point B = null;
	Point C = null;
	ArrayList extraPoints = new ArrayList();
	if ((p0.x - p1.x) < 0) {
	    logger.debug("calculateExtraPoints: dx<0");
	    if ((p0.y - p1.y) > 0) {
		//dy>0
		logger.debug("calculateExtraPoints: dy>0");
		A = new Point(p0.x + spacingX / 2, p0.y - spacingY / 2);
		logger.debug("point A: " + A);
		B = new Point(p1.x - spacingX / 2, A.y);
		logger.debug("point B: " + B);
		C = new Point(B.x, p1.y + spacingY / 2);
		logger.debug("point C: " + C);
	    } else if ((p0.y - p1.y) < 0) {
		//dy<0
		logger.debug("calculateExtraPoints: dy<0");
		A = new Point(p0.x + spacingX / 2, p0.y + spacingY / 2);
		logger.debug("point A: " + A);
		B = new Point(p1.x - spacingX / 2, A.y);
		logger.debug("point B: " + B);
		C = new Point(B.x, p1.y - spacingY / 2);
		logger.debug("point C: " + C);
	    } else {
		//dy=0
		logger.debug("calculateExtraPoints: dy=0");
		A = new Point(p0.x + spacingX / 2, p0.y + spacingY / 2);
		logger.debug("point A: " + A);
		B = new Point(p1.x - spacingX / 2, A.y);
		logger.debug("point B: " + B);
	    }
	} else if ((p0.x - p1.x) > 0) {
	    // dx>0
	    logger.debug("calculateExtraPoints: dx>0");
	    if ((p0.y - p1.y) > 0) {
		//dy>0
		logger.debug("calculateExtraPoints: dy>0");
		A = new Point(p0.x - spacingX / 2, p0.y - spacingY / 2);
		logger.debug("point A: " + A);
		B = new Point(p1.x + spacingX / 2, A.y);
		logger.debug("point B: " + B);
		C = new Point(B.x, p1.y + spacingY / 2);
		logger.debug("point C: " + C);
	    } else if ((p0.y - p1.y) < 0) {
		//dy<0
		logger.debug("calculateExtraPoints: dy<0");
		A = new Point(p0.x - spacingX / 2, p0.y + spacingY / 2);
		logger.debug("point A: " + A);
		B = new Point(p1.x + spacingX / 2, A.y);
		logger.debug("point B: " + B);
		C = new Point(B.x, p1.y - spacingY / 2);
		logger.debug("point C: " + C);
	    } else {
		//dy=0
		logger.debug("calculateExtraPoints: dy=0");
		A = new Point(p0.x - spacingX / 2, p0.y + spacingY / 2);
		logger.debug("point A: " + A);
		B = new Point(p1.x + spacingX / 2, A.y);
		logger.debug("point B: " + B);
	    }
	} else {
	    //dx=0
	    logger.debug("calculateExtraPoints: dx=0");
	    if ((p0.y - p1.y) > 0) {
		//dy>0
		logger.debug("calculateExtraPoints: dy>0");
		A = new Point(p0.x + spacingX / 2, p0.y - spacingY / 2);
		logger.debug("point A: " + A);
		B = new Point(p1.x + spacingX / 2, A.y);
		logger.debug("point B: " + B);
		C = new Point(B.x, p1.y + spacingY / 2);
		logger.debug("point C: " + C);
	    } else if ((p0.y - p1.y) < 0) {
		//dy<0
		logger.debug("calculateExtraPoints: dy<0");
		A = new Point(p0.x + spacingX / 2, p0.y + spacingY / 2);
		logger.debug("point A: " + A);
		B = new Point(p1.x + spacingX / 2, A.y);
		logger.debug("point B: " + B);
		C = new Point(B.x, p1.y - spacingY / 2);
		logger.debug("point C: " + C);
	    } else {
		//dy=0
		logger.debug("calculateExtraPoints: dy=0");
		A = new Point(p0.x - spacingX / 2, p0.y - nodoH / 2);
		logger.debug("point A: " + A);
		B = new Point(p1.x + spacingX / 2, A.y + nodoH / 2);
		logger.debug("point B: " + B);
	    }
	}
	extraPoints.add(p0);
	extraPoints.add(A);
	extraPoints.add(B);
	if (C != null)
	    extraPoints.add(C);
	extraPoints.add(p1);
	return extraPoints;
    }

    // Returns the contents of the file in a byte array.
    private byte[] getBytesFromFile(File file) throws IOException {

	InputStream is = new FileInputStream(file);
	// Get the size of the file
	long length = file.length();
	// You cannot create an array using a long type.
	// It needs to be an int type.
	// Before converting to an int type, check
	// to ensure that file is not larger than Integer.MAX_VALUE.
	if (length > Integer.MAX_VALUE) {
	    // File is too large
	}
	// Create the byte array to hold the data
	byte[] bytes = new byte[(int) length];
	// Read in the bytes
	int offset = 0;
	int numRead = 0;
	while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
	    offset += numRead;
	}
	// Ensure all the bytes have been read in
	if (offset < bytes.length) {
	    throw new IOException("Could not completely read file " + file.getName());
	}
	// Close the input stream and return bytes
	is.close();
	// Delete the file
	logger.debug("deleting file: " + file.getName());
	file.delete();
	return bytes;
    }
}
