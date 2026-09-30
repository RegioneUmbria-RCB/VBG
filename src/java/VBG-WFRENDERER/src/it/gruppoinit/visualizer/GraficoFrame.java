package it.gruppoinit.visualizer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.apache.log4j.Logger;
import org.jgraph.JGraph;
import org.jgraph.graph.DefaultGraphModel;
import org.jgraph.plugins.layouts.AnnealingLayoutAlgorithm;
import org.jgraph.plugins.layouts.AnnealingLayoutSettings;
import org.jgraph.plugins.layouts.CircleGraphLayout;
import org.jgraph.plugins.layouts.JGraphLayoutAlgorithm;
import org.jgraph.plugins.layouts.SpringEmbeddedLayoutAlgorithm;
import org.jgraph.plugins.layouts.SugiyamaLayoutAlgorithm;


public class GraficoFrame extends JFrame{
	
	private JButton sugi;
	private JButton print;
	private JButton circle;
	private JButton annealing;
	private JButton spring;
	protected JGraph graph;
	protected Object[] cells;
	protected String file;
	public static Logger logger = Logger.getLogger(GraficoFrame.class.getName());
	
	public GraficoFrame(JGraph graph, String file){

		super();
		this.file = file;
		this.graph = graph;
		this.setLayout(new BorderLayout());
		this.setState(GraficoFrame.MAXIMIZED_BOTH);
		this.getContentPane().add(new JScrollPane(graph),BorderLayout.CENTER);
		JPanel actionPanel = new JPanel();
		circle = new JButton("Circle Layout");
		actionPanel.add(circle);
		spring = new JButton("Spring Layout");
		actionPanel.add(spring);
		sugi = new JButton("Sugiyama Layout");
		actionPanel.add(sugi);
		annealing = new JButton("Annealing Layout");
		actionPanel.add(annealing);
		print = new JButton("Stampa");
		actionPanel.add(print);
		this.getContentPane().add(actionPanel,BorderLayout.SOUTH);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		this.setResizable(true);
		this.setVisible(true);
		
		
		circle.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	setLayout(new CircleGraphLayout());
            }
		});
		spring.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	setLayout(new SpringEmbeddedLayoutAlgorithm());
            }
		});
		
		sugi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	SugiyamaLayoutAlgorithm sugi = new SugiyamaLayoutAlgorithm();
            	/*
            	SugiyamaLayoutSettings set = (SugiyamaLayoutSettings)sugi.createSettings();
            	set.setFlushToOrigin(true);
            	set.setVerticalSpacing("400");
            	set.setVerticalDirection(true);
            	set.setIndention("400");
            	set.apply();
            	*/
            	setLayout(sugi);
            }
		});
		
		
		annealing.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	AnnealingLayoutAlgorithm ann = new AnnealingLayoutAlgorithm();
            	AnnealingLayoutSettings set = (AnnealingLayoutSettings)ann.createSettings();
            	ArrayList lambdaLU1 = new ArrayList();
            	//COSTFUNCTION_NODE_DISTANCE
                lambdaLU1.add(new Double(10000000));
                //COSTFUNCTION_NODE_DISTRIBUTION
                lambdaLU1.add(new Double(10000000));
                //COSTFUNCTION_BORDERLINE
                lambdaLU1.add(new Double(0.02));
                //COSTFUNCTION_EDGE_LENGTH
                lambdaLU1.add(new Double(200000.0));
                //COSTFUNCTION_EDGE_CROSSING
                lambdaLU1.add(new Double(15000.0));
                //COSTFUNCTION_EDGE_DISTANCE
                lambdaLU1.add(new Double(1000000));
            	set.setLambda(lambdaLU1);
            	set.apply();
            	setLayout(ann);
            }
		});
		
		print.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {           	
            	//saveGraphAsJPG();
            	saveGraphAsPNG();
            }
		});

	}
	
	protected void setLayout(JGraphLayoutAlgorithm layout){
		Object[] cells = DefaultGraphModel.getAll(graph.getModel());
		JGraphLayoutAlgorithm.applyLayout(graph, layout, cells);
		logger.debug(layout.toString());
	}
	
	protected void saveGraphAsJPG()
    {
    	try {
            Object[] cells = DefaultGraphModel.getAll(graph.getModel());
            Dimension dp = graph.getPreferredSize();
            Dimension d1 = new Dimension((int)dp.getWidth()+100,(int)dp.getHeight()+100);
            graph.setSize(d1);
    		BufferedImage img = null;
    		//Object cells[] = graph.getRoots();
    		if(cells.length > 0){
    			Rectangle bounds = graph.getCellBounds(cells).getBounds();
    			graph.toScreen(bounds);
    			Dimension d = bounds.getSize();
    			img = new BufferedImage(d.width+100, d.height+100, 1);
    			java.awt.Graphics2D graphics = img.createGraphics();
    			graph.paint(graphics);
    		}
    		FileOutputStream fos = new FileOutputStream(file);
		ImageIO.write(img, "jpg", fos);
		fos.flush();
		fos.close();
        	logger.debug("save image to:"+file);
    	}catch (Exception e){
    		e.printStackTrace();
    	}
    }
	
	protected void saveGraphAsPNG()
    {
		try {
			File  f = new File(file);
			Color bg = null;
			bg = graph.getBackground();
			BufferedImage img = graph.getImage(bg, 100);
			ImageIO.write(img, "png", f);
			logger.debug("save png image to:"+file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    }
	


}
