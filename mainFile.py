import tkinter
import math
import ssl
from urllib.request import urlopen, urlretrieve
from urllib.parse import urlencode, quote_plus
import json
import twitteraccess
import webbrowser

#API KEY: AIzaSyAAs3-TdSTabUZhARFkk9giwErMHnWkTFY
#
# In HW8 Q2 and HW 9, you will use two Google services, Google Static Maps API
# and Google Geocoding API.  Both require use of an API key.
# You have two options for getting an API key:
#   1. (STRONGLY RECOMMENDED and WORTH 1 POINT on each assigment)
#     Get your own.  I think it is a valuable learning experience to see how
#     signing up for and using these services works.
#     Getting a key requires that you have a Google account *and* that you
#     "enable billing" BUT I guarantee you can do this homework for free. Google
#     provides a free trial that provides $300 worth of API usage and that amount
#     is *far* more than you will use, and provided $200/mo free Maps-related
#     free. The chances are small that you'd use more than $1/day while working
#     on the homework. You can get your free account here. (It actually includes
#     much more than just access to Google Maps services):
#     https://console.cloud.google.com
#     You can also start here:
#        https://developers.google.com/maps/documentation/geocoding/get-api-key
#     
#   2. Use the API key for the class that I will provide separate via ICON on
#      ICON. If you use this one *DO NOT* share it with anyone outside the class.
# 
# When you have the API key, put it between the quotes in the string below
GOOGLEAPIKEY = "AIzaSyAAs3-TdSTabUZhARFkk9giwErMHnWkTFY"

# To run the HW9 program, call the last function in this file: HW9().

# The Globals class demonstrates a better style of managing "global variables"
# than simply scattering the globals around the code and using "global x" within
# functions to identify a variable as global.
#
# We make all of the variables that we wish to access from various places in the
# program properties of this Globals class.  They get initial values here
# and then can be referenced and set anywhere in the program via code like
# e.g. Globals.zoomLevel = Globals.zoomLevel + 1
#

def generateMarkerString(currentTweetIndex, tweetLatLonList, mapCenterLatLon):
    markerurl = ''
    currtweet = tweetLatLonList[currentTweetIndex]
    if currtweet == None:
        markerurl += "&markers=color:red|{},{}".format(mapCenterLatLon[0], mapCenterLatLon[1])
    else:
        markerurl += "&markers=color:red|{},{}".format(currtweet[0], currtweet[1])
    index = 0
    while index < len(tweetLatLonList):
        if index == 0:
            markerurl += "&markers=color:blue|size:small"
        if index != currentTweetIndex:
            if tweetLatLonList[index] == None:
                markerurl += "|{},{}".format(mapCenterLatLon[0], mapCenterLatLon[1])
            else:
                markerurl += "|{},{}".format(tweetLatLonList[index][0], tweetLatLonList[index][1])
        index += 1
    return markerurl
        
class Globals:
   rootWindow = None
   mapLabel = None
   locationEntry = None
   searchEntry = None
   mapTypeId = 'roadmap'
   tweets = None
   codlist = []
   markerlist = ''
   centerLoc = []
   
   currentTweetIndex = 0
   currentTweetURLIndex = 0
   defaultLocation = "Mauna Kea, Hawaii"
   mapLocation = defaultLocation
   mapFileName = 'googlemap.gif'
   mapSize = 400
   zoomLevel = 9
   
# Given a string representing a location, return 2-element tuple
# (latitude, longitude) for that location 
#
# See https://developers.google.com/maps/documentation/geocoding/
# for details
#
def geocodeAddress(addressString):
   urlbase = "https://maps.googleapis.com/maps/api/geocode/json?address="
   geoURL = urlbase + quote_plus(addressString)
   geoURL = geoURL + "&key=" + GOOGLEAPIKEY

   ctx = ssl.create_default_context()
   ctx.check_hostname = False
   ctx.verify_mode = ssl.CERT_NONE
   
   stringResultFromGoogle = urlopen(geoURL, context=ctx).read().decode('utf8')
   jsonResult = json.loads(stringResultFromGoogle)
   if (jsonResult['status'] != "OK"):
      print("Status returned from Google geocoder *not* OK: {}".format(jsonResult['status']))
      return (0.0, 0.0) # this prevents crash in retrieveMapFromGoogle - yields maps with lat/lon center at 0.0, 0.0
   loc = jsonResult['results'][0]['geometry']['location']
   return (float(loc['lat']),float(loc['lng']))

# Contruct a Google Static Maps API URL that specifies a map that is:
# - is centered at provided latitude lat and longitude long
#
# - Globals.mapSize-by-Globals.mapsize in size (in pixels), 
# - is "zoomed" to the Google Maps zoom level in Globals.zoomLevel
#
# See https://developers.google.com/maps/documentation/static-maps/
#
# YOU WILL NEED TO MODIFY THIS TO BE ABLE TO
# 1) DISPLAY A PIN ON THE MAP
# 2) SPECIFY MAP TYPE - terrain vs road vs ...
#
def getMapUrl(lat, lng):
   urlbase = "http://maps.google.com/maps/api/staticmap?"
   args = "center={},{}&zoom={}&size={}x{}&format=gif&maptype={}{}".format(lat,lng,Globals.zoomLevel,Globals.mapSize, Globals.mapSize, Globals.mapTypeId, Globals.markerlist)
   args = args + "&key=" + GOOGLEAPIKEY
   mapURL = urlbase+args
   return  mapURL

# Retrieve a map image via Google Static Maps API:
# - centered at the location specified by global propery mapLocation
# - zoomed according to global property zoomLevel (Google's zoom levels range from 0 to 21)
# - width and height equal to global property mapSize
# Store the returned image in file name specified by global variable mapFileName
#
def retrieveMapFromGoogle():
   global lat
   global lng
   lat, lng = geocodeAddress(Globals.mapLocation)
   url = getMapUrl( lat, lng)
   urlretrieve(url, Globals.mapFileName)

########## 
#  basic GUI code

def displayMap():
   retrieveMapFromGoogle()    
   mapImage = tkinter.PhotoImage(file=Globals.mapFileName)
   Globals.mapLabel.configure(image=mapImage)
   # next line necessary to "prevent (image) from being garbage collected" - http://effbot.org/tkinterbook/label.htm
   Globals.mapLabel.mapImage = mapImage
   
def displayNewTweet():
   global numb
   numb = 1
   Globals.centerLoc = []
   Globals.markerlist = ''
   Globals.currentTweetIndex = 0
   Globals.codlist = []
   readEntryAndDisplayMap()

def readEntryAndDisplayMap():
   #### you should change this function to read from the location from an Entry widget
   #### instead of using the default location
   global lat
   global lng
   global totnumb
   global tweeturl
   global numb
   global currdict
   
   tweettext.configure(state='normal')
   tweettext.delete(1.0, tkinter.END)
   tweettext.configure(state='disabled')
   
   locationString = Globals.locationEntry.get()
   Globals.mapLocation = locationString
   searchString = Globals.searchEntry.get()
   Globals.tweets = twitteraccess.searchTwitter(searchString, latlngcenter=geocodeAddress(locationString))
   totnumb = (len(Globals.tweets))
   tweetnum.configure(text="Tweet {} out of {}".format(numb, totnumb))
   currdict = Globals.tweets[Globals.currentTweetIndex]
   
   lat, lng = geocodeAddress(Globals.mapLocation)
   tweetCod()
   Globals.centerLoc.append(lat)
   Globals.centerLoc.append(lng)
   Globals.markerlist = generateMarkerString(Globals.currentTweetIndex, Globals.codlist, Globals.centerLoc)

   
   if (len(currdict['entities']['urls']) != 0):
       if(Globals.currentTweetURLIndex < len(currdict['entities']['urls'])):
           tweeturl = currdict['entities']['urls'][Globals.currentTweetURLIndex]['expanded_url']
           urllabel.configure(text=tweeturl)
       if(not(Globals.currentTweetURLIndex < len(currdict['entities']['urls']))):
           urllabel.configure(text="No More URLs to Display")
   if(len(currdict['entities']['urls']) == 0):
       tweeturl = None
       urllabel.configure(text="URL: None")
        
   
   name = currdict['user']["name"]
   name = twitteraccess.printable(name)
   
   screenname = currdict['user']["screen_name"]
   screenname = twitteraccess.printable(screenname)
   
   text = currdict['text']
   text = twitteraccess.printable(text)
   
   nametext.configure(text="Name: {}".format(name))
   screentext.configure(text="ScreenName: {}".format(screenname))
   
   tweettext.configure(state='normal')
   tweettext.insert(1.0, str(text))
   tweettext.configure(state='disabled')
   
   displayMap()
   
def initializeGUIetc():
   global label
   global selectedButtonText  
   global choiceVar
   global tweettext
   global nametext
   global screentext
   global urllabel
   global tweetnum
   global numb
   global totnumb

   totnumb = None
   numb = 1
   Globals.rootWindow = tkinter.Tk()
   Globals.rootWindow.title("HW9")

   mainFrame = tkinter.Frame(Globals.rootWindow) 
   mainFrame.pack()


   choiceVar = tkinter.IntVar()
   selectedButtonText = "Roadmap"
   choiceVar.set(1)

   # until you add code, pressing this button won't change the map (except
   # once, to the Beijing location "hardcoded" into readEntryAndDisplayMap)
   # you need to add an Entry widget that allows you to type in an address
   # The click function should extract the location string from the Entry widget
   # and create the appropriate map.
   readEntryAndDisplayMapButton = tkinter.Button(mainFrame, text="Search Twitter and show map!", command=displayNewTweet)
   readEntryAndDisplayMapButton.pack()

   # we use a tkinter Label to display the map image
   
   zoominc = tkinter.Button(mainFrame, text="Zoom In", command=zoomincrease)
   zoominc.pack(side=tkinter.LEFT)
   zoomdec = tkinter.Button(mainFrame, text="Zoom Out", command=zoomdecrease)
   zoomdec.pack(side=tkinter.LEFT)
   
   Globals.mapLabel = tkinter.Label(mainFrame, width=Globals.mapSize, bd=2, relief=tkinter.FLAT)
   Globals.mapLabel.pack()
   
   locationlabel = tkinter.Label(mainFrame, text="Enter Location:")
   locationlabel.pack()
   Globals.locationEntry = tkinter.Entry(mainFrame)
   Globals.locationEntry.pack(side=tkinter.TOP)
   
   searchlabel = tkinter.Label(mainFrame, text="Search with keyword:")
   searchlabel.pack()
   Globals.searchEntry = tkinter.Entry(mainFrame)
   Globals.searchEntry.pack(side=tkinter.TOP)


   label = tkinter.Label(mainFrame, text = "Radio button choice is: {}".format(selectedButtonText))
   label.pack()

   choice1 = tkinter.Radiobutton(mainFrame, text="Roadmap", variable=choiceVar, value=1, command=radioButtonChosen)
   choice1.pack()

   choice2 = tkinter.Radiobutton(mainFrame, text="Satellite", variable=choiceVar, value=2, command=radioButtonChosen)
   choice2.pack()

   choice3 = tkinter.Radiobutton(mainFrame, text="Hybrid", variable=choiceVar, value=3, command=radioButtonChosen)
   choice3.pack()

   choice4 = tkinter.Radiobutton(mainFrame, text="Terrain", variable=choiceVar, value=4, command=radioButtonChosen)
   choice4.pack()
   
   nametext = tkinter.Label(mainFrame, text="Name: None")
   nametext.pack()
   
   screentext = tkinter.Label(mainFrame, text="ScreenName: None")
   screentext.pack()
   
   newtweet = tkinter.Button(mainFrame, text="Next Tweet", command=nextTweet)
   newtweet.pack()
   
   oldtweet = tkinter.Button(mainFrame, text="Previous Tweet", command=prevTweet)
   oldtweet.pack()
   
   urllabel = tkinter.Label(mainFrame, text="URL: None")
   urllabel.pack()
   
   oldurl = tkinter.Button(mainFrame, text="Previous URL", command=prevURL)
   oldurl.pack(side=tkinter.LEFT)
   
   newurl = tkinter.Button(mainFrame, text="Next URL", command=nextURL)
   newurl.pack(side=tkinter.LEFT)
   
   gourl = tkinter.Button(mainFrame, text="Go to displayed URL", command=gotoweb)
   gourl.pack(side=tkinter.LEFT)
   
   tweetnum = tkinter.Label(mainFrame, text="Tweet {} out of {}".format(numb, totnumb))
   tweetnum.pack(side=tkinter.RIGHT)
   
   tweettext = tkinter.Text(mainFrame, state='disabled')
   tweettext.pack()
   
   
   twitteraccess.authTwitter()
   
def gotoweb():
    webbrowser.open(	tweeturl)
   
def tweetCod():
    global lat
    global lng
    
    for tweet in Globals.tweets:
        if tweet['coordinates'] == None:
            Globals.codlist.append(Globals.centerLoc)
        else:
            codrev = tweet['coordinates']['coordinates']
            codrev[1], codrev[0] = codrev[0], codrev[1]
            Globals.codlist.append(codrev)


def nextURL():
    Globals.currentTweetURLIndex += 1
    readEntryAndDisplayMap()
    
def prevURL():
    Globals.currentTweetURLIndex -= 1
    readEntryAndDisplayMap()

def nextTweet():
    global numb
    global tweetnumb
    
    if numb < len(Globals.tweets):
        numb += 1
    tweetnum.configure(text="Tweet {} out of {}".format(numb, totnumb))
    Globals.currentTweetURLIndex = 0
    if (Globals.currentTweetIndex < len(Globals.tweets)):
        Globals.currentTweetIndex += 1
    readEntryAndDisplayMap()
    
def prevTweet():
    global numb
    global tweetnumb
    
    if numb > 1:
        numb -= 1
    tweetnum.configure(text="Tweet {} out of {}".format(numb, totnumb))
    Globals.currentTweetURLIndex = 0
    Globals.currentTweetIndex -= 1
    readEntryAndDisplayMap()
    

def radioButtonChosen():
   global selectedButtonText
   global choiceVar

   if choiceVar.get() == 1:
      selectedButtonText = "Roadmap"
      Globals.mapTypeId = "roadmap"
   if choiceVar.get() == 2:
      selectedButtonText = "Satellite"
      Globals.mapTypeId = "satellite"
   if choiceVar.get() == 3:
      selectedButtonText = "Hybrid"
      Globals.mapTypeId = "hybrid"
   if choiceVar.get() == 4:
      selectedButtonText = "Terrain"
      Globals.mapTypeId = "terrain"
   displayMap()
   label.configure(text= "Radio button choice is: {}".format(selectedButtonText))


def zoomincrease():
   Globals.zoomLevel += 1
   displayMap()

def zoomdecrease():
   Globals.zoomLevel -= 1
   displayMap()

def HW9():
    initializeGUIetc()
    displayMap()
    Globals.rootWindow.mainloop()

