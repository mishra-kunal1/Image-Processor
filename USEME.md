The newest version of the JAR file is located in the res/ directory. IME.jar.

This new version supports a Graphical User Interface.

To use the GUI, run the JAR file with no arguments. An example image of the GUI is in the res/ directory, gui.PNG.

```aidl
java -jar IME.jar
```
The GUI Includes the following features:
- File open and save
- Image Operations
- Image Display
- Image Color Histogram

The Operations supported by the GUI and how to use them are as follows:

Clicking these buttons will do the following:
- "Open a file" ---> Opens a file chooser to select an image file to open.
- "Save a file" ---> Opens a file chooser to select a file to save the image to.
- "Blur" ---> Blurs the image.
- "Sharpen" ---> Sharpens the image.
- "Sepia" ---> Applies a sepia tone to the image.
- "Grayscale" ---> Applies a grayscale to the image.
- "Horizontal Flip" ---> Flips the image horizontally.
- "Vertical Flip" ---> Flips the image vertically.
- "Color Correct" ---> Applies a color correction to the image.

The following operations allow for user input:
- "Component" ---> Allows the user to select a component to extract from the image. By default, this is set to Red Component.
- "Compress" ---> Allows the user to select a compression level to apply to the image. By default, this is set to 50.
- "Level Adjust" ---> Allows the user to select a Black (B) Mid (M) White (W) value to apply to the image. By default, this is set to 0, 128, 255 respectively.
- "Brighten" ---> Allows the user to select a brightness level to apply to the image. By default, this is set to 0.

The following feature is a toggleable feature:
- "Split View" ---> Allows the user to preview an operation with a given percentage of preview. By default, this is set to 50%.
  - To utilize Split view clicking it will activate preview and upon clicking an operation it will show the preview of the image.

```aidl
READ BELOW FOR THE INDIVIDUAL COMMANDS THAT ARE SUPPORTED BY THIS APPLICATION.
```

Commands that are supported by this application.

The user can run these commands individually or can run a script that will run all these commands in a sequence.

Additionally, a script can be run by passing in the script file as an argument when running the program. Arguments:
```aidl
-file scriptFile.txt
```

Commands:
1. load <fileName> <name> -> action = load an image from a file
   Example - load src/images/koala.ppm koala

2. save <destFileName> <name> -> action = save an image to a file
   Example - save src/images/koala.png koala

3. brighten <amount> <sourceName> <destName> -> action = adjust brightness of an image by a positive/negative amount
   Example - brighten 10 koala koala-brighten-ten
   Example - brighten -10 koala koala-brighten-neg-ten
   Condition - positive integer for brightening and negative integer for darkening

4. red-component <sourceName> <destName> -> action = extract the red component of an image
   Example - red-component koala koala-red

5. green-component <sourceName> <destName> -> action = extract the green component of an image
   Example - green-component koala koala-green

6. blue-component <sourceName> <destName> -> action = extract the blue component of an image
   Example - blue-component koala koala-blue

7. value-component <sourceName> <destName> -> action = extract the value component of an image
   Example - value-component koala koala-value

8. luma-component <sourceName> <destName> -> action = extract the luma component of an image
   Example - luma-component koala koala-luma

9. intensity-component <sourceName> <destName> -> action = extract the intensity component of an image
   Example - intensity-component koala koala-intensity

10. horizontal-flip <sourceName> <destName> -> action = flip an image horizontally
    Example - horizontal-flip koala koala-horizontal

11. vertical-flip <sourceName> <destName> -> action = flip an image vertically
    Example - vertical-flip koala koala-vertical

12. blur <sourceName> <destName> -> action = apply a blur effect to an image
    Example - blur koala koala-blur

13. sharpen <sourceName> <destName> -> action = apply a sharpening effect to an image
    Example - sharpen koala koala-sharpen

14. sepia <sourceName> <destName> -> action = apply a sepia tone effect to an image
    Example - sepia koala koala-sepia

15. rgb-split <sourceName> <redDestName> <greenDestName> <blueDestName> -> action = split an image into its RGB components
    Example - rgb-split koala koala-red-split koala-green-split koala-blue-split

16. run <scriptFileName> -> action = run a script of commands
    Example - run src/script.txt

17. rgb-combine <destImageName> <redImageName> <greenImageName> <blueImageName> -> action = combine individual RGB components to form an image
    Example - rgb-combine koala-rgb koala-red-split koala-green-split koala-blue-split
 
18. histogram <sourceName> <destName> -> action = create a histogram of color values of an image
    Example - histogram koala koala-histogram

19. color-correct <sourceName> <destName> -> action = color correct an image by aligning the peaks of the histogram
    Example - color-correct koala koala-color-correct

20. level-adjust <value> <value> <value> <name> <destImageName> -> action = adjust the levels of an image by given black,mid,white values respectively
    Example - level-adjust 20 100 200 koala koala-level-adjust
    Condition - values need to be between 0 and 255 and need to be in ascending order. From black, mid, white.

21. compress <percentage> <sourceName> <destName> -> action = compress: compress the image by a given percentage
    Example - compress 50 koala koala-compress
    Condition - percentage needs to be between 0 and 100