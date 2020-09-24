# Connected Clock
Create a magical Weasley clock using muggle technology.

In the Harry Potter universe, the Weasley family has a magical clock in their kitchen. Instead of telling time, this clock's hands tell the location of each of the family members. Each hand of the clock has a picture of a family member and the hand points at different labels on the clock face such as Home, Quidditch, School, Lost, and Mortal Peril. While the Weasley family used magic to accomplish this, we muggles could just as well accomplish this project by using technology.

For the project, I propose having an Android app that uses geofences to track users' locations (such as Home, School, Store, Traveling, etc). This app will send updates to a server when the user enters or leaves different geofences. A wifi-enabled Arduino device will periodically poll the server to retrieve user locations. The device will then move its servos in order to change the hands on the clock face.

I started working on this project over the summer (after watching a Harry Potter marathon), but life got in the way and I never got a fully working system. For this project, I'd like to start over from scratch and build it as a team in a way that we all think would work best. I do still have my Arduino board, wifi module, and servos set up for this project, so we wouldn't need to worry too much about that. If we decide to go forward with this project, I'll hurry up and set up the rest of the hardware.

## Team
I am looking for one or two other teammates to work on this project.

## Relational Database
Open to suggestions. Most likely tables include:
* Clocks/Families (if multiple families use this app, they'll need to be grouped by clock or family and have their own set of Statuses and Geofences)
* Users (all the users that have a geofence app)
* Geofences (collection of geofences around areas of interest, specific to a particular family/clock)
* Statuses (perhaps multiple geofences correspond to a single status such as several different geofences are around different stores, all representing the status of "Shopping")

## NoSQL Database
I originally used Google's Firestore, which is a NoSQL document based database, but we can change it to whatever we'd like.

## Business
This will be an open source project with no plans to monetize or profit.

## Legal
I plan to keep this project open source.

## Technical
The following are steps for this project:
* Build a relational database which can store clocks, users, geofences, and statuses.
* Build a server (maybe in Java, depending on what people want) which can update and read from that database.
* Build an Android client (in Kotlin) which can handle geofences and tell the server when the user has moved.
* Add the ability to create families, geofences, and statuses on the android device.
* Have the Arduino device poll the server and retrieve the current statuses of users.
* If we have time, it would be awesome to make an iOS app that could track users as well (but not necessary).
* ???
* PROFIT!!!
