Here's part of my capstone project, mostly the data input pipeline, I might add more as time goes. 

Input: Earning Call Transcript
Network: Bert-based deep model with Keras
Label/Prediction: Change in stock price normalized to +-1.0 being equal to +-10%.

The files uploaded were almost exclusively authored by me.
It's the first part of the data input pipeline for an NLP model my team was developing for the capstone.
The input is a folder with pdfs containing earning call transcripts. The output is an Excel file with the columns "Year-Month-Day,	Quarter,	Ticker,	Text,	Price_Prior,	Price_During,	Price_After".

From there a Jupter notebook developed by a teammate would add the "industry" column and filter the rows to 'Information Technology'.

From there the file would be ran through "Normalize Prices_V2" which would normalize the prices as per description of the model above.

Then, it would be run through "Merging Texts on Ticker_Date V2" to merge all calls that happened on each day for each company in an array in a cell.
To work around Excel's limit of 32k characters per cell, the file is then converted to a tab delimited csv, with the array safely encoded using '", "'.

The file can then be read by our Deep Learning models using the code sampled in "CODE for Reading From Tab Delimited File".
As the individual texts are preserved, we can quickly run the model on any combination of them, or all of them. We can input them one at a time, or as a single large text.

As for the results of our analysis of the correlation between transcript and price changes, our model results were similar to previous studies done on it, as in the correlation was very tenous and dealing with very large bodies of text was extremely hard to manage for an NLP Deep Learning model.
