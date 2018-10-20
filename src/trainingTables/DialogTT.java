package trainingTables;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.event.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Random;

import com.opencsv.*;

public class DialogTT extends JFrame implements ActionListener, ChangeListener {

  private JTextField  cSemilla, cEntradas, cSalidas, cPorcentaje, cFilas, cInicio, cFin, cColumna, cColumnas;
  private JLabel eSemilla, eFuente, eDestino, eEntradas, eSalidas, ePorcentaje, eFilas, eInicio, eFin, eColumna, eColumnas, et1, et2;
  private int Semilla, Entradas, Salidas, Filas, Inicio, Fin, Columna, Columnas;
  private double  Porcentaje;
  private JButton bFuente, bDestino, bAceptar;
  private JCheckBox cbLoga, cbNormT, cbNormI, cbPvc;
  private boolean Loga, NormT, NormI, Pvc;
  private File fF, fD;
  private Reader rF;
  private Writer wD;
  private CSVReader crF;
  private CSVReaderBuilder rbF;
  private CSVWriter cwD;
  private Random ran;
  private boolean flag=false; 
   
public DialogTT()  
		{
		Loga=NormT=NormI=Pvc=false;
		setLayout(null);
        setTitle("Tabla de entrenamiento");
        //Etiquetas
        et1=new JLabel("");
        et1.setBounds(260,10,10,15);
        add(et1);
        et2=new JLabel("");
        et2.setBounds(260,30,10,15);
        add(et2);
        eFuente=new JLabel("CSV fuente: ");
        eFuente.setBounds(10,10,100,15);
        add(eFuente);
        eDestino=new JLabel("CSV destino: ");
        eDestino.setBounds(10,30,100,15);
        add(eDestino);
        eEntradas=new JLabel("Cantidad de entradas: ");
        eEntradas.setBounds(10,50,140,15);
        add(eEntradas);
        eSalidas=new JLabel("Horizonte: ");
        eSalidas.setBounds(10,70,100,15);
        add(eSalidas);
        ePorcentaje=new JLabel("Porcentaje: ");
        ePorcentaje.setBounds(10,90,100,15);
        add(ePorcentaje);
        eFilas=new JLabel("Iteraciones: ");
        eFilas.setBounds(10,110,100,15);
        add(eFilas);
        eInicio=new JLabel("Fila de inicio: ");
        eInicio.setBounds(10,130,100,15);
        add(eInicio);
        eFin=new JLabel("Fila de Finalización: ");
        eFin.setBounds(10,150,140,15);
        add(eFin);
        eColumna=new JLabel("Usar columna n°: ");
        eColumna.setBounds(10,170,100,15);
        add(eColumna);
        eColumnas=new JLabel("Número de columnas: ");
        eColumnas.setBounds(10,190,140,15);
        add(eColumnas);
        eSemilla=new JLabel("Semilla: ");
        eSemilla.setBounds(10,210,140,15);
        add(eSemilla);
        // Campos de texto
        cEntradas=new JTextField("10");
        cEntradas.setBounds(160,50,100,15);
        add(cEntradas);
        cSalidas=new JTextField("5");
        cSalidas.setBounds(160,70,100,15);
        add(cSalidas);
        cPorcentaje=new JTextField("20");
        cPorcentaje.setBounds(160,90,100,15);
        add(cPorcentaje);
        cFilas=new JTextField("20");
        cFilas.setBounds(160,110,100,15);
        add(cFilas);
        cInicio=new JTextField("20");
        cInicio.setBounds(160,130,100,15);
        add(cInicio);
        cFin=new JTextField("200");
        cFin.setBounds(160,150,100,15);
        add(cFin);
        cColumna=new JTextField("3");
        cColumna.setBounds(160,170,100,15);
        add(cColumna);
        cColumnas=new JTextField("5");
        cColumnas.setBounds(160,190,100,15);
        add(cColumnas);
        cSemilla=new JTextField("20");
        cSemilla.setBounds(160,210,100,15);
        add(cSemilla);
        //CheckBoxes
        cbLoga=new JCheckBox("Logaritmizar");
        cbLoga.setBounds(10, 240, 200, 15);
        cbLoga.addChangeListener(this); add(cbLoga);
        cbNormT=new JCheckBox("Normalizar todo");
        cbNormT.setBounds(10, 260, 200, 15);
        cbNormT.addChangeListener(this); add(cbNormT);
        cbNormI=new JCheckBox("Normalizar cada entrada");
        cbNormI.setBounds(10, 280, 200, 15);
        cbNormI.addChangeListener(this); add(cbNormI);
        cbPvc=new JCheckBox("Porcentaje constante");
        cbPvc.setBounds(10, 300, 200, 15);
        cbPvc.addChangeListener(this); add(cbPvc);
        // Botones
        bFuente=new JButton("Buscar");
        bFuente.setBounds(160,10,90,15);
        add(bFuente);
        bFuente.addActionListener(this); 
        bDestino=new JButton("Buscar");
        bDestino.setBounds(160,30,90,15);
        add(bDestino);
        bDestino.addActionListener(this);
        bAceptar=new JButton("Aceptar");
        bAceptar.setBounds(10,330,90,15);
        add(bAceptar);
        bAceptar.addActionListener(this);
		}

public double[] norm(double[] ent)
		{
		double max=ent[0];
		double min=ent[0];
		double[] ret=new double[ent.length];
		for(int i=1; i<ent.length; i++)
			{
			if(ent[i]>max)
				max=ent[i];
			}
		for(int j=1; j<ent.length; j++)
			{
			if(ent[j]<min)
				min=ent[j];
			}
		for(int k=0; k<ent.length; k++)
			ret[k]=(ent[k]-min)/(max-min);
		return ret;
		}

public File setArchivo(String titulo, JLabel jl)
	   {
	   JFileChooser chooserCSV = new JFileChooser();
       chooserCSV.setCurrentDirectory(new java.io.File("."));
       chooserCSV.setDialogTitle(titulo);
       chooserCSV.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
       chooserCSV.setAcceptAllFileFilterUsed(false);
        //chooserImagen.showOpenDialog(null);
       int status = chooserCSV.showOpenDialog(null);
       File file = chooserCSV.getSelectedFile();                   
	   if(status == JFileChooser.APPROVE_OPTION) 
			jl.setText("*");
	   return file;
	   }

@Override
public void stateChanged(ChangeEvent ce) 
	  {
	  Loga=cbLoga.isSelected();
	  NormT=cbNormT.isSelected(); 
	  NormI=cbNormI.isSelected();
	  Pvc=cbPvc.isSelected();
	  }

@Override
public void actionPerformed(ActionEvent e) 
		{
        if(e.getSource()==bFuente)
			{
			fF=setArchivo("Cargar CSV fuente", et1);
			try {
	            rF = new FileReader(fF); }
	        catch(IOException c) {
	            System.out.println(c.getMessage()); }
			}
		if(e.getSource()==bDestino) 
			{
			fD=setArchivo("Cargar CSV destino", et2);
			try {
	            wD = new FileWriter(fD); }
	        catch(IOException c) {
	            System.out.println(c.getMessage()); }
			}
        if(e.getSource()==bAceptar) 
			{
        	Entradas=Integer.parseInt(cEntradas.getText());
        	Salidas=Integer.parseInt(cSalidas.getText());
        	Porcentaje=Double.parseDouble(cPorcentaje.getText());
        	Filas=Integer.parseInt(cFilas.getText());
        	Inicio=Integer.parseInt(cInicio.getText());
        	Columna=Integer.parseInt(cColumna.getText());
        	Fin=Integer.parseInt(cFin.getText());
        	Columnas=Integer.parseInt(cColumnas.getText());
        	Semilla=Integer.parseInt(cSemilla.getText());
        	String[] M1=new String[Entradas+1];
        	String[] Mi=new String[Columnas];
        	double[] MD=new double[Fin-Inicio+1];
        	double[] MD2=new double[Entradas+Salidas];
        	rbF=new CSVReaderBuilder(rF);
        	rbF.withSkipLines(Inicio-1);
        	crF=rbF.build();
        	ran=new Random(Semilla);
           	int in=0;
        	for(int j=0; j<Fin-Inicio+1; j++)
        		{
        		try{
        			Mi=crF.readNext();}
        		catch(IOException c) {
        			System.out.println(c.getMessage()); }
        		MD[j]=Double.parseDouble(Mi[Columna-1]);
        		}
        	try{
    			crF.close();}
    		catch(IOException c) {
    			System.out.println(c.getMessage()); }
        	if(Loga==true)
        		{
        		for(int i=0; i<Fin-Inicio; i++)
        			MD[i]=Math.log(MD[i]);
        	   	}
        	if(NormT==true)
        		MD=norm(MD);
        	double sal, comp;
        	cwD=new CSVWriter(wD);
        	for(int j=0; j<Filas; j++)
        		{
        		in=ran.nextInt((Fin-Inicio)-(Entradas+Salidas));
        		for(int k=0; k<Entradas+Salidas; k++)
        			{
        			MD2[k]=MD[in];
        			in++;
        			}
        		if(NormI==true)
            		MD2=norm(MD2);
        		sal=0.0;
        		if(Pvc==true)
        			comp=MD2[Entradas-1]+(Porcentaje/100);
        		else
        			comp=MD2[Entradas-1]*(1+Porcentaje/100);
        		for(int l=Entradas; l<Entradas+Salidas; l++)
        			{
        			if(MD2[l]>comp)
        				sal=1.0;
        			}
        		for(int k=0; k<Entradas; k++)
        			M1[k]=Double.toString(MD2[k]);
        		M1[Entradas]=Double.toString(sal);
        		try {
        			cwD.writeNext(M1); }
	    		catch(Exception ee) {
	    			System.out.println("...esto no camina"); }
        		}
        	try{
        		cwD.close();}
		    catch(Exception ee) {
		    	System.out.println("...no funka"); }
        	flag=true;
        	System.exit(0);
            }
        }
}
