Library setup:
This program uses ini4j API (http://ini4j.sourceforge.net/index.html) for database functionalities.
So, before running the program, you have to set up ini4j API in Intellij. Here is the tutorial of how to set up:
1. Select File in the top menu, “File” > “Project Structure”.
2. Find the “Library” option on the left side, then click the “+” icon. Choose “Java”
    and then select “...\phase2\lib\ini4j-0.5.4.jar”.
3. “OK” > “Apply”.
4. Done! Now you can check a gateway class in the GateWay package and you should
    see the line “import org.ini4j.*” is not underlined by red.

Program Introduction:
This program is a design of conference system which allow users to do several operations.
We have three kinds of users: Organizer, Speaker, Attendee.
Each type of user will login to their own system and only allowed to do operations depend on their type.

Organizer are allowed to access to message system, create speaker account, schedule a event, and create room.
They are able to message all the attendees and speakers, at once or individually.

Speakers are allowed to read the information of talks that they will present(like time, room, etc) and access to messaging system.
They are able to message all the attendees who attend their own talks, at once or individually.

Attendees are allowed to read information about the event they registered, sign up anc cancel registration for new event, and also access to messaging system.
They are able to message to other attendees and the speakers of the talks they registered.

In our program, the dashboard of each systems are clear and precise to do all the operations above.

Instructions:
1. When you enter our program, please follow the showed instruction. Please do not enter any invalid response.
2. The valid response of dashboard are mostly integer. For example, if the dashboard shows "3 -> View your inbox" and you want to view all the message you got. Please enter "3".
3. If this is the first time you use the program, you'd better to signup first.
4. When you type in a message, if you want to finish typing, please type "end" in a new line.

