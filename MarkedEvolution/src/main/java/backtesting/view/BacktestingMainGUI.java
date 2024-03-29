package backtesting.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import backtesting.BackTest;
import backtesting.order.OrdersManager;
import backtesting.strategy.Evaluator;
import backtesting.strategy.Strategy;
import main.Test;
import model.Constants;
import model.Experiment;
import model.algorithm.BasicSearchAlgorithm;
import model.grammar.Parser;
import model.individual.Individual;
import model.individual.Population;

/**
 *
 * @author fabrizioortega
 */
public class BacktestingMainGUI extends javax.swing.JFrame {
	DefaultTableModel indTableModel;
	Vector<Vector<Object>>tableData;
	Vector<Object>header;
	JLabel backtestImg;
	BacktestRenderer backtestRenderer;
	IndividualSimulation indSimulation;
	Experiment experiment;
	boolean indStop,evoStop;
	Properties properties;
	int iterations,currentIteration;
    /**
     * Creates new form MainGUI
     */
    public BacktestingMainGUI() {
    	tableData=new Vector<Vector<Object>>();
    	header=new Vector<Object>();
    	header.add("individual");
    	header.add("fitness");
    	indTableModel = new DefaultTableModel(tableData,header);
    	
//    	indSimulation = new IndividualSimulation();
//        backtestRenderer = new BacktestRenderer(indSimulation.backtest.getData());
//        backtestRenderer.setCursor(indSimulation.cursor);
//        backtestImg = new JLabel(new ImageIcon(backtestRenderer.init(41)));
//        backtestRenderer.setOrdersManager(indSimulation.ordersManager); 
        initComponents();
//        jScrollPaneBacktestingVisualization.setViewportView(backtestImg);
//        this.jbStopInd.setEnabled(false);
//        indStop = true;
        
        
        ListSelectionModel cellSelectionModel = this.jIndividualTable.getSelectionModel();
		cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
		        public void valueChanged(ListSelectionEvent e) {
		        	runIndSimulationFromTableSelection(jIndividualTable.getSelectedRow());
	
		
		        }

				
		});
        
        experiment = new Test(new SwingSearchAlgorithm());
        properties = new Properties();
		try { 
			properties.load(new FileInputStream(new File("resources/properties/default.properties")));
		} catch (IOException e) {e.printStackTrace(); } 
		
		experiment.setup(properties);
		evoStop = true;
		iterations = Integer.parseInt(properties.getProperty(Constants.ITERATIONS,Constants.DEFAULT_ITERATIONS));
		currentIteration = 0;
		//experiment.run(properties);
		
    }
    public class IndividualSimulation{
    	BackTest backtest;
    	Integer cursor;
    	Strategy strategy;
    	OrdersManager ordersManager;
		public long delay;
		String code;
    	public IndividualSimulation(String code, String datafile) {
    		this.code=code;
    		backtest = new BackTest(datafile);
    		cursor = 500;
    		Parser parser = new Parser();
    		Evaluator eva = new Evaluator(parser.parse(code));
    		strategy = new Strategy();
    		strategy.setEvaluator(eva);
    		ordersManager = new OrdersManager();
    		delay = 1000;
    	}
    	public IndividualSimulation() {
    		this("if(ma25[3]>close[3]){"
    					+ "return BUY;"
    					+ "}"
    					+ "else{"
    					+ "return SELL;"
    					+ "}", "NVDA");
    	}
    }
    public class SwingSearchAlgorithm extends BasicSearchAlgorithm{
    	boolean ini=false;
    	@Override
    	public void run(int its) {
    		if(its>0 && !BacktestingMainGUI.this.evoStop) {
    			if(!ini) {
        			init();
        			ini = true;
        		}
        		else {
        			
        			currentIteration++;
        			currentIteration%=BacktestingMainGUI.this.iterations;
        			System.out.println("Starting Generation "+(currentIteration));
        			long start2 = System.currentTimeMillis();
        			step();
        			System.out.println("-----------------------------------------Genration done in: "+(System.currentTimeMillis()-start2)+"-----------------------------------------");
        			
        			BacktestingMainGUI.this.jProgressBar1.setValue(100*(currentIteration)/BacktestingMainGUI.this.iterations);
        			
        			
        		}	
    			//update table
    			updateTable(this.initPipeline.get(0).getPopulation());
    			BacktestingMainGUI.this.jIndividualTable.repaint();
    			//BacktestingMainGUI.this.jScrollPaneTable.repaint();
    		}
    		SwingUtilities.invokeLater(()->{
				this.run(its-1);
			});
    		 	
    	}
  
		private void updateTable(Population population) {

			System.out.println("updating table");
			BacktestingMainGUI.this.tableData.clear();
			for(Individual ind:population) {
				Vector<Object>v = new Vector<Object>();
				v.add(ind);
				v.add(ind.getFitness());
				BacktestingMainGUI.this.indTableModel.addRow(v);
			}
			System.out.println("new table size: "+BacktestingMainGUI.this.jIndividualTable.getRowCount());
			//BacktestingMainGUI.this.indTableModel.setValueAt("5", 0, 0);
		}
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPaneBacktestingVisualization = new javax.swing.JScrollPane();
        jScrollPaneTable = new javax.swing.JScrollPane();
        jIndividualTable = new javax.swing.JTable();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jpEvoConfig = new javax.swing.JPanel();
        jbRun = new javax.swing.JButton();
        jProgressBar1 = new javax.swing.JProgressBar();
        jbStop = new javax.swing.JButton();
        jpIndAnalysis = new javax.swing.JPanel();
        jbLoad = new javax.swing.JButton();
        jtfLoadFile = new javax.swing.JTextField();
        jbChoose = new javax.swing.JButton();
        jbLeft = new javax.swing.JButton();
        jbRight = new javax.swing.JButton();
        jSlider1 = new javax.swing.JSlider();
        jbPlayInd = new javax.swing.JButton();
        jbStopInd = new javax.swing.JButton();
        jsDelay = new javax.swing.JSlider();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(1400, 701));
        setMinimumSize(new java.awt.Dimension(1400, 701));
        setResizable(false);

        jPanel1.setMaximumSize(new java.awt.Dimension(1400, 701));
        jPanel1.setSize(new java.awt.Dimension(1400, 701));

        jScrollPaneBacktestingVisualization.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        jScrollPaneBacktestingVisualization.setMaximumSize(new java.awt.Dimension(762, 446));
        jScrollPaneBacktestingVisualization.setMinimumSize(new java.awt.Dimension(762, 446));
        jScrollPaneBacktestingVisualization.setPreferredSize(new java.awt.Dimension(762, 446));
        jScrollPaneBacktestingVisualization.setSize(new java.awt.Dimension(762, 446));

        jScrollPaneTable.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));

        jIndividualTable.setModel(
        		this.indTableModel
        		);
        jScrollPaneTable.setViewportView(jIndividualTable);

        jpEvoConfig.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));

        jbRun.setText("Run");
        jbRun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbRunActionPerformed(evt);
            }
        });

        jbStop.setText("Stop");
        jbStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbStopActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpEvoConfigLayout = new javax.swing.GroupLayout(jpEvoConfig);
        jpEvoConfig.setLayout(jpEvoConfigLayout);
        jpEvoConfigLayout.setHorizontalGroup(
            jpEvoConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpEvoConfigLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jbRun, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbStop, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(107, Short.MAX_VALUE))
        );
        jpEvoConfigLayout.setVerticalGroup(
            jpEvoConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpEvoConfigLayout.createSequentialGroup()
                .addContainerGap(386, Short.MAX_VALUE)
                .addGroup(jpEvoConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jbStop, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                    .addComponent(jbRun, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jProgressBar1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Evolution config", jpEvoConfig);

        jpIndAnalysis.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));

        jbLoad.setText("Load");
        jbLoad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbLoadActionPerformed(evt);
            }
        });

        jtfLoadFile.setText("resources/loads/test.json");

        jbChoose.setText("Choose");
        jbChoose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbChooseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpIndAnalysisLayout = new javax.swing.GroupLayout(jpIndAnalysis);
        jpIndAnalysis.setLayout(jpIndAnalysisLayout);
        jpIndAnalysisLayout.setHorizontalGroup(
            jpIndAnalysisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpIndAnalysisLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpIndAnalysisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jbLoad)
                    .addGroup(jpIndAnalysisLayout.createSequentialGroup()
                        .addComponent(jtfLoadFile, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbChoose)))
                .addContainerGap(226, Short.MAX_VALUE))
        );
        jpIndAnalysisLayout.setVerticalGroup(
            jpIndAnalysisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpIndAnalysisLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jpIndAnalysisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfLoadFile, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbChoose))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 344, Short.MAX_VALUE)
                .addComponent(jbLoad)
                .addGap(16, 16, 16))
        );

        jTabbedPane1.addTab("Individual analysis", jpIndAnalysis);

        jbLeft.setText("Left");
        jbLeft.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbLeftActionPerformed(evt);
            }
        });

        jbRight.setText("Right");
        jbRight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbRightActionPerformed(evt);
            }
        });

        jSlider1.setValue(0);
        jSlider1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSlider1StateChanged(evt);
            }
        });

        jbPlayInd.setText("Play");
        jbPlayInd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbPlayIndActionPerformed(evt);
            }
        });

        jbStopInd.setText("Stop");
        jbStopInd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbStopIndActionPerformed(evt);
            }
        });

        jsDelay.setToolTipText("");
        jsDelay.setValue(0);
        jsDelay.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jsDelayStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPaneTable)
                    .addComponent(jScrollPaneBacktestingVisualization, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jbLeft)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbRight)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSlider1, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbPlayInd, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbStopInd, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jsDelay, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 620, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPaneBacktestingVisualization, javax.swing.GroupLayout.PREFERRED_SIZE, 446, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jbLeft)
                                        .addComponent(jbRight))
                                    .addComponent(jSlider1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jbPlayInd)
                                        .addComponent(jbStopInd))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jsDelay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(4, 4, 4))))
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 483, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPaneTable, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>                   

    private void jbLeftActionPerformed(java.awt.event.ActionEvent evt) {                                       
    	backtestRenderer.left(1);
    	this.jScrollPaneBacktestingVisualization.repaint();
    }                                      

    private void jbRightActionPerformed(java.awt.event.ActionEvent evt) {                                        
    	backtestRenderer.right(1);
    	this.jScrollPaneBacktestingVisualization.repaint();
    }     
    
    private void jSlider1StateChanged(javax.swing.event.ChangeEvent evt) {                                      
    	backtestRenderer.init(this.jSlider1.getValue());
    	this.jScrollPaneBacktestingVisualization.repaint();
    }   

    private void jsDelayStateChanged(javax.swing.event.ChangeEvent evt) {                                     
    	indSimulation.delay = this.jsDelay.getValue()*10;
    }                                    

    private void jbStopIndActionPerformed(java.awt.event.ActionEvent evt) {                                          
		this.jbPlayInd.setEnabled(true);
		this.jbStopInd.setEnabled(false);
		indStop = true;
		
    }                                         

    private void jbPlayIndActionPerformed(java.awt.event.ActionEvent evt) {                                          
        this.jbPlayInd.setEnabled(false);
        this.jbStopInd.setEnabled(true);
        indStop = false;
        runIndSimulation();
    }   

    private void jbChooseActionPerformed(java.awt.event.ActionEvent evt) {                                         
        JFileChooser chooser = new JFileChooser();
        //pending
    }                                        

    private void jbLoadActionPerformed(java.awt.event.ActionEvent evt) {  
    	JSONObject o=null;
		try {
			o = new JSONObject(new JSONTokener(new FileInputStream(this.jtfLoadFile.getText())));
		} catch (JSONException | FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
        this.indSimulation = new IndividualSimulation(o.getString("code"),o.getString("datafile"));
        backtestRenderer = new BacktestRenderer(indSimulation.backtest.getData());
        backtestRenderer.setCursor(indSimulation.cursor);
        backtestRenderer.setCode(indSimulation.code);
        backtestImg = new JLabel(new ImageIcon(backtestRenderer.init(41)));
        backtestRenderer.setOrdersManager(indSimulation.ordersManager); 
        jScrollPaneBacktestingVisualization.setViewportView(backtestImg);
        this.jbStopInd.setEnabled(false);
        this.jbPlayInd.setEnabled(true);
        indStop = true;
    }    
    private void runIndSimulation() {
		if(!indStop) {
			
				boolean b = indSimulation.backtest.runStep(indSimulation.strategy, indSimulation.cursor, indSimulation.ordersManager);
				if(!b)return;
				this.backtestRenderer.update(indSimulation.cursor);
		    	this.jScrollPaneBacktestingVisualization.repaint();
				indSimulation.cursor++;
				this.jSlider1.setValue(100*indSimulation.cursor/indSimulation.backtest.getData().size());
				try {
					TimeUnit.MILLISECONDS.sleep(indSimulation.delay);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	SwingUtilities.invokeLater(()->{
		    		
		    		
		    		runIndSimulation();
		    		
		    	});
			
		}
		
	}

    private void jbRunActionPerformed(java.awt.event.ActionEvent evt) {                                      
        this.evoStop = false;
        experiment.run(properties);
    }                                     

    private void jbStopActionPerformed(java.awt.event.ActionEvent evt) {                                       
    	this.evoStop = true;
    }   
    private void runIndSimulationFromTableSelection(int selectedRow) {
		String code = ((Individual)this.indTableModel.getValueAt(selectedRow, 0)).getPhenotype().getVisualCode();
		this.indSimulation = new IndividualSimulation(code,"NVDA");
        backtestRenderer = new BacktestRenderer(indSimulation.backtest.getData());
        backtestRenderer.setCursor(indSimulation.cursor);
        backtestRenderer.setCode(code);
        backtestRenderer.setEvaluator(indSimulation.strategy.getEvaluator());
        backtestImg = new JLabel(new ImageIcon(backtestRenderer.init(41)));
        backtestRenderer.setOrdersManager(indSimulation.ordersManager); 
        jScrollPaneBacktestingVisualization.setViewportView(backtestImg);
        this.jbStopInd.setEnabled(false);
        this.jbPlayInd.setEnabled(true);
        indStop = true;
	}
	/**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(BacktestingMainGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BacktestingMainGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BacktestingMainGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BacktestingMainGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BacktestingMainGUI().setVisible(true);
            }
        });
    }

 // Variables declaration - do not modify                     
    private javax.swing.JTable jIndividualTable;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPaneBacktestingVisualization;
    private javax.swing.JScrollPane jScrollPaneTable;
    private javax.swing.JSlider jSlider1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JButton jbChoose;
    private javax.swing.JButton jbLeft;
    private javax.swing.JButton jbLoad;
    private javax.swing.JButton jbPlayInd;
    private javax.swing.JButton jbRight;
    private javax.swing.JButton jbRun;
    private javax.swing.JButton jbStop;
    private javax.swing.JButton jbStopInd;
    private javax.swing.JPanel jpEvoConfig;
    private javax.swing.JPanel jpIndAnalysis;
    private javax.swing.JSlider jsDelay;
    private javax.swing.JTextField jtfLoadFile;
    // End of variables declaration                    
}
