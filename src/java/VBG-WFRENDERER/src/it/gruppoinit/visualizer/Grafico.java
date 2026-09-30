package it.gruppoinit.visualizer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.Map;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.BoxView;
import javax.swing.text.ComponentView;
import javax.swing.text.Element;
import javax.swing.text.IconView;
import javax.swing.text.LabelView;
import javax.swing.text.ParagraphView;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;
import org.jgraph.JGraph;
import org.jgraph.graph.DefaultCellViewFactory;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.DefaultGraphModel;
import org.jgraph.graph.DefaultPort;
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.GraphLayoutCache;
import org.jgraph.graph.GraphModel;

public class Grafico {

    private static Logger logger = Logger.getLogger(Grafico.class.getName());

    public JGraph generateGraph(Map m) {

	logger.debug("generateGraph");
	Vector vertex_v = (Vector) m.get("node");
	Vector edge_v = (Vector) m.get("edge");
	// Construct Model and Graph
	GraphModel model = new DefaultGraphModel();
	GraphLayoutCache view = new GraphLayoutCache(model, new DefaultCellViewFactory());
	JGraph graph = new JGraph(model, view);
	// Control-drag should clone selection
	// graph.setCloneable(true);
	// Enable edit without final RETURN keystroke
	// graph.setInvokesStopCellEditing(true);
	// When over a cell, jump to its default port (we only
	// have one, anyway)
	// graph.setJumpToDefaultPort(true);
	// Insert all cells in one call
	Vector n = new Vector();
	for (int i = 0; i < vertex_v.size(); i++) {
	    // Create Vertex i
	    String[] col = ((Nodo) (vertex_v.get(i))).getColor().split(",");
	    n.add(createVertex(((Nodo) vertex_v.get(i)).getId(), ((Nodo) vertex_v.get(i)).getName(), 20, 20, 150, 100, new Color(Integer
		    .parseInt(col[0]), Integer.parseInt(col[1]), Integer.parseInt(col[2])), false));
	}
	// Create edges
	Vector e = new Vector();
	for (int i = 0; i < vertex_v.size(); i++) {
	    Vector temp = createEdges(n, ((Nodo) vertex_v.get(i)).getId(), edge_v);
	    e.addAll(temp);
	}
	DefaultGraphCell[] cells = new DefaultGraphCell[vertex_v.size() + edge_v.size()];
	Vector tot = new Vector();
	tot.addAll(n);
	tot.addAll(e);
	tot.copyInto(cells);
	// Insert the cells via the cache, so they get selected
	graph.getGraphLayoutCache().insert(cells);
	graph.setSelectionCell(cells[0]);
	return graph;
    }

    private VertexWrapper createVertex(String id, String name, double x, double y, double w, double h, Color bg, boolean raised) {

	logger.debug("createVertex:" + id);
	// Create vertex with the given name
	VertexWrapper cell = new VertexWrapper(id, "<html><center>" + name + "</center></html>");
	cell.getAttributes().put("id", id);
	// Set bounds
	// GraphConstants.setBounds(cell.getAttributes(),new Rectangle2D.Double(x, y, w, h));
	JLabel label = new JLabel();
	label.setSize((int) w, (int) h);
	GraphConstants.setBounds(cell.getAttributes(), getWrappedSize(GraphConstants.getFont(cell.getAttributes()), label, name));
	GraphConstants.setSizeable(cell.getAttributes(), false);
	GraphConstants.setMoveable(cell.getAttributes(), true);
	// Set fill color
	// GraphConstants.setAutoSize(cell.getAttributes(), true);
	if (bg != null) {
	    GraphConstants.setGradientColor(cell.getAttributes(), bg);
	    GraphConstants.setBackground(cell.getAttributes(), Color.white);
	    GraphConstants.setOpaque(cell.getAttributes(), true);
	}
	// Set raised border
	if (raised) {
	    GraphConstants.setBorder(cell.getAttributes(), BorderFactory.createRaisedBevelBorder());
	} else {
	    // Set black border
	    GraphConstants.setBorderColor(cell.getAttributes(), Color.black);
	}
	GraphConstants.setVerticalAlignment(cell.getAttributes(), JLabel.TOP);
	GraphConstants.setVerticalTextPosition(cell.getAttributes(), JLabel.TOP);
	GraphConstants.setHorizontalAlignment(cell.getAttributes(), JLabel.CENTER);
	GraphConstants.setHorizontalAlignment(cell.getAttributes(), JLabel.CENTER);
	// Add a Port
	DefaultPort port = new DefaultPort();
	cell.add(port);
	port.setParent(cell);
	return cell;
    }

    public Rectangle2D getWrappedSize(Font xx, Component comp, String text) {

	int fontHeight = comp.getFontMetrics(xx).getHeight();
	int stringWidth = comp.getFontMetrics(xx).stringWidth(text);
	int linesCount = (int) Math.floor(stringWidth / comp.getWidth());
	linesCount = Math.max(1, linesCount + 2);
	Dimension d = new Dimension(120, (fontHeight + 2) * (linesCount + 1));
	Rectangle2D rect = new Rectangle(d).getFrame();
	return rect;
    }

    private Vector createEdges(Vector cells, String vertexId, Vector edge_v) {

	logger.debug("createEdges:" + vertexId);
	Vector edge_of_vertex = new Vector();
	DefaultEdge edge;
	Ramo r;
	for (int i = 0; i < edge_v.size(); i++) {
	    r = (Ramo) edge_v.get(i);
	    if (r.getOrigine().equals(vertexId)) {
		edge = new DefaultEdge(r.getLabel());
		// Fetch the ports from the new vertices,
		// and connect them with the edge
		edge.setSource(getCellFromVertexId(cells, r.getOrigine()));
		edge.setTarget(getCellFromVertexId(cells, r.getDestinazione()));
		// Set Style for edge
		GraphConstants.setLineEnd(edge.getAttributes(), GraphConstants.ARROW_CLASSIC);
		GraphConstants.setEndFill(edge.getAttributes(), true);
		GraphConstants.setLabelAlongEdge(edge.getAttributes(), false);
		edge_of_vertex.add(edge);
	    }
	}
	return edge_of_vertex;
    }

    protected TreeNode getCellFromVertexId(Vector cells, String vertexId) {

	logger.debug("getCellFromVertexId:" + vertexId);
	VertexWrapper c;
	String id;
	for (int i = 0; i < cells.size(); i++) {
	    c = (VertexWrapper) cells.get(i);
	    id = c.getId();
	    if (id.equals(vertexId)) {
		return ((VertexWrapper) cells.get(i)).getChildAt(0);
	    }
	}
	return null;
    }
}

class MyEditorKit extends StyledEditorKit {

    public ViewFactory getViewFactory() {

	return new StyledViewFactory();
    }

    static class StyledViewFactory implements ViewFactory {

	public View create(Element elem) {

	    String kind = elem.getName();
	    if (kind != null) {
		if (kind.equals(AbstractDocument.ContentElementName)) {
		    return new LabelView(elem);
		} else if (kind.equals(AbstractDocument.ParagraphElementName)) {
		    return new ParagraphView(elem);
		} else if (kind.equals(AbstractDocument.SectionElementName)) {
		    return new CenteredBoxView(elem, View.Y_AXIS);
		} else if (kind.equals(StyleConstants.ComponentElementName)) {
		    return new ComponentView(elem);
		} else if (kind.equals(StyleConstants.IconElementName)) {
		    return new IconView(elem);
		}
	    }
	    // default to text display
	    return new LabelView(elem);
	}
    } // class StyledViewFactory
} // class MyEditorKit

class CenteredBoxView extends BoxView {

    public CenteredBoxView(Element elem, int axis) {

	super(elem, axis);
    }

    protected void layoutMajorAxis(int targetSpan, int axis, int[] offsets, int[] spans) {

	super.layoutMajorAxis(targetSpan, axis, offsets, spans);
	int textBlockHeight = 0;
	int offset = 0;
	for (int i = 0; i < spans.length; i++) {
	    textBlockHeight += spans[i];
	}
	offset = (targetSpan - textBlockHeight) / 2;
	// offset = (targetSpan - textBlockHeight);
	for (int i = 0; i < offsets.length; i++) {
	    offsets[i] += offset;
	}
    }
}
