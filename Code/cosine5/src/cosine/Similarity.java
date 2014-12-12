package cosine;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Similarity {


	public static void main(String args[]) {

		ParallelCosineJaccard parallelProcess=new ParallelCosineJaccard();
		parallelProcess.main(args);

		SerialCosine serialProcess=new SerialCosine();		
		serialProcess.main(args);
		ArrayList<String> allfiles=new ArrayList<String>(ParallelCosineJaccard.allfiles);



		double[][] cosinesimilarity=new double[allfiles.size()][allfiles.size()]; 
		double[][] jaccardsimilarity=new double[allfiles.size()][allfiles.size()];
		ArrayList<Long> durationcosineparallel=new ArrayList<Long>();
		ArrayList<Long> durationcosineserial=new ArrayList<Long>();


		cosinesimilarity=ParallelCosineJaccard.cosinesimilarity;
		jaccardsimilarity=ParallelCosineJaccard.jaccardsimilarity;
		durationcosineparallel=ParallelCosineJaccard.Durationlistcosineparallel;
		durationcosineserial=SerialCosine.Durationlistcosineserial;


                //THIS PART IS USED FOR CALCULATIONG THE TIME TAKEN FOR COMPARISON

		FileOutputStream fcpds = null;

		try {
			fcpds = new FileOutputStream(ParallelCosineJaccard.filecosineparalleldistancediagram);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PrintStream fcpdsps = new PrintStream(fcpds);
		System.setOut(fcpdsps);

		for (String file :allfiles ) {   
			System.out.print(";"+ file);
		}

		DecimalFormat df = new DecimalFormat("#.000000000000");
		DecimalFormat df2 = new DecimalFormat("#.############");

		double last=0;
		System.out.print("\n");
		for (int i = 0; i < cosinesimilarity.length; i++)
		{last=0;
		System.out.print(allfiles.get(i));
		for (int j = 0; j < cosinesimilarity[i].length; j++) 
		{

			if(last!=1.0)
			{
				if((cosinesimilarity[i][j])==1.0)
				{
					System.out.print(";"+df.format(cosinesimilarity[i][j]) );
					last=cosinesimilarity[i][j];
				}
				else{
					System.out.print(";"+df2.format(cosinesimilarity[i][j])); // here printing an array
				}  

			}// elements.
			else{
				System.out.print(";"+"" ); // here printing an array

			}
		}
		System.out.print("\n");


		}

		//====================================================================================================================================

		FileOutputStream fjpds = null;

		try {
			fjpds = new FileOutputStream(ParallelCosineJaccard.filejaccardparalledistancediagram);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PrintStream fjpdsps = new PrintStream(fjpds);
		System.setOut(fjpdsps);

		for (String file :allfiles ) {   
			System.out.print(";"+ file);
		}



		double lastj=0;
		System.out.print("\n");
		for (int i = 0; i < jaccardsimilarity.length; i++)
		{lastj=0;
		System.out.print(allfiles.get(i));
		for (int j = 0; j < jaccardsimilarity[i].length; j++) 
		{

			if(lastj!=1.0)
			{
				if((jaccardsimilarity[i][j])==1.0)
				{
					System.out.print(";"+df.format(jaccardsimilarity[i][j]) );
					lastj=jaccardsimilarity[i][j];
				}
				else{
					System.out.print(";"+df2.format(jaccardsimilarity[i][j])); // here printing an array
				}  

			}// elements.
			else{
				System.out.print(";"+"" ); // here printing an array

			}
		}
		System.out.print("\n");


		}

		//====================================================================================================================================
		FileOutputStream fcptds = null;

		try {
			fcptds = new FileOutputStream(ParallelCosineJaccard.filecosineparalleltimediagram);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PrintStream fcptdsps = new PrintStream(fcptds);
		System.setOut(fcptdsps);

		for (String file :allfiles ) {   
			System.out.print(";"+ file);
		}



		double lastcp=0;
		System.out.print("\n");
		for (int i = 0; i < allfiles.size(); i++)
		{lastcp=0;
		System.out.print(allfiles.get(i));
		for (int j = 0; j < allfiles.size(); j++) 
		{

			System.out.print(";"+durationcosineparallel.get(i*j+j));
		}
		System.out.print("\n");


		}
		//==============================================================================================================================

		FileOutputStream fcstds = null;

		try {
			fcstds = new FileOutputStream(ParallelCosineJaccard.filecosineserialtimediagram);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PrintStream fcstdsps = new PrintStream(fcstds);
		System.setOut(fcstdsps);

		for (String file :allfiles ) {   
			System.out.print(";"+ file);
		}



		double lastcs=0;
		System.out.print("\n");
		for (int i = 0; i < allfiles.size(); i++)
		{lastcp=0;
		System.out.print(allfiles.get(i));
		for (int j = 0; j < allfiles.size(); j++) 
		{

			System.out.print(";"+durationcosineserial.get(i*j+j));
		}
		System.out.print("\n");
		

		}
		//==============================================================================================================================
		




	}
}
