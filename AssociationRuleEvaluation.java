package task_1;

import ca.pfv.spmf.algorithms.frequentpatterns.fpgrowth.AlgoFPClose;
import ca.pfv.spmf.algorithms.frequentpatterns.fpgrowth.AlgoFPGrowth;
import ca.pfv.spmf.input.transaction_database_list_integers.TransactionDatabase;
import ca.pfv.spmf.tools.dataset_converter.TransactionDatabaseConverter;
import ca.pfv.spmf.tools.resultConverter.ResultConverter;
import ca.pfv.spmf.algorithms.associationrules.TopKRules_and_TNR.AlgoTopKClassRules;
import ca.pfv.spmf.algorithms.associationrules.TopKRules_and_TNR.Database;
import ca.pfv.spmf.algorithms.frequentpatterns.apriori.AlgoApriori;
import ca.pfv.spmf.algorithms.frequentpatterns.apriori_close.AlgoAprioriClose;
import ca.pfv.spmf.algorithms.frequentpatterns.charm.AlgoCharm_Bitset;
import java.io.IOException;

public class AssociationRuleEvaluation {

	//
	//Evaluation of performance using FPGrowth, Apriori, Charm, FPClose, AprioriClose 
	//and TopKClassRules association mining algorithms.
	//
	
	public static void main(String[] args) throws IOException{
		//Load Datasets
		String dataset_names[] = {"bank", "bank_no", "bank_yes"};
		String dataset_path = "G:/My Drive/University/IFN645/major_assignment/task_1/datasets/";
		//Convert Datasets to String
		TransactionDatabaseConverter converter = new TransactionDatabaseConverter();
		for (int i = 0; i < dataset_names.length; i++) {
			converter.convertARFFandReturnMap(dataset_path+dataset_names[i]+".arff", dataset_path+dataset_names[i]+"_converted.txt", 45211);
		}
		String bank_converted = dataset_path + "bank_converted.txt";
		String bank_no_converted = dataset_path + "bank_no_converted.txt";
		String bank_yes_converted = dataset_path + "bank_yes_converted.txt";
		//Complete Each Question
		question_1(bank_converted);
		question_2(bank_no_converted, bank_yes_converted);
		question_3(bank_no_converted, bank_yes_converted);
		question_4(bank_converted);
		question_5(bank_converted);
	}
	
	
	//
	//Generation of frequent patterns using FPGrowth and Apriori algorithms, performance evaluated
	//by time efficiency considering supports of 0.05, 0.1 and 0.2.
	//
	private static void question_1(String bank_dataset_converted) throws IOException {
		//Create Objects of 2 Different Frequent Pattern Mining Algorithms
		AlgoFPGrowth algo_FPGrowth = new AlgoFPGrowth();
		AlgoApriori algo_Apri = new AlgoApriori();
		//Load Locations of Output Files
		String FPGrowth_output = "G:/My Drive/University/IFN645/major_assignment/task_1/outputs/FPGrowth_q1.txt";
		String Apri_output = "G:/My Drive/University/IFN645/major_assignment/task_1/outputs/Apri_q1.txt";
		//Define Minimum Supports and Pattern Size
		Double supports[] = {0.05, 0.1, 0.2};
		algo_FPGrowth.setMinimumPatternLength(2);
		//Run Algorithms with 3 Different Minimum Supports for Comparison
		for (int i = 0; i < supports.length; i++) {
			System.out.println("\nMODEL STATS FOR A MINIMUM SUPPORT OF "+supports[i].toString()+":\n");
			algo_FPGrowth.runAlgorithm(bank_dataset_converted, FPGrowth_output+supports[i].toString()+"minsupp_q1.txt", supports[i]);
			algo_FPGrowth.printStats();
			algo_Apri.runAlgorithm(supports[i], bank_dataset_converted, Apri_output+supports[i].toString()+"minsupp_q1.txt");
			algo_Apri.printStats();
        }
	}
	
	
	//
	//Most frequent patterns of size 3 are determined using FPGrowth for 2 divisions of the data set and compared.
	//
	private static void question_2(String bank_no_converted, String bank_yes_converted) throws IOException {
		//Create Objects of a Frequent Pattern Mining Algorithm
		AlgoFPGrowth algo_FPGrowth_no = new AlgoFPGrowth();
		AlgoFPGrowth algo_FPGrowth_yes = new AlgoFPGrowth();
		algo_FPGrowth_no.setMinimumPatternLength(3);
		algo_FPGrowth_no.setMaximumPatternLength(3);
		algo_FPGrowth_yes.setMinimumPatternLength(3);
		algo_FPGrowth_yes.setMaximumPatternLength(3);
		//Mining of bank_no.arff
		String bank_no_output = "G:/My Drive/University/IFN645/major_assignment/task_1/outputs/bank_no_FPGrowth_q2.txt";
		algo_FPGrowth_no.runAlgorithm(bank_no_converted, bank_no_output, 0.1);
		//Mining of bank_yes.arff
		String bank_yes_output = "G:/My Drive/University/IFN645/major_assignment/task_1/outputs/bank_yes_FPGrowth_q2.txt";
		algo_FPGrowth_yes.runAlgorithm(bank_yes_converted, bank_yes_output, 0.1);
	}
	
	
	//
	//Most frequent patterns of any size are determined using FPGrowth for 2 divisions of the data set 
	//
	private static void question_3(String bank_no_converted, String bank_yes_converted) throws IOException {
		//Create Objects of a Frequent Pattern Mining Algorithm
		AlgoFPGrowth algo_FPGrowth_no = new AlgoFPGrowth();
		AlgoFPGrowth algo_FPGrowth_yes = new AlgoFPGrowth();
		algo_FPGrowth_no.setMinimumPatternLength(6);
		algo_FPGrowth_yes.setMinimumPatternLength(6);
		//Mining of bank_no.arff
		String bank_no_output = "G:/My Drive/University/IFN645/major_assignment/task_1/outputs/bank_no_FPGrowth_q3.txt";
		algo_FPGrowth_no.runAlgorithm(bank_no_converted, bank_no_output, 0.1);
		//Mining of bank_yes.arff
		String bank_yes_output = "G:/My Drive/University/IFN645/major_assignment/task_1/outputs/bank_yes_FPGrowth_q3.txt";
		algo_FPGrowth_yes.runAlgorithm(bank_yes_converted, bank_yes_output, 0.1);
	}
	
	
	//
	//Most frequent closed patterns are determined from the entire dataset using Charm, FPClose and AprioriClose and
	//evaluated by time efficiency. 
	//
	private static void question_4(String bank_converted) throws IOException {
		//Create Objects of 3 Different Frequent Closed Pattern Mining Algorithms
		AlgoCharm_Bitset algo_charm = new AlgoCharm_Bitset();
		AlgoFPClose algo_fpclose = new AlgoFPClose();
		AlgoAprioriClose algo_apclose = new AlgoAprioriClose();
		//Create Transaction Database for algo_charm
		TransactionDatabase bank_tdb = new TransactionDatabase();
		bank_tdb.loadFile(bank_converted);
		//Mining of bank.arff
		String output_path = "G:/My Drive/University/IFN645/major_assignment/task_1/outputs/";
		String bank_charm_output = output_path+"bank_charm_q4.txt";
		String bank_fpclose_output = output_path+"bank_fpclose_q4.txt";
		String bank_apclose_output = output_path+"bank_apclose_q4.txt";
		algo_charm.runAlgorithm(bank_charm_output, bank_tdb, 0.1, false, 10000);
		algo_charm.printStats();
		algo_fpclose.runAlgorithm(bank_converted, bank_fpclose_output, 0.1);
		algo_fpclose.printStats();
		algo_apclose.runAlgorithm(0.1, bank_converted, bank_apclose_output);
		algo_apclose.printStats();
	}
	
	
	//
	//Top 10 most frequent association using subscribed=yes and subscribed=no as the consequent are 
	//determined using TopKClassRules. Results are output to a text file. 
	//
	private static void question_5(String bank_converted) throws IOException {
		//Create Object of Consequent Considering Mining Algorithms
		AlgoTopKClassRules topK_classRules_no = new AlgoTopKClassRules();
		AlgoTopKClassRules topK_classRules_yes = new AlgoTopKClassRules();
		//Define Items to be Used as Consequent
		int[] subscribed_no_item = new int[] {11};
		int[] subscribed_yes_item = new int[] {42};
		//Create Database from Dataset
		Database bank_db = new Database();
				bank_db.loadFile(bank_converted);
		//Mining of bank.arff
		String output_path = "G:/My Drive/University/IFN645/major_assignment/task_1/outputs/";
		String subscribed_no_output = output_path+"subscribed_no_q5.txt";
		String subscribed_no_output_final = output_path+"subscribed_no_final_q5.txt";
		String subscribed_yes_output = output_path+"subscribed_yes_q5.txt";
		String subscribed_yes_output_final = output_path+"subscribed_yes_final_q5.txt";
		topK_classRules_no.runAlgorithm(10, 0.3, bank_db, subscribed_no_item);
		topK_classRules_no.writeResultTofile(subscribed_no_output);
		topK_classRules_yes.runAlgorithm(10, 0.3, bank_db, subscribed_yes_item);
		topK_classRules_yes.writeResultTofile(subscribed_yes_output);
		//Re-format Rules for Easier Interpretation
		ResultConverter output_converter = new ResultConverter();
		output_converter.convert(bank_converted, subscribed_no_output, subscribed_no_output_final, null);
		output_converter.convert(bank_converted, subscribed_yes_output, subscribed_yes_output_final, null);
	}
}
