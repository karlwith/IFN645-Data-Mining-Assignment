package task_3;

import java.util.Random;

import weka.classifiers.Classifier;
import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.functions.SMO;
import weka.classifiers.lazy.IBk;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.trees.HoeffdingTree;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.core.stemmers.SnowballStemmer;
import weka.core.stopwords.Rainbow;
import weka.core.tokenizers.AlphabeticTokenizer;
import weka.filters.unsupervised.attribute.StringToWordVector;

public class TextClassifierEvaluation {

	//
	//Evaluation of the performance of IBk, SMO, J48 and HoeffdingTree algorithms for
	//classification of text based documents. StringToWordVector filter is used to tokenize,
	//stem and prepare documents for classification.
	//
	
	public static void main(String[] args) throws Exception {
		//Load Dataset
		DataSource source = new DataSource("G:/My Drive/University/IFN645/major_assignment/task_3/datasets/News.arff");
		Instances data = source.getDataSet();
		data.setClassIndex(1);
		//Complete Each Question
		evaluate_documents(data);
	}

	private static void evaluate_documents(Instances data) throws Exception {
		//Create StringToWordVector Filter
		StringToWordVector swFilter = new StringToWordVector();
		//Set Filter Parameters
		swFilter.setAttributeIndices("first-last");
		swFilter.setDoNotOperateOnPerClassBasis(true);
		swFilter.setOutputWordCounts(true);
		swFilter.setStemmer(new SnowballStemmer());
		swFilter.setStopwordsHandler(new Rainbow());
		swFilter.setTokenizer(new AlphabeticTokenizer());
		swFilter.setWordsToKeep(100);
		// Create a FilteredClassifier object
		FilteredClassifier filter_classifier = new FilteredClassifier();
		// Set the filter to the filtered classifier
		filter_classifier.setFilter(swFilter);
		//Create Classification Algorithm Objects
		Classifier[] cls = {new IBk(), new SMO(), new J48(), new HoeffdingTree()};
		String[] cls_names = {"IBk", "SMO", "J48", "HoeffdingTree"};
		//Build and Iterate Evaluation for each Classifier 
		for (int i = 0; i < cls.length; i++) {
			long startSource = System.nanoTime();
			filter_classifier.setClassifier(cls[i]);
			filter_classifier.buildClassifier(data);
			Evaluation eval = new Evaluation(data);
			eval.crossValidateModel(filter_classifier, data, 10, new Random(1));
			System.out.println("======= EVALUATION OF " + cls_names[i] + " ALGORITHM FOR TEXT CLASSIFICATION =======");
			System.out.println(eval.toSummaryString());
			System.out.println(eval.toClassDetailsString());
			long endSource = System.nanoTime();
			double elapsedSource = (endSource - startSource);
			elapsedSource = elapsedSource/ 1000000000;
			System.out.println("\n=== Computation time from document filtering till completion ===");
			System.out.println(elapsedSource + " seconds\n");
		}
	}
}
