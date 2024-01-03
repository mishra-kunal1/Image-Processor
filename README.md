# Image Manipulation and Enhancement
 
### Programming Design
 
The assignment necessitates implementing various image effects within an image processing application.  It has three components Model, View and Controller
 
## Model 
The model of this project is responsible for the set of operations that are going to be performed on the image.
An interface, IModel, has been established, encapsulating method signatures intended for client utilization.


The Image class functions as an abstract implementation of this interface. It serves as the base for two distinct concrete subclasses, RGBImage and GrayscaleImage, both extending the functionality of the Image class. These subclasses are responsible for all the image manipulations within the application.


ImageUtil class is also implemented in the model to load the images in the application for the required processing. It also facilitates saving an image after the required processing.

The core logic for flipping, brightening, darkening, splitting to individual (red, green, blue) components, combining RGB components, blurring, sharpening, converting to grayscale, and sepia operation is implemented in the model. 

### Updates for Assignment 5
The logic for operations like compression, visualizing images using histograms, color correction, and level adjustment is added in the concrete classes. There was no significant change in the design of the model as new functionalities were added directly to the interface and implemented in the concrete classes.

### Updates for Assignment 6
There were no changes in the model design as no new operations were added.


 
## Controller
The controller acts as a bridge between the model and the view. Command line design pattern is implemented in the controller design where the relevant command is executed as defined in the predefined command map. Each command has been represented as a separate class of its own that will check the validity of the command and will finally call the command’s implementation in the model.

### Update for Assignment 5
New commands were added for compression, creating histograms, color correction, and level adjustment in the command map to perform the required operations. The new commands then call their respective implementation in the model to execute the logic. There were no significant changes in the controller design as 4 new commands were added to the already existing command map.

### Update for Assignment 6 
A new controller ControllerGUI was added using the General Commands Callbacks design. A new controller was chosen to facilitate the working of GUI components. No change was made to the original controller as it still supports script-based commands. GUI controller implements the GUI Features and acts as a bridge between the JFrameView and the model. 

The JFrameView component's addActionListener is calling the required methods of GUI features. Since ControllerGUI is implementing these features it will then call the required operation in the model and the final output will be displayed to the user.
 
 
## View
The view helps in rendering the operations and displaying them to the user. The view informs the user whether the operation has been successfully completed or not. An interface Iview is created and View implements the interface to show the user menu, success messages, and error messages.

### Update for Assignment 5
There were no significant changes in the view as the new operations don't require any new view component.

### Updates for Assignment 6
A new GUIView interface is designed to get an interactive Graphical user interface-based system for this application. The interface is implemented using JFrameView concrete class.
The design, look, and feel of the GUI are present in the constructor of JFrameView class. Java Swing is used to design the components of the interface. 

Various operations such as flipping, burring, sharpening, converting to grayscale, converting to sepia, correcting colors, and adjusting levels are provided using components in the JFrameView. The user can perform these operations and check the preview of the image after these operations. The user can also preview the split view of the image so that the image before operation and after operation are compared at the same time. The histogram of the current Image is also displayed in the bottom left corner. 





 

——————————————————————————————————————————————————
 
How to run the application?

The user has to run the main method in the ActivateProgram class that will start the application.

Using the JAR File

The user can also run the application using the JAR file. The JAR file is located in the res/ directory. IME.jar.


```aidl
java -jar IME.jar

will open up the GUI 
(Graphical User Interface for the program by default)
```

An example image of the GUI is in the res/ directory, gui.PNG.

If you add arguments to that you can run the program in text mode or run a script file.

Running a script
The user can directly run the script using the commandline or passed in as an argument when running the program.

To pass in a script file as an argument use the argument:-

```aidl
-file scriptFile.txt
```

There is a provided script file that you can utilize to showcase the functionality of the program. The script takes a picture of a Rubiks Cube and performs various functions on it.

The Rubik's Cube image is provided by Creazilla:
Rubik’s Cube. Creazilla, https://creazilla.com/nodes/64958-rubik-s-cube-clipart. Accessed 1 Nov. 2023.

This script is located in the res/ directory. script.txt and the file paths are relative to that directory.

In order to run the script, after running the program... type:

```
run res/script.txt
``` 

The resulting images will be saved with a premade filepath in the res/ directory.

Another way to run the application is to run the program using the given JAR file.
The JAR file is located in the res/ directory. IME.jar.

You can use the jar file to test the script by using:

```aidl
java -jar IME.jar -file script.txt
```
If you would like to use the text based interface, you can run the jar file with the argument -text.

```aidl
java -jar IME.jar -text
```

Please see the USEME.md file for more information on how to use specific commands.

——————————————————————————————————————————————————

Program Status:

The program is fully functional and all the requirements have been met.

——————————————————————————————————————————————————
