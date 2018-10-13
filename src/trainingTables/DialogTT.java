package trainingTables;

import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import com.opencsv.*;

public class DialogTT extends JFrame implements ActionListener {

  private JTextField  cEntradas, cSalidas, cPorcentaje, cFilas, cInicio, cFin, cColumna, cColumnas;
  private JLabel eFuente, eDestino, eEntradas, eSalidas, ePorcentaje, eFilas, eInicio, eFin, eColumna, eColumnas, et1, et2;
  private int Entradas, Salidas, Porcentaje, Filas, Inicio, Fin, Columna, Columnas;
  private JButton bFuente, bDestino, bAceptar;
  private Reader rFuente, rDestino;
  private CSVReader crFuente, crDestino;
  private CSVReaderBuilder rbF;
  private CSVWriter wDestino;
  private boolean flag=false; 
   
	 public DialogTT()  
		{
		setLayout(null);
        setTitle("Tabla de entrenamiento");
        //Etiquetas
        et1=new JLabel("*");
        et1.setBounds(260,10,10,15);
        et2=new JLabel("*");
        et2.setBounds(260,30,10,15);
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
        ePorcentaje=new JLabel("Porcentaje");
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
        // Campos de texto
        cEntradas=new JTextField("20");
        cEntradas.setBounds(160,50,100,15);
        add(cEntradas);
        cSalidas=new JTextField("20");
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
        cFin=new JTextField("20");
        cFin.setBounds(160,150,100,15);
        add(cFin);
        cColumna=new JTextField("20");
        cColumna.setBounds(160,170,100,15);
        add(cColumna);
        cColumnas=new JTextField("20");
        cColumnas.setBounds(160,190,100,15);
        add(cColumnas);
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
        bAceptar.setBounds(10,220,90,15);
        add(bAceptar);
        bAceptar.addActionListener(this);
		}
		
public Reader setSource(String titulo, JLabel jl)
	   {
	   Reader cr=null;
	   JFileChooser chooserCSV = new JFileChooser();
       chooserCSV.setCurrentDirectory(new java.io.File("."));
       chooserCSV.setDialogTitle(titulo);
       chooserCSV.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
       chooserCSV.setAcceptAllFileFilterUsed(false);
        //chooserImagen.showOpenDialog(null);
       int status = chooserCSV.showOpenDialog(null);
       File file = chooserCSV.getSelectedFile();                   
	   if(status == JFileChooser.APPROVE_OPTION) 
			{
		    add(jl);
		    try
				{
                cr = new FileReader(file); 
               	}
            catch(IOException c) 
				{
                System.out.println(c.getMessage());
				}
			}
	   return cr;
	   }

		@Override
    public void actionPerformed(ActionEvent e) 
		{
        rFuente=null;
		if(e.getSource()==bFuente)
        	rFuente=setSource("Cargar CSV fuente", et1);
		if(e.getSource()==bDestino) 
        	rDestino=setSource("Cargar CSV destino", et2);
        if(e.getSource()==bAceptar) 
			{
        	Entradas=Integer.parseInt(cEntradas.getText());
        	Salidas=Integer.parseInt(cSalidas.getText());
        	Porcentaje=Integer.parseInt(cPorcentaje.getText());
        	Filas=Integer.parseInt(cFilas.getText());
        	Inicio=Integer.parseInt(cInicio.getText());
        	Columna=Integer.parseInt(cColumna.getText());
        	Fin=Integer.parseInt(cFin.getText());
        	Columnas=Integer.parseInt(cColumnas.getText());
        	double[][] M1=new double[Filas][Entradas+1];
        	String[] M2=new String[Fin-Inicio];
        	String[] M3=new String[Columnas];
        	rbF=new CSVReaderBuilder(rFuente);
        	rbF.withSkipLines(Inicio);
        	crFuente=rbF.build();
        	for(int i=0; i<Fin-Inicio; i++)
        		{
        		try{
        			M3=crFuente.readNext();}
        		catch(IOException c) {
        			System.out.println(c.getMessage()); }
        		M2[i]=M3[Columna+1];
        		}
        	
        	
        	//getters and setters
			flag=true;
            System.exit(0);
            }
        }
        	
		
}
