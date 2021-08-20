"""
Created By Anderson Henrique Klein in Winter 2019 for MSIS 2627
"""

"""
#Importing PySpark and sys
from pyspark.sql import SparkSession
import pyspark.sql.functions as sql
from pyspark.sql.functions import desc
from pyspark.sql.functions import lower, col
import sys
"""

from pyspark.sql import SparkSession
from graphframes import GraphFrame

print()
print("Creating a PySpark Session...")
spark = SparkSession.builder\
        .appName('triangles')\
        .getOrCreate()

"""
print("Session created with name "+sys.argv[0])

print()
print("Running code on file from root\ "+sys.argv[1])
input_path = sys.argv[1] #"whitehouse_waves-2016_12.csv"
df = spark.read.format('com.databricks.spark.csv')\
     .options(header='true', inferschema='true')\
     .load(input_path)
     """
vertices = [('n1',),('n2',),('n3',),('n4',)]
collumn_names = ['id']
vertices = spark.createDataFrame(vertices, collumn_names)

edges = [('n1','n2'),('n2','n3'),('n3','n1'),('n1','n3'),('n2','n4'),('n4','n3'),('n3','n2')]
collumn_names = ['src','dst']
edges = spark.createDataFrame(edges, collumn_names)

print(vertices)
print(edges)

graph = GraphFrame(vertices,edges)
