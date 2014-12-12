package cosine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;

/*==========THIS PART CALCULATES THE SERIAL COSINE TIME AND THEIR SIMILARITY==============*/
public class SerialCosine {

	File mainFolder = new File("E:\\hybris self notes\\c1c1\\input files");
	static File fileoutput1 = new File("E:\\hybris self notes\\c1c1\\text output\\cosine_serial\\Output_file_cosine_serial");

	
	static long time1,duration1;
	static int i;
	static List<HashMap<String, Integer>> ls;
	static ArrayList numerator;
	static double aInterB=0;
	static double aUnionB=0;
	static int finalnumerator=0;
	static int sumofsq1=0;
	static int sumofsq2=0;
	static int basefile=0;
	static int compareto=0;
	static ArrayList<Long> Durationlistcosineserial=new ArrayList<Long>();



	long a1;

	private  HashMap<String,Integer> data1;
	private  int start;
	private  int end;
	static double[][] serialcosinesimilarity;
	;



	public SerialCosine(HashMap<String, Integer> data1, int start, int end,int basefile, int compareto) {
		this.data1 = data1;
		this.start = start;
		this.end = end;
		this.basefile=basefile;
		this.compareto=compareto;
	}



	public SerialCosine() {
		// TODO Auto-generated constructor stub
	}



	public SerialCosine(List<HashMap<String, Integer>> finallist, int basefile,
			int compareto) {
		// TODO Auto-generated constructor stub
		this(finallist.get(basefile), 0, finallist.get(basefile).size(),basefile,compareto);
	}



	public  void main(String[] args)
	{
		FileOutputStream fos1 = null;
		
		try {
			fos1 = new FileOutputStream(fileoutput1);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PrintStream ps1 = new PrintStream(fos1);
		System.setOut(ps1);


		ParallelCosineJaccard lf = new ParallelCosineJaccard();


		ls=CalcSimilarity.finallist;
		int iz=ls.get(1).size();
		
		//System.out.println(ls);
		System.out.println("done");


		{	serialcosinesimilarity=new double[ls.size()][ls.size()]; // declaration of an array. here array a

		for(basefile=0;basefile<ls.size();basefile++)
		{
			finalnumerator=0;sumofsq1=0;sumofsq2=0;
			time1 = System.currentTimeMillis() ;
			for(compareto=0;compareto<ls.size();compareto++)
			{finalnumerator=0;sumofsq1=0;sumofsq2=0;



                           //SERIAL CALCULATION OF COSINE
                        
			for ( String key : ls.get(basefile).keySet() ) 
			{
	
				if(ls.get(basefile).get(key)!=null && ls.get(compareto).get(key)!=null)
				{						

					finalnumerator=finalnumerator+ls.get(basefile).get(key)*ls.get(compareto).get(key);
					sumofsq1=sumofsq1+ls.get(basefile).get(key)*ls.get(basefile).get(key);
					sumofsq2=sumofsq2+ls.get(compareto).get(key)*ls.get(compareto).get(key);        	    


				}

			}




			System.out.println("numerator calculation done , basefile is "+CalcSimilarity.allPaths.get(basefile)+" comparedto file is "+CalcSimilarity.allPaths.get(compareto));
			System.out.println("value is :"+finalnumerator);

			System.out.println("final value of cosine is :");
			double cosine=0;
			if(sumofsq1==sumofsq2 && sumofsq1!=0 && sumofsq2!=0)
			{
				cosine=finalnumerator/(sumofsq2);
			}
			else{
				if(sumofsq1!=0 && sumofsq2!=0)
				{
					cosine=(finalnumerator/(Math.sqrt(sumofsq1)*Math.sqrt(sumofsq2)));
				}

			}
			serialcosinesimilarity[basefile][compareto]=(cosine);

			System.out.println(cosine);
			duration1 = System.currentTimeMillis() - time1;
			System.out.println("Total comparison time for cosine in serial is :"+duration1+" ms");
			Durationlistcosineserial.add(duration1);
			System.out.println("===========================================================================");



			}
		}



		}
		
		DecimalFormat df3 = new DecimalFormat("#.000000000000");
		DecimalFormat df4 = new DecimalFormat("#.############");
		System.setOut(ps1);
		double lastvalue=0;
		System.out.println("Matrix for cosine similarity");
		System.out.println("============================");
		for (int i = 0; i < serialcosinesimilarity.length; i++)
		{lastvalue=0;
		for (int j = 0; j < serialcosinesimilarity[i].length; j++) 
		{

			if(lastvalue!=1.0)
			{
				if((serialcosinesimilarity[i][j])==1.0)
				{
					System.out.print(df3.format(serialcosinesimilarity[i][j]) + "   ");
					lastvalue=serialcosinesimilarity[i][j];
				}
				else{
					System.out.print(df4.format(serialcosinesimilarity[i][j]) + "   "); // here printing an array
				}  

			}// elements.
			else{
				System.out.print("0.000000000000" + "   "); // here printing an array

			}
		}
		System.out.print("\n\n");
		}







	}
        
	







}
