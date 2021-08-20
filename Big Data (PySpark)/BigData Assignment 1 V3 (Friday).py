"""Program created by By Anderson Klein in January 2020

Goal is to simulate a Map and Reduce program.

The input is ideally a string with the format below and a surogated key.

The output are:
unique-category-name aggregated-total-frequency
"excluded-records" total-number-of-excluded-records
"total-number-of-books" total-number-of-books-with-or-without-error
"unique-list-of-categories" unique list of categories"""

#input format desired
"""<bookID><,><category-1><,><category-2>"""

#Importing pyspark but not using in the pseldo code
import pyspark

#creating simulated block retrieved from hypotetical input = "root_dir/"
data = """ISBN-100,sales,biology
IS-01235,sales,econ
ISBN-101,sales,econ
ISBN-102,sales,biology
ISBN-109,econ,sales
ISBN-103,CS,sales
ISBN-104,CS,biology
ISBN-105,CS,econ
ISBN-200,CS"""

#creating variables necessary to simulate input, sort&shuffle and output
PseldoSortAndShuffle = []
Dictionary = {}
Output = {}

#Simulating the input process for demo purposes
def PseldoMagicImporterForDemo(data):
    newArray = []
    Raw = data.splitlines()
    return Raw

#simulating the emit function for demo purposes
def Emit(key,value):
    print('A mapper has emited ('+str(key)+','+str(value)+')')
    PseldoSortAndShuffle.append([key,value])

#defining emit lowercased for the reducer
def emit(key,value):
    print('A reducer has emited ('+str(key)+','+str(value)+')')
    Output[key] = value

"""############################################################
The Mapper

Input: 	key = surrogated number. It is then ignored.
		value = string of text, a record of a book, hopefully as <bookID><,><category-1><,><category-2>

Output1:filtering = it will get emited always
		key = 'total-number-of-books'
		value = 1
		
Output2:filtering = will get emited if record is not in desired format with bookID starting with ISBN
		key = 'excluded-records'
		value = 1
		
Output3:filtering = will get emited for records in desired format
		key = unique-category-name
		value = 1
		
Output4:filtering = will get emited for records in desired format
		key = 'unique-list-of-categories'
		value = unique-category-name
		
############################################################"""
	
#my mapper function
def Mapper(key,value):
    #Breaking the input into ISBN + category1 + category2
    temp = value.split(',')
    #filtering for inputs that don't have ISBN or 2 categories 
    if temp[0][0:4]=='ISBN' and len(temp)>2:
        #emitting categories 1 and 2 as lowercase strings(text)
        Emit(temp[1].lower(),1)
        Emit(temp[2].lower(),1)
        #terribly unoptimized way to create unique category list
        Emit('unique-list-of-categories',temp[1].lower())
        Emit('unique-list-of-categories',temp[2].lower())
    else:
        #if excluded, emit to excluded count
        Emit('excluded-records',1)
    #regardless if valid, count book towards total
    Emit('total-number-of-books',1)
	
"""############################################################
The Reducer

Input1: key = string of text: 'total-number-of-books','excluded-records',unique-category-name
		value = array of ints: the occurencies of category of total to be counted

Output1:goal = it will sum all numbers in array
		key = 'total-number-of-books'
		value = int: total number of books
		
Output2:goal = it will sum all numbers in array
		key = 'excluded-records'
		value = int: number of excluded records

Output3:goal = it will sum all numbers in array
		key = unique-category-name
		value = int: number of times category appeared in valid records
		
Input2:	key = string of text: 'unique-list-of-categories'
		value = array of strings: unique-category-name
		
Output4:goal = it will remove duplicates from array, unique categories
		key = 'unique-list-of-categories'
		value = string of text: array with each unique-category-name
		
############################################################"""

#my reducer function
def Reducer(key,values):
    #checking whether this is the unique-list key
    if key == 'unique-list-of-categories':
        categories = []
        #if so, adding each unique category to list
        for category in values:
            if category in categories:
                pass
            else:
                categories.append(category)
        #emitting list
        emit(key,categories)
    else:
        #it is a normal category or total that needs to be "counted"
        count = 0
        #adding each value in values to a counter
        for value in values:
            count += value
        #emitting the total value for the given key ('category',int total_count)
        emit(key,count)

#simulating sort and shuffle for demo purposes
def SortAndShuffle():
    for key in PseldoSortAndShuffle:
        if key[0] in Dictionary.keys():
            Dictionary[key[0]].append(key[1])
        else:
            Dictionary[key[0]] = [key[1]]

#calling for the simulated input
root_values = PseldoMagicImporterForDemo(data)

#simuating running mapper for all rows
row_n = 0
for entry in root_values:
    print('A mapper has received ('+str(row_n)+','+str(entry)+')')
    Mapper(row_n,entry)
    row_n += 1

#simuating Sort and Shuffle
SortAndShuffle()

#simulating running reducer for all rows
for key in Dictionary:
    print('A reducer has received ('+str(key)+','+str(Dictionary[key])+')')
    Reducer(key,Dictionary[key])

#yey, the program has completed with success
print("The ouput is:")
print(Output)
print('The program has finished.')
#Instead of printing the ouput, of course, it would be written to: Output = "temp/ouput/book"

"""SAMPLE OUTPUT FROM EXECUTION:
A mapper has received (0,ISBN-100,sales,biology)
A mapper has emited (sales,1)
A mapper has emited (biology,1)
A mapper has emited (unique-list-of-categories,sales)
A mapper has emited (unique-list-of-categories,biology)
A mapper has emited (total-number-of-books,1)
A mapper has received (1,IS-01235,sales,econ)
A mapper has emited (excluded-records,1)
A mapper has emited (total-number-of-books,1)
A mapper has received (2,ISBN-101,sales,econ)
A mapper has emited (sales,1)
A mapper has emited (econ,1)
A mapper has emited (unique-list-of-categories,sales)
A mapper has emited (unique-list-of-categories,econ)
A mapper has emited (total-number-of-books,1)
A mapper has received (3,ISBN-102,sales,biology)
A mapper has emited (sales,1)
A mapper has emited (biology,1)
A mapper has emited (unique-list-of-categories,sales)
A mapper has emited (unique-list-of-categories,biology)
A mapper has emited (total-number-of-books,1)
A mapper has received (4,ISBN-109,econ,sales)
A mapper has emited (econ,1)
A mapper has emited (sales,1)
A mapper has emited (unique-list-of-categories,econ)
A mapper has emited (unique-list-of-categories,sales)
A mapper has emited (total-number-of-books,1)
A mapper has received (5,ISBN-103,CS,sales)
A mapper has emited (cs,1)
A mapper has emited (sales,1)
A mapper has emited (unique-list-of-categories,cs)
A mapper has emited (unique-list-of-categories,sales)
A mapper has emited (total-number-of-books,1)
A mapper has received (6,ISBN-104,CS,biology)
A mapper has emited (cs,1)
A mapper has emited (biology,1)
A mapper has emited (unique-list-of-categories,cs)
A mapper has emited (unique-list-of-categories,biology)
A mapper has emited (total-number-of-books,1)
A mapper has received (7,ISBN-105,CS,econ)
A mapper has emited (cs,1)
A mapper has emited (econ,1)
A mapper has emited (unique-list-of-categories,cs)
A mapper has emited (unique-list-of-categories,econ)
A mapper has emited (total-number-of-books,1)
A mapper has received (8,ISBN-200,CS)
A mapper has emited (excluded-records,1)
A mapper has emited (total-number-of-books,1)
A reducer has received (sales,[1, 1, 1, 1, 1])
A reducer has emited (sales,5)
A reducer has received (biology,[1, 1, 1])
A reducer has emited (biology,3)
A reducer has received (unique-list-of-categories,['sales', 'biology', 'sales', 'econ', 'sales', 'biology', 'econ', 'sales', 'cs', 'sales', 'cs', 'biology', 'cs', 'econ'])
A reducer has emited (unique-list-of-categories,['sales', 'biology', 'econ', 'cs'])
A reducer has received (total-number-of-books,[1, 1, 1, 1, 1, 1, 1, 1, 1])
A reducer has emited (total-number-of-books,9)
A reducer has received (excluded-records,[1, 1])
A reducer has emited (excluded-records,2)
A reducer has received (econ,[1, 1, 1])
A reducer has emited (econ,3)
A reducer has received (cs,[1, 1, 1])
A reducer has emited (cs,3)
The ouput is:
{'sales': 5, 'biology': 3, 'unique-list-of-categories': ['sales', 'biology', 'econ', 'cs'], 'total-number-of-books': 9, 'excluded-records': 2, 'econ': 3, 'cs': 3}
The program has finished."""
