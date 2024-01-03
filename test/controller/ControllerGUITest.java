package controller;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ControllerGUITest{
  private MockModel m;
  private MockViewGUI v;
  private MockFeatures f;

  @Before
  public void setUp() {
    m = new MockModel();
    v = new MockViewGUI();

  }

  @Test
  public void testShowImage(){
    GUIFeaturesInterface c = new ControllerGUI(m);
    c.setView(v);
    c.showImage();
    assertEquals("getImage called with name: currImage\n" +
            "addImage called with name: splitImage image: \n" +
            "getImage called with name: currImage\n" +
            "histogram called with name: currImage destImageName: histImage\n" +
            "getImage called with name: histImage\n",m.getLog().toString());
  }

  //testing split view
  @Test
  public void testShowSplit(){
    GUIFeaturesInterface c = new ControllerGUI(m);
    c.setView(v);
    c.showSplit();
    assertEquals("getImage called with name: splitView\n" +
            "histogram called with name: splitImage destImageName: splitHistImage\n" +
            "getImage called with name: splitHistImage\n",m.getLog().toString());
  }

  @Test
  public void testLoadImage(){
    GUIFeaturesInterface c = new ControllerGUI(m);
    c.setView(v);
    c.loadImage("test/controller/MockImage.png");
    assertEquals("addImage called with name: currImage image: \n" +
            "getImage called with name: currImage\n" +
            "histogram called with name: currImage destImageName: histImage\n" +
            "getImage called with name: histImage\n"+
            "addImage called with name: splitImage image: \n",m.getLog().toString());
  }

@Test
public void testSepia(){
  GUIFeaturesInterface c = new ControllerGUI(m);
  c.setView(v);
  c.sepiaOperation();
  assertEquals("sepiaImage called with name: currImage destImageName: currImage\n" +
          "getImage called with name: currImage\n" +
          "addImage called with name: splitImage image: \n" +
          "getImage called with name: currImage\n" +
          "histogram called with name: currImage destImageName: histImage\n" +
          "getImage called with name: histImage\n",m.getLog().toString());
}

@Test
public void testRedComponent(){
  GUIFeaturesInterface c = new ControllerGUI(m);
  c.setView(v);
  c.redComponentOperation();
  assertEquals("isGrayScale called with s: currImage\n"+
          "redComponent called with name: currImage destImageName: currImage\n" +
          "getImage called with name: currImage\n" +
          "addImage called with name: splitImage image: \n" +
          "getImage called with name: currImage\n" +
          "histogram called with name: currImage destImageName: histImage\n" +
          "getImage called with name: histImage\n",m.getLog().toString());

  }

  @Test
  public void testBlueComponent(){
    GUIFeaturesInterface c = new ControllerGUI(m);
    c.setView(v);
    c.blueComponentOperation();
    assertEquals("isGrayScale called with s: currImage\n"+
            "blueComponent called with name: currImage destImageName: currImage\n" +
            "getImage called with name: currImage\n" +
            "addImage called with name: splitImage image: \n" +
            "getImage called with name: currImage\n" +
            "histogram called with name: currImage destImageName: histImage\n" +
            "getImage called with name: histImage\n",m.getLog().toString());

  }

  @Test
  public void testGreenComponent(){
    GUIFeaturesInterface c = new ControllerGUI(m);
    c.setView(v);
    c.greenComponentOperation();
    assertEquals("isGrayScale called with s: currImage\n"+
            "greenComponent called with name: currImage destImageName: currImage\n" +
            "getImage called with name: currImage\n" +
            "addImage called with name: splitImage image: \n" +
            "getImage called with name: currImage\n" +
            "histogram called with name: currImage destImageName: histImage\n" +
            "getImage called with name: histImage\n",m.getLog().toString());
  }

@Test
public void testGrayScale(){
  GUIFeaturesInterface c = new ControllerGUI(m);
  c.setView(v);
  c.greyScaleOperation();
  assertEquals("isGrayScale called with s: currImage\n"+
          "calculateLuma called with name: currImage destImageName: currImage\n" +
          "getImage called with name: currImage\n" +
          "addImage called with name: splitImage image: \n" +
          "getImage called with name: currImage\n" +
          "histogram called with name: currImage destImageName: histImage\n" +
          "getImage called with name: histImage\n",m.getLog().toString());

}
  @Test
  public void testBlur(){
    GUIFeaturesInterface c = new ControllerGUI(m);
    c.setView(v);
    c.blurOperation();
    assertEquals("blurImage called with name: currImage destImageName: currImage\n" +
            "getImage called with name: currImage\n" +
            "addImage called with name: splitImage image: \n" +
            "getImage called with name: currImage\n" +
            "histogram called with name: currImage destImageName: histImage\n" +
            "getImage called with name: histImage\n",m.getLog().toString());
  }
  @Test
  public void testSharpen(){
    GUIFeaturesInterface c = new ControllerGUI(m);
    c.setView(v);
    c.sharpenOperation();
    assertEquals("sharpenImage called with name: currImage destImageName: currImage\n" +
            "getImage called with name: currImage\n" +
            "addImage called with name: splitImage image: \n" +
            "getImage called with name: currImage\n" +
            "histogram called with name: currImage destImageName: histImage\n" +
            "getImage called with name: histImage\n",m.getLog().toString());
  }

  @Test
  public void testHorizontalFlip(){
    GUIFeaturesInterface c = new ControllerGUI(m);
    c.setView(v);
    c.horizontalFlipOperation();
    assertEquals("horizontalFlip called with name: currImage destImageName: currImage\n" +
            "getImage called with name: currImage\n" +
            "addImage called with name: splitImage image: \n" +
            "getImage called with name: currImage\n" +
            "histogram called with name: currImage destImageName: histImage\n" +
            "getImage called with name: histImage\n",m.getLog().toString());
  }

  @Test
  public void testVerticalFlip(){
    GUIFeaturesInterface c = new ControllerGUI(m);
    c.setView(v);
    c.verticalFlipOperation();
    assertEquals("verticalFlip called with name: currImage destImageName: currImage\n" +
            "getImage called with name: currImage\n" +
            "addImage called with name: splitImage image: \n" +
            "getImage called with name: currImage\n" +
            "histogram called with name: currImage destImageName: histImage\n" +
            "getImage called with name: histImage\n",m.getLog().toString());

  }

  @Test
  public void testCompression(){
    GUIFeaturesInterface c = new ControllerGUI(m);
    c.setView(v);
    c.compressionOperation(50);
    assertEquals("compressImage called with name: currImage destImageName: currImage factor: 50\n" +
            "getImage called with name: currImage\n" +
            "addImage called with name: splitImage image: \n" +
            "getImage called with name: currImage\n" +
            "histogram called with name: currImage destImageName: histImage\n" +
            "getImage called with name: histImage\n",m.getLog().toString());
  }

  @Test
  public void testColorCorrection(){
    GUIFeaturesInterface c = new ControllerGUI(m);
    c.setView(v);
    c.colorCorrectionOperation();
    assertEquals("colorCorrect called with name: currImage destImageName: currImage\n" +
            "getImage called with name: currImage\n" +
            "addImage called with name: splitImage image: \n" +
            "getImage called with name: currImage\n" +
            "histogram called with name: currImage destImageName: histImage\n" +
            "getImage called with name: histImage\n",m.getLog().toString());
  }

  @Test
  public void testLevelAdjust(){
    GUIFeaturesInterface c = new ControllerGUI(m);
    c.setView(v);
    c.adjustLevelsOperation(1,2,3);
    assertEquals("levelAdjust called with b: 1 m: 2 w: 3 name: currImage destImageName: currImage\n" +
            "getImage called with name: currImage\n" +
            "addImage called with name: splitImage image: \n" +
            "getImage called with name: currImage\n" +
            "histogram called with name: currImage destImageName: histImage\n" +
            "getImage called with name: histImage\n",m.getLog().toString());
  }

  @Test
  public void testSaveImage(){
    GUIFeaturesInterface c = new ControllerGUI(m);
    c.setView(v);
    c.saveImage("test/controller/MockImage.png");
    assertEquals("getImage called with name: currImage\n",m.getLog().toString());
  }
}