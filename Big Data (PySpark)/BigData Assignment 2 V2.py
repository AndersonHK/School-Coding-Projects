"""
Created by Anderson H Klein from MSIS 2627
"""

#Importing PySpark for Python Program
from pyspark.sql import SparkSession

#Creating a PySpark Session
spark = SparkSession.builder\
        .appName("CheckPyspark")\
        .getOrCreate()

"""############################################################
The Input
Assuming books.txt is located in the same directory as the spark session.
If running on the shell, following two commands are the first ones required.
Else, program can be made shorther by running textFile("books.txt") directly.
############################################################"""

input_path = "books.txt"
records = spark.sparkContext.textFile(input_path)

"""############################################################
The Functions
Here the functions that will be called with map and reduce should be declared.
These will be called with the commands that will produce the output.
############################################################"""

def Mapper (value):
     temp = value.split(',')
     if temp[0][0:4]=='ISBN' and len(temp)>2:
             value = [(temp[1].lower(),1),(temp[2].lower(),1),('unique-list-of-categories',[temp[1].lower()]),('unique-list-of-categories',[temp[2].lower()])]
     else:
             value = [('excluded-records',1)]
     value.append(('total-number-of-books',1))
     return value

def Reducer(value1,value2):
    if type(value1) == int:
          return value1+value2
    else:
        for value in value2:
            if value not in value1:
                value1.append(value)
        return value1

"""############################################################
The Output
Here are the commands required to produce the ouput.
They are a flatMap and a reduceByKey. The prints are for showing the results.
In shell, the print() is not require, just the collect.
############################################################"""

mapped = records.flatMap(Mapper)
print(mapped.collect())

Output = mapped.reduceByKey(Reducer)
print(Output.collect())

#This command is not required in shell, it closes the spark enviroment in python
spark.stop()
