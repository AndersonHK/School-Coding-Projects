"""
Created By Anderson Henrique Klein in Winter 2019 for MSIS 2627
"""

#Importing PySpark and sys
from pyspark.sql import SparkSession
import sys

print()
print("Creating a PySpark Session...")
spark = SparkSession.builder\
        .appName(sys.argv[0])\
        .getOrCreate()
print("Session created with name "+sys.argv[0])

print()
print("Running code on file from root\ "+sys.argv[1])
input_path = sys.argv[1] #"whitehouse_waves-2016_12.csv"
records = spark.sparkContext.textFile(input_path)

print("Here are the top 10 rows from the file:")
records_10 = records.take(10)
print(records_10)

#spliting rows and then filtering into valid and invalid
split_records = records.map(lambda x: x.lower().split(','))
invalid_records = split_records.filter(lambda x: x[0]=="" or x[19]=="")
valid_records = split_records.filter(lambda x: x[0]!="" and x[19]!="" and x[0]!='NAMELAST')

#creating visitor count rdd <lastName,firstName,middleName><frequency>
visitors = valid_records.map(lambda x: (x[0]+','+x[1]+','+x[2],1))
visitors_count = visitors.reduceByKey(lambda x,y: x+y)

#creating visitee count rdd <lastName,firstName><frequency>
visitee = valid_records.map(lambda x: (x[19]+','+x[20],1))
visitee_count = visitee.reduceByKey(lambda x,y: x+y)

#creating visitor-visitee count rdd <visitor-visitee><frequency>
combinations = valid_records.map(lambda x: (x[0]+','+x[1]+','+x[2]+'-'+x[19]+','+x[20],1))
combinations_count = combinations.reduceByKey(lambda x,y: x+y)

#printing ouput
print()
print("The number of dropped rows is: "+str( invalid_records.count() ))

print()
print("The 10 most frequent visitors are: ")
print("<lastName,firstName,middleName><frequency>")
print(visitors_count.takeOrdered(10, key=lambda x: -x[1]))

print()
print("The 10 most visited people are: ")
print("<lastName,firstName><frequency>")
print(visitee_count.takeOrdered(10, key=lambda x: -x[1]))

print()
print("The 10 most frequent combinations are: ")
print("<visitor-visitee><frequency>")
print(combinations_count.takeOrdered(10, key=lambda x: -x[1]))

print()
print("The program has completed successfully")
