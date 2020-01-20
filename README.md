# FannieMae-LoanDataParser

This code produces price-optimized mortgage-backed securities by parsing relevant loan data from text files. The objective is 
to maximize the objective value function value of various loans pools into order to maximize the net profitability of each pool.

Over the course of this project, the following relevent questions were answered:

      How do you remove unwanted columns of data from input text files?
      How do you identify the latest transaction of a loan from transaction lists?
      How do you read a text file using the Scanner class?
      How do you merge files using a BufferedReader?
      How do you convert Scanner Object into workable data?
      How do you organize criteria checking tools to avoid code redundancy?
      How do you truncate a String representation of a float?
      
The current status of this implementation reduces transaction list sizes for certain pooling criteria to roughly 0.0285% of its
original size.
