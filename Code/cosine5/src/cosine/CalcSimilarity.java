package cosine;

import java.awt.geom.Arc2D.Double;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;


public class CalcSimilarity implements Runnable{
	public static int fmax1=0,fmin1=0;
	public static Double similarity=null;
	HashMap<String, Integer> file = new HashMap<String, Integer>();
	public static double finaloutput1=0;
	public String filepath;
	public static ArrayList allPaths=new ArrayList();

	public static List<HashMap<String, Integer>> finallist = new ArrayList<>();

	public CalcSimilarity(String filepath) {
		// TODO Auto-generated constructor stub
		this.filepath=filepath;
		allPaths.add(filepath);
	}

	public Double doSimilarityCalculation( )
	{  
		BufferedReader br1 = null;

		try {
			br1 = new BufferedReader(new FileReader(filepath));
			
		} 
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
		}
		String line1;
		int imax1,imin1;
		try {
			fmax1=0;
			fmin1=0;
			while ((line1 = br1.readLine()) != null) {		
				String[] temp;				
				temp = line1.split("\\s+");			


				int value = Integer.parseInt(temp[1]);

				file.put(temp[0], value);


			}
			//System.out.println("Total no of tecords in "+filepath+" is "+file.size());
			System.out.println("===========================================================================");
			finallist.add(file);


		} catch (Exception e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
		}

		try {
			br1.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}




		return similarity;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub		
		doSimilarityCalculation();		

	}





}
