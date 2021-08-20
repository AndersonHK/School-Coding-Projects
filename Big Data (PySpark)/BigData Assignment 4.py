"""
Created By Anderson Henrique Klein in Winter 2019 for MSIS 2627
"""

#Importing PySpark and sys
from pyspark.sql import SparkSession
import pyspark.sql.functions as sql
from pyspark.sql.functions import desc
from pyspark.sql.functions import lower, col
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
df = spark.read.format('com.databricks.spark.csv')\
     .options(header='true', inferschema='true')\
     .load(input_path)

print("Dropping Collumns from DF and converting to lowercase...")
df = df.select([lower(col('namelast')).alias('namelast'),\
                lower(col('namefirst')).alias('namefirst'),\
                lower(col('namemid')).alias('namemid'),\
                lower(col('visitee_namelast')).alias('visitee_namelast'),\
                lower(col('visitee_namefirst')).alias('visitee_namefirst')])

print()
print("Removing invalid rows and filling N/A namemid with blanks...")
valid_records = df.filter((df.visitee_namelast.isNotNull()) & (df.namelast.isNotNull())).fillna("")
valid_records.registerTempTable("valid_records")

print()
print("Here are the top 10 rows from the filtered dataframe:")
valid_records.show(10)

print()
print("The number of dropped rows is: "+str(df.count()-valid_records.count()))


print()
print("The 10 most frequent visitors are: ")
print("<lastName,firstName,middleName><frequency>")
spark.sql("SELECT CONCAT(namelast, ' ',  namefirst, ' ',  namemid) FROM valid_records")\
                  .groupBy("concat(namelast,  , namefirst,  , namemid)")\
                  .count().sort(desc("count")).show(10)

print("The 10 most visited people are: ")
print("<lastName,firstName><frequency>")
spark.sql("SELECT CONCAT(visitee_namelast, ' ',  visitee_namefirst) FROM valid_records")\
                  .groupBy("concat(visitee_namelast,  , visitee_namefirst)")\
                  .count().sort(desc("count")).show(10)

print()
print("The 10 most frequent combinations are: ")
print("<visitor-visitee><frequency>")
spark.sql("SELECT CONCAT(namelast, ' ',  namefirst, ' ',  namemid, '-', visitee_namelast, ' ',  visitee_namefirst) FROM valid_records")\
                  .groupBy("concat(namelast,  , namefirst,  , namemid, -, visitee_namelast,  , visitee_namefirst)")\
                  .count().sort(desc("count")).show(10,False)

print()
print("The program has completed successfully")
