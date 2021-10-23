package task_2;

import java.util.Random;

import weka.classifiers.Classifier;
import weka.classifiers.CostMatrix;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.meta.AttributeSelectedClassifier;
import weka.classifiers.rules.OneR;
import weka.classifiers.rules.PART;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class ClassifierEvaluation {
	
	//
	//Evaluation of performance using using PART, OneR, J48 and NaiveBayes classifiers.
	//
	
	public static void main(String[] args) throws Exception {
		//Load Dataset
		DataSource source = new DataSource("G:/My Drive/University/IFN645/major_assignment/task_2/datasets/bank.arff");
		Instances data = source.getDataSet();
		//Set the Class for Classification
		data.setClass(data.attribute("subscribed"));
		//Run Classification
		question_1(data);
		question_2(data);
	}
	
	
	//
	//Cost sensitive weighting is implemented on the confusion matrix for more effective  
	//evaluation of algorithms and the result output to console. 
	//
	private static void question_1(Instances data) throws Exception {
		//Create Classification Algorithm Objects
		Classifier[] cls = {new PART(), new OneR(), new J48(), new NaiveBayes()};
		String[] cls_names = {"PART", "OneR", "J48", "NaiveBayes"};
		//Build Classifiers
		for (int i = 0; i < cls.length; i++) {
			cls[i].buildClassifier(data);
		}	
		//Define Cost Matrix
		String matlab = "[0.0 0.0; 5.0 0.0]";
		CostMatrix matrix = CostMatrix.parseMatlab(matlab);
		//Iterate Evaluation for each Classifier
		for (int i = 0; i < cls.length; i++) {
			double total_cost = 0;
			double total_correct = 0;
			for (int j =1; j < 10; j++) {
				Evaluation eval = new Evaluation(data, matrix);
				Random r = new Random(j); 
				eval.crossValidateModel(cls[i], data, 10, r);
				total_correct = total_correct + eval.correct();
				total_cost = total_cost + eval.totalCost();
			}
			System.out.println("Evaluation of " + cls_names[i] + " considering cost weighting.");
			System.out.println("Accuracy - " + 100*(total_correct / (9 * data.numInstances())));
			System.out.println("Cost	 - " + (total_cost / 9 )+"\n");
		}
	}
	
	
	//
	//Relevant attributes only are first determined using AttributeSelectedClassifier 
	//resulting in reduced time complexity and better accuracy for J48 and NaiveBayes 
	//classifiers but marginally worse accuracy for PART.
	//
	private static void question_2(Instances data) throws Exception {
		//Create Classification Algorithm Objects
		AttributeSelectedClassifier[] asc = {new AttributeSelectedClassifier(), new AttributeSelectedClassifier(), new AttributeSelectedClassifier()};
		Classifier[] cls = {new PART(), new J48(), new NaiveBayes()};
		String[] cls_names = {"PART", "J48", "NaiveBayes"};
		//Build Classifiers
		for (int i = 0; i < asc.length; i++) {
			asc[i].setClassifier(cls[i]);
			asc[i].buildClassifier(data);
		}
		//Iterate Evaluation for each Classifier
		for (int i = 0; i < cls.length; i++) {
			double total_correct = 0;
			for (int j =1; j < 10; j++) {
				Evaluation eval = new Evaluation(data);
				Random r = new Random(j); 
				eval.crossValidateModel(asc[i], data, 10, r);
				total_correct = total_correct + eval.correct();
			}
			System.out.println("Evaluation of " + cls_names[i] + " following AttributeSelectedClassifier.");
			System.out.println("Features - " + asc[i].measureNumAttributesSelected());
			System.out.println("Accuracy - " + 100*(total_correct / (9 * data.numInstances()))+"\n");
		}
	}
}
