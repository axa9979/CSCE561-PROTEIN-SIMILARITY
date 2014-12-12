package cosine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;


public class ParallelCosineJaccard extends RecursiveTask<Integer>{
    
    //GIVEN DECLARATION BELOW GIVES WHERE THE OUTPUT GETS PRINTED

	File mainFolder = new File("E:\\hybris self notes\\c1c1\\input files");
	static File fileoutput = new File("E:\\hybris self notes\\c1c1\\text output\\cosine_parallel\\Output_file_cosine_parallel");
	
	static File fileoutput2 = new File("E:\\hybris self notes\\c1c1\\text output\\jaccard_parallel\\Output_file_jaccard_parallel");
	static File fileoutput3 = new File("E:\\hybris self notes\\c1c1\\text output\\cosine_serial\\Output_file_cosine_serial");
	static File filecosineparalleldistancediagram = new File("E:\\hybris self notes\\c1c1\\diagramfile\\cosine_paralle_distance_diagram_file.csv");
	static File filejaccardparalledistancediagram = new File("E:\\hybris self notes\\c1c1\\diagramfile\\jaccard_parallel_distance_diagram_file.csv");
	static File filecosineparalleltimediagram = new File("E:\\hybris self notes\\c1c1\\diagramfile\\cosine_parallel_time_diagram_file.csv");
	static File filecosineserialtimediagram = new File("E:\\hybris self notes\\c1c1\\diagramfile\\cosine_serial_time_diagram_file.csv");


	static long time1,duration1;
	static ArrayList<Long> Durationlistcosineparallel=new ArrayList<Long>();

	static int i;
	static List<HashMap<String, Integer>> ls;
	static ArrayList Duration;//processing time
	static double aInterB=0;//a intersection b
	static double aUnionB=0;//a union b
	static HashSet allfiles=new HashSet();
	static int finalnumerator=0;//numerator for cosine
	static int sumofsq1=0;//denominator for sum of squares 
	static int sumofsq2=0;//denominator for sum of squares of other file
	private static final int SEQUENTIAL_THRESHOLD = 110000; //Decides how many set of keys to be taken for parallel processing at a time
	static int basefile=0;
	static int compareto=0;
	long a1;

	private  HashMap<String,Integer> data1;
	private  int start;
	private  int end;
	static double[][] cosinesimilarity ;//lower triangle matrix for cosine
	static double[][] jaccardsimilarity ;//lower triangle matrix for jaccard


	public ParallelCosineJaccard(HashMap<String, Integer> data1, int start, int end,int basefile, int compareto) {
		this.data1 = data1;
		this.start = start;
		this.end = end;
		this.basefile=basefile;
		this.compareto=compareto;
	}



	public ParallelCosineJaccard() {
		// TODO Auto-generated constructor stub
	}


        //Passes all the 
	public ParallelCosineJaccard(List<HashMap<String, Integer>> finallist, int basefile,
			int compareto) {
		// TODO Auto-generated constructor stub
		this(finallist.get(basefile), 0, finallist.get(basefile).size(),basefile,compareto);
	}



	@Override
	protected Integer compute() {

		final int length = end - start;
		if (length < SEQUENTIAL_THRESHOLD) {
			return computeDirectly();
		}
                //splitting the dataset i.e key values
		final int split = length / 2;
		final ParallelCosineJaccard left = new ParallelCosineJaccard(data1, start, start + split,basefile,compareto);
		left.fork();
		final ParallelCosineJaccard right = new ParallelCosineJaccard(data1, start + split, end,basefile,compareto);
		left.join();
		right.compute();


		return null;

	}

	private Integer computeDirectly() {


		int pointer=0;
                //calculation on the cosine similarity
		for ( String key : data1.keySet() ) 
		{
			if((pointer >=start && pointer<end)||(end==ls.get(basefile).size() && pointer==ls.get(basefile).size()))
			{
				
				if(ls.get(basefile).get(key)!=null && ls.get(compareto).get(key)!=null)
				{

					finalnumerator=finalnumerator+ls.get(basefile).get(key)*ls.get(compareto).get(key);
					sumofsq1=sumofsq1+ls.get(basefile).get(key)*ls.get(basefile).get(key);
					sumofsq2=sumofsq2+ls.get(compareto).get(key)*ls.get(compareto).get(key); 

                                        //calculation of jaccard
					int tempintersection=Math.min(ls.get(basefile).get(key), ls.get(compareto).get(key));

					aInterB=aInterB+ tempintersection;					
					aUnionB=aUnionB+(ls.get(basefile).get(key)+ ls.get(compareto).get(key)-tempintersection);
				} 
				else{
					if(ls.get(basefile).get(key)!=null && ls.get(compareto).get(key)==null)
					{

						aUnionB=aUnionB+(ls.get(basefile).get(key));
					}
					else{
						if(ls.get(basefile).get(key)==null && ls.get(compareto).get(key)!=null)
						{

							aUnionB=aUnionB+ls.get(compareto).get(key);

						}
					}

				}

			}
			pointer++;


		}

		//comparison logic

		return null;
	}



	public  void main(String[] args)
	{

		PrintStream console = System.out;


		FileOutputStream fos = null;
		
		FileOutputStream fos2 = null;
		FileOutputStream fos3 = null;
		try {
			fos = new FileOutputStream(fileoutput);
			fos2 = new FileOutputStream(fileoutput2);
			fos3 = new FileOutputStream(fileoutput3);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PrintStream ps = new PrintStream(fos);		
		PrintStream ps2 = new PrintStream(fos2);
		PrintStream ps3 = new PrintStream(fos3);


		System.setOut(ps);
		// System.out.println("initial time "+t);


		ParallelCosineJaccard lf = new ParallelCosineJaccard();

		lf.getFiles(lf.mainFolder);
		/*================================================
                Parallel processing starts here
                 * fork/join implimentation starts
		 * ===============================================
		 */



		ls=CalcSimilarity.finallist;
		cosinesimilarity = new double[ls.size()][ls.size()]; // declaration of an array. here array a
		jaccardsimilarity = new double[ls.size()][ls.size()];

		for(basefile=0;basefile<ls.size();basefile++)
		{
			finalnumerator=0;sumofsq1=0;sumofsq2=0;aInterB=0;aUnionB=0;
			for(compareto=0;compareto<ls.size();compareto++)
			{
				finalnumerator=0;sumofsq1=0;sumofsq2=0;aInterB=0;aUnionB=0;

				final  int NTHREADS = Runtime.getRuntime().availableProcessors();

				final ForkJoinPool pool = new ForkJoinPool(NTHREADS);
				final ParallelCosineJaccard finder = new ParallelCosineJaccard(CalcSimilarity.finallist,basefile,compareto);
				time1 = System.currentTimeMillis() ;

				pool.invoke(finder);

				duration1 = System.currentTimeMillis() - time1;
				System.setOut(ps);
				System.out.println("===========================================================================");
				System.out.println("Total comparison time using Parallel approach is :"+duration1+" ms");
				Durationlistcosineparallel.add(duration1);

				System.out.println("numerator calculation done , basefile is "+CalcSimilarity.allPaths.get(basefile)+" comparedto file is "+CalcSimilarity.allPaths.get(compareto));
				System.out.println("numerator value is :"+finalnumerator);

				System.out.println("final value of similarity using Cosine approach is :");
				double cosine=0;
				double jc=0;
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

				System.out.println(cosine);
				cosinesimilarity[basefile][compareto]=(cosine);

				System.out.println("final value of distance using Cosine approach is :");
				System.out.println(""+(double)(1-cosine));


				jc=aInterB/aUnionB;
				System.setOut(ps2);
				System.out.println("Total comparison time using Parallel approach is :"+duration1+" ms");
				System.out.println("numerator calculation done , basefile is "+CalcSimilarity.allPaths.get(basefile)+" comparedto file is "+CalcSimilarity.allPaths.get(compareto));
				
				System.out.println("final value of similarity using jaccard approach is :");
				System.out.println(jc);
				jaccardsimilarity[basefile][compareto]=jc; 
				
				System.out.println("final value of distance using jaccard approach is :");
				System.out.println(""+(double)(1-jc));



				System.out.println("===========================================================================");


			}
		}







		DecimalFormat df = new DecimalFormat("#.000000000000");
		DecimalFormat df2 = new DecimalFormat("#.############");
		System.setOut(ps);
		double last=0;
		System.out.println("Matrix for cosine similarity");
		System.out.println("============================");
		for (int i = 0; i < cosinesimilarity.length; i++)
		{last=0;
		for (int j = 0; j < cosinesimilarity[i].length; j++) 
		{

			if(last!=1.0)
			{
				if((cosinesimilarity[i][j])==1.0)
				{
					System.out.print(df.format(cosinesimilarity[i][j]) + "   ");
					last=cosinesimilarity[i][j];
				}
				else{
					System.out.print(df2.format(cosinesimilarity[i][j]) + "   "); // here printing an array
				}  

			}// elements.
			else{
				System.out.print("0.000000000000" + "   "); // here printing an array

			}
		}
		System.out.print("\n\n");
		}


		DecimalFormat df3 = new DecimalFormat("#.000000000000");//decimal format specifies how many decimals to be printed
		DecimalFormat df4 = new DecimalFormat("#.############");
		System.setOut(ps2);
		double lastvalue=0;
		System.out.println("Matrix for jaccard similarity");
		System.out.println("============================");
                //======Printing Jaccard similarity matrix=========
		for (int i = 0; i < jaccardsimilarity.length; i++)
		{lastvalue=0;
		for (int j = 0; j < jaccardsimilarity[i].length; j++) 
		{

			if(lastvalue!=1.0)
			{
				if((jaccardsimilarity[i][j])==1.0)
				{
					System.out.print(df3.format(jaccardsimilarity[i][j]) + "   ");
					lastvalue=jaccardsimilarity[i][j];
				}
				else{
					System.out.print(df4.format(jaccardsimilarity[i][j]) + "   "); // here printing an array
				}  

			}// elements.
			else{
				System.out.print("0.000000000000" + "   "); // here printing an array

			}
		}
		System.out.print("\n\n");
		}



	}
	public void getFiles(File f){
		File files[];

		long intime = System.currentTimeMillis();
		if(f.isFile())
		{		allfiles.add(f.getName());

		System.out.println(f.getAbsolutePath());
		CalcSimilarity cs=new CalcSimilarity(f.getAbsolutePath());
                
		Thread thread=new Thread(cs,f.getAbsolutePath());
                //Starting a thread for parallel processing
		thread.start();
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// cs.doSimilarityCalculation();
		}
		else{
			files = f.listFiles();

			for (i = 0; i < files.length; i++) {
				getFiles(files[i]); 



			}
			//System.out.println("final time "+System.currentTimeMillis());
			a1 = System.currentTimeMillis() - intime;
			System.out.println("===========================================================================");

			System.out.println("Total file processing time is "+a1+" ms"); 
			System.out.println("===========================================================================");

			//System.out.println(CalcSimilarity.myMap);

		}


	}







}