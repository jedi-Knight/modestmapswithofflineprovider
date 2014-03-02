/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.modestmaps.providers;

/**
 *
 * @author jedi-Knight
 */
import android.content.Context;
import android.graphics.Bitmap;
import processing.core.*;
import com.modestmaps.core.*;
import com.modestmaps.geo.*;
import com.modestmaps.mapextends.MapPApplet;
import com.modestmaps.providers.connection.TileBaseHelper;

public class MaperitiveMBTilesProvider extends AbstractMapProvider {

  //public String[] subdomains = new String[] { "", "a.", "b.", "c." };]glxx
  
  private TileBaseHelper tileDBHelper;
  

  public MaperitiveMBTilesProvider(PApplet context, String serverADD, String dbPath) {
    super(new MercatorProjection(26, new Transformation(1.068070779e7f, 0.0f, 3.355443185e7f, 0.0f, -1.068070890e7f, 3.355443057e7f)));
    System.out.println("OfflineMapProvider now instantiating TileBaseHelper..");
    System.out.println("passing context: " + context);
    tileDBHelper = new TileBaseHelper(context, serverADD);
    tileDBHelper.setDBPathAs(dbPath);
    tileDBHelper.setTileTableAs("tiles");
    tileDBHelper.setTileColumnAs("tile_data");
    System.out.println("TileBaseHelper instantiated by MaperitiveMBTilesProvider with tableName as 'tiles' and columnName as 'tile_data'..now excecuting tileDBHelper.openDatabase()..");
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
    String url = "zoom_level = " + (int)coordinate.zoom + " AND tile_column = " + (int)coordinate.column + " AND tile_row = " + (int) (PApplet.pow(2, (int) coordinate.zoom) - coordinate.row - 1);
    
    
    System.out.println("tileQ = "+url);
    //String url = "http://" + subdomains[(int)random(0, 4)] + "tile.openstreetmap.org/" + img;
    
    //String url = tileDBHelper.getTileQ(coordQ);
    System.out.println("tileQ for coordQ: " + url);
    //tileDBHelper.closeDatabase();
    return new String[] { url };
  }
  
  public Bitmap getTile(String tileQ) throws OutOfMemoryError{
      
      //tileDBHelper.openDatabase();
      System.out.println("tileQ for getTile: "+ tileQ);
      //Bitmap bmg = null;
//      try{
          return tileDBHelper.getTile(tileQ);
//      }catch(Exception e){
//          return null;
//      }
      //tileDBHelper.closeDatabase();
      
      
  }
  
  
  //TODO: experimental code:
  @Override
  public TileBaseHelper getTileDBHelper(){
      return this.tileDBHelper;
  }

}


