# Connected Clock
In the Harry Potter universe, the Weasley family has a magical clock in their kitchen. Instead of telling time, this clock's hands tell the location of each of the family members. Each hand of the clock has a picture of a family member and the hand points at different labels on the clock face such as Home, Quidditch, School, Lost, and Mortal Peril. While the Weasley family used magic to accomplish this, we muggles could just as well accomplish this project by using technology.

Our project has an Android app that uses geofences to track users' locations (such as Home, School, Store, Traveling, etc). This app sends updates to a server when the user enters or leaves different geofences. A wifi-enabled Arduino device periodically polls the server to retrieve user locations. The arduino device then moves its servos in order to change the hands on the clock face.

Here is a video of the system in action. You can see the user modifying geofences as well as a live demo of the software and hardware.

[Watch the Demo!](https://drive.google.com/file/d/1oWXl59Yeh67KuK059gy5DeBxBmPtj64d/view?usp=sharing)

Here is a behind the scenes video demonstrating the arduino's hardware.

[Behind the Scenes](https://drive.google.com/file/d/1pumeV0WCx7sLN-HO-RRrDbw5ONNyLnj_/view?usp=sharing)
