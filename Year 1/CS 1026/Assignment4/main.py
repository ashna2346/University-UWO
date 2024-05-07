# Computer Science 1026A - Section 001
# Ashna Mittal - 251206758

from sentiment_analysis import compute_tweets
# accessing the function compute tweets
tweetfile = input("Please enter the name of the tweet file you wish to process: ")
keyfile = input("Please enter the name of the keyword file you wish to use: ")
# prompts the user for the files to analyze

values = compute_tweets(tweetfile, keyfile)
# accesses the list of values to be printed

try:
    print()
    print("Eastern Region Results \n%s" % "Happiness score: {:.3f}".format(values[0][0]), "\n%s" % "Number of keyword tweets:", values[0][1], "\n%s" % "Total number of tweets:", values[0][2])

    print()
    print("Central Region Results \n%s" % "Happiness score: {:.3f}".format(values[1][0]), "\n%s" % "Number of keyword tweets:", values[1][1], "\n%s" % "Total number of tweets:", values[1][2])

    print()
    print("Mountain Region Results \n%s" % "Happiness score: {:.3f}".format(values[2][0]), "\n%s" % "Number of keyword tweets:", values[2][1], "\n%s" % "Total number of tweets:", values[2][2])

    print()
    print("Pacific Region Results \n%s" % "Happiness score: {:.3f}".format(values[3][0]), "\n%s" % "Number of keyword tweets:", values[3][1], "\n%s" % "Total number of tweets:", values[3][2])
# printing the final answers

except:
    print("Files does not exist")
