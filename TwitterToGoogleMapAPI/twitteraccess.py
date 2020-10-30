import oauth2 as oauth
from urllib.parse import quote_plus
import json

# 
# The code in this file won't work until you set up your Twitter "app"
# at https://dev.twitter.com/apps
# After you set up the app, copy the four long messy strings and put them here.
#

CONSUMER_KEY = "bNuGG5Muq9P0xHCR2gFh3Tr58"
CONSUMER_SECRET = "hVGcwTwl3c9NXrPKjTusLkkJq9GzLaqe3885zgWOenWSC01pur"
ACCESS_KEY = "1123083474611724288-ZnI0zvFLO2fNq1E8MfkicZkhunLi6b"
ACCESS_SECRET = "xmfhpLgWZG7EzFICWZ0qMq4zYCa3GEAOhOuDwgrCLE0J2"

# Call this function after starting Python.  It creates a Twitter client object (in variable client)
# that is authorized (based on your account credentials and the keys above) to talk
# to the Twitter API. You won't be able to use the other functions in this file until you've
# called authTwitter()
#
def authTwitter():
    
    global client   
    consumer = oauth.Consumer(key=CONSUMER_KEY, secret=CONSUMER_SECRET)
    access_token = oauth.Token(key=ACCESS_KEY, secret=ACCESS_SECRET)
    client = oauth.Client(consumer, access_token)


# Study the documenation at 
# https://developer.twitter.com/en/docs/tweets/search/api-reference/get-search-tweets
# to learn about construction Twitter queries and at
# https://developer.twitter.com/en/docs/tweets/data-dictionary/overview/tweet-object
# the understand the structure of the JSON results returned for search queries
#

# Try:
#      tweets = searchTwitter("finals")
#
# Iowa City's lat/lng is [41.6611277, -91.5301683] so also try:
#      tweets = searchTwitter("finals", latlngcenter=[41.6611277, -91.5301683])
#
# To find tweets with location, it's often helpful to search in big cities.
#      E.g. lat/long for Times Square in NYC is (40.758895, -73.985131)
#      tweets = searchTwitter("party", latlngcenter=(40.758895, -73.985131))
#      usually yields several tweets with location detail
#
def searchTwitter(searchString, count = 20, radius = 2, latlngcenter = None):    
    query = "https://api.twitter.com/1.1/search/tweets.json?q=" + quote_plus(searchString) + "&count=" + str(count)

    # if you want JSON results that provide full text of tweets longer than 140
    # characters, add "&tweet_mode=extended" to your query string.  The
    # JSON structure will be different, so you'll have to check Twitter docs
    # to extract the relevant text and entities.
    #query = query + "&tweet_mode=extended" 
    if latlngcenter != None:
        query = query + "&geocode=" + str(latlngcenter[0]) + "," + str(latlngcenter[1]) + "," + str(radius) + "km"

    response, data = client.request(query)
    data = data.decode('utf8')
    resultDict = json.loads(data)
    # The most important information in resultDict is the value associated with key 'statuses'
    tweets = resultDict['statuses']
    tweetsWithGeoCount = 0 
    for tweetIndex in range(len(tweets)):
        tweet = tweets[tweetIndex]
        if tweet['coordinates'] != None:
            tweetsWithGeoCount += 1
            print("Tweet {} has geo coordinates.".format(tweetIndex))           
    return tweets
    

# sometimes tweets contain emoji or other characters that can't be
# printed in Python shell, yielding runtime errors when you attempt
# to print.  This function can help prevent that, replacing such charcters
# with '?'s.  E.g. for a tweet, you can do print(printable(tweet['text']))
#
def printable(s):
    result = ''
    for c in s:
        result = result + (c if c <= '\uffff' else '?')
    return result

#####

# You don't need the following functions for HW 9. They are just additional
# examples of Twitter API use.
#
def whoIsFollowedBy(screenName):
    global response
    global resultDict
    
    query = "https://api.twitter.com/1.1/friends/list.json?&count=50"
    query = query + "&screen_name={}".format(screenName)
    response, data = client.request(query)
    data = data.decode('utf8')
    resultDict = json.loads(data)
    for person in resultDict['users']:
        print(person['screen_name'])
    
def getMyRecentTweets():
    global response
    global data
    global statusList 
    query = "https://api.twitter.com/1.1/statuses/user_timeline.json"
    response, data = client.request(query)
    data = data.decode('utf8')
    statusList = json.loads(data)
    for tweet in statusList:
        print(printable(tweet['text']))
        print()

 
