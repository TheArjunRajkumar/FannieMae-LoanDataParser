# FannieMae-LoanDataParser

This code is a tool for compiling price-optimized mortgage-backed securities by parsing relevant loan data from text files. 

When arranging a security, one central goal is to maximize the objective value function of various loans pools into order to maximize the net profitability of the combined security. However, loans must meet a set of criteria before being selected as a candidate for a pool. Since loan transaction lists are far too large to parse manually, this code allows users to manipuate data in these expansive lists and extract any relevent data they are seeking. 

Over the course of this project, the following questions were answered:

      How do you remove unwanted columns of data from input text files?
      How do you identify the latest transaction of a loan from transaction lists?
      How do you read a text file using the Scanner class?
      How do you merge files using a BufferedReader?
      How do you convert Scanner Object into workable data?
      How do you organize criteria checking tools to avoid code redundancy?
      How do you truncate a String representation of a float?
      
The current status of this implementation reduces transaction list sizes for certain pooling criteria by roughly 99.97%.
