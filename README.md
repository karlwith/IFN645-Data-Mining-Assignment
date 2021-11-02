# QUT IFN645 Data Mining Assignment
Outline of a number of different approaches to evaluation of data mining techniques, completed as part of an individual assignment in IFN645 - Large Scale Data Mining at QUT. Mining algorithms are referenced from Weka library version 3.5.8.

### Association Rule Evaluation
Implementation and evaluation of FPGrowth, Apriori, Charm, FPClose, AprioriClose and TopKClassRules association mining algorithms, applied to the novel bank.arff dataset consisting of customer attributes such as 'education', 'age' and 'job' status using the nominal class variable 'subscribed'. The class variable is considered in evaluation of a recent marketing campaign. Dataset available here - https://github.com/lpfgarcia/ucipp/blob/master/uci/bank-marketing.arff.

### Classifier Evaluation
Implementation and evaluation of PART, OneR, J48 and NaiveBayes classifiers on the bank.arff dataset. Consideration of cost sensitive weighting on the confusion matrix is made and the performance of attribute selection using AttributeSelectedClassifier is determined.

### Text Classifier Evaluation
Implementation and evaluation of IBk, SMO, J48 and HoeffdingTree algorithms for classification of text based documents. StringToWordVector filter is used to tokenize, stem and prepare documents for classification. The dataset consists of news documents with the topic of each ('computer', 'politics', 'science', 'sports') being considered as the nominal class variable.  
