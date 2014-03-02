package com.modestmaps.providers;

import android.content.Context;
import android.graphics.Bitmap;
import processing.core.*;
import com.modestmaps.core.*;
import com.modestmaps.geo.*;
import com.modestmaps.mapextends.MapPApplet;
import com.modestmaps.providers.connection.TileBaseHelper;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OfflineMapProvider extends AbstractMapProvider {

  //public String[] subdomains = new String[] { "", "a.", "b.", "c." };]glxx
  
  private TileBaseHelper tileDBHelper;
  

  public OfflineMapProvider(PApplet context, String serverADD) {
    super(new MercatorProjection(26, new Transformation(1.068070779e7f, 0.0f, 3.355443185e7f, 0.0f, -1.068070890e7f, 3.355443057e7f)));
    System.out.println("OfflineMapProvider now instantiating TileBaseHelper..");
    System.out.println("passing context: " + context);
    tileDBHelper = new TileBaseHelper(context, serverADD);
    //TODO..eliminate the following block:
//      try {
//          tileDBHelper.createDatabase();
//      } catch (IOException e) {
//          System.out.println("error loading database..check if connected to internet, or check if local file exists..!!");
//      }
    System.out.println("TileBaseHelper instantiated by OfflineMapProvider..now excecuting tileDBHelper.openDatabase()..");
    tileDBHelper.openDatabase();
    System.out.println("tileDBHelper.openDatabase() excecuted");
  }

  public int tileWidth() {
    return 256;
  }

  public int tileHeight() {
    return 256;
  }

  public String[] getTileUrls(Coordinate coordinate) {
    //String img = (int)coordinate.zoom + "/" + (int)coordinate.column + "/" + (int)coordinate.row + ".png";
    String coordQ = "zoom_level = " + (int)coordinate.zoom + " AND tile_column = " + (int)coordinate.column + " AND tile_row = " + (int) (PApplet.pow(2, (int) coordinate.zoom) - coordinate.row - 1);
    
    
    System.out.println("coordQ = "+coordQ);
    //String url = "http://" + subdomains[(int)random(0, 4)] + "tile.openstreetmap.org/" + img;
    
    String url = tileDBHelper.getTileQ(coordQ);
    System.out.println("tileQ for coordQ: " + url);
    //tileDBHelper.closeDatabase();
    return new String[] { url };
  }
  
  public Bitmap getTile(String tileQ){
      
      //tileDBHelper.openDatabase();
      System.out.println("tileQ for getTile: "+ tileQ);
      Bitmap bmg = tileDBHelper.getTile(tileQ);
      //tileDBHelper.closeDatabase();
      
      return bmg;
      
  }

}

