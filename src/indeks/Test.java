package indeks;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import javax.swing.JFileChooser;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class Test {
	
	public static void main(String[] args) throws IOException{

		JFileChooser chooser = new JFileChooser();
	      chooser.setCurrentDirectory(new File("."));
	      int r = chooser.showOpenDialog(chooser);
	      if (r == JFileChooser.APPROVE_OPTION) {
	    	  File fajl = chooser.getSelectedFile();
	    	  String naslov = fajl.getAbsolutePath();
	    	  
	    	  char[] tabela = naslov.toCharArray();
	    	  int c = tabela.length - 1;
	  while (tabela[c] != '/' && tabela[c] != '\\') {
	    		  tabela[c] = '\0';
	    		  c--;
	    	  }
	    	  naslov = new String(tabela);
	    	  naslov = naslov.trim() + "Ustvarjeni_indeks";

	    	  Map<String, Vector<Integer>> slovar = new TreeMap<String, Vector<Integer>>();

		  		PDDocument dokument = PDDocument.load(fajl);
		  		PDFTextStripper pdfStripper = new PDFTextStripper();
		  		
		  		BufferedWriter writer = null;

		  		File fajl1 = new File(naslov);

		  		writer = new BufferedWriter(new FileWriter(fajl1));
		  		
		  		String tekst = pdfStripper.getText(dokument);
		  		String[] sez = tekst.split("\\r?\\n");
		  		
		  		int stevec = 1;
		  		int d;
		  		int l = sez.length;
		  		String[] vrstica;
		  		String beseda;
		  		
		  		for (int i = 0; i < l; i++) {
		  			vrstica = sez[i].trim().split("\\W+");
		  			d = vrstica.length;
		  			if (d == 1 && vrstica[0].matches("\\d+")) {
		  				stevec += 1;
		  			}
		  			for (int j = 0; j < d; j++) {
		  				beseda = vrstica[j].toLowerCase();
		  				if (beseda.length() > 3) {
		  					if (beseda.substring(beseda.length() - 1) == "s"){
		  						beseda = beseda.substring(0, beseda.length() - 1);
		  					}
		  					if (slovar.containsKey(beseda) && slovar.get(beseda).lastElement() != stevec) {
		  						slovar.get(beseda).add(stevec);
		  					} else {
		  						Vector<Integer> mnoz = new Vector<Integer>();
		  						mnoz.add(stevec);
		  						slovar.put(beseda, mnoz);
		  					}
		  				}
		  			}
	
		  		}
		  		for (String i : slovar.keySet()) {
		  			writer.write(i + ": ");
		  			for (int j : slovar.get(i)) {
		  				writer.write(j + " ");
		  			}
		  			writer.write("\n");
		  		}
		  		
		  		dokument.close();
		  		writer.close();
		    	  
			  } else {
				  System.exit(0);
			  }	
	
	}
}
