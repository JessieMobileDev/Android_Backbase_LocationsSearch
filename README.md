# Android_Backbase_LocationsSearch
Backbase assignment involving locations and search.

### Decisions take along the development of this app
- **Converting the json file:** I personally love using the library Gson, it's quick and literally a one liner when converting. I decided this was the best option to go with. I converted the json file into a string, which I fed into Gson function to convert into a list of my custom object.

- **Connection handler:** One of the features of this app is to be able to view the location on the map, and that can't be done without internet connection. I made sure that the phone had connection before doing any further actions.

- **Permissions:** The user should be able to tell the application if they want to share their location or not. Just like any other sort of permission. The user is prompted with an alert that ask them for permissions.

- **Map Markers:** Even though it wasn't required, I thought it was necessary to add markers on the map so that the user understand that he got feedback from them clicking on the list.

- **Saved list for reuse:** Parsing the cities.json list takes about 2 seconds, very long for a user to keep seeing over and over. For that matter, I decided to save the list in the instance state bundle so that it could be reused again without the need of getting parsed again.

### Challenges:
- **Saving other info:** Unfortunatelly I could not get to save information to display again when orientation changed. Information such as: search text and location on map. I tried a couple of approaches, and I noticed I spent a lot of time on that one thing. Decided that I could come back on a later time to polish it.
