package com.greenlab.nmea_locator.parsers;

class GPRMC implements SentenceParser {

   public boolean parse(String[] nmea, GPSPosition gpsPosition) {
      gpsPosition.time = nmea[1];
      gpsPosition.lat = NMEAParser.Latitude2Decimal(nmea[3], nmea[4]);
      gpsPosition.lon = NMEAParser.Longitude2Decimal(nmea[5], nmea[6]);
      gpsPosition.LatNS = nmea[4];
      gpsPosition.LonEW = nmea[6];
      float var3;
      if(nmea[7].length() == 0) {
         var3 = 0.0F;
      } else {
         var3 = Float.parseFloat(nmea[7]);
      }

      gpsPosition.speed = var3;
      gpsPosition.speed = (float)(1.852D * (double)gpsPosition.speed);
      int var4 = nmea[8].length();
      float var5 = 0.0F;
      if(var4 != 0) {
         var5 = Float.parseFloat(nmea[8]);
      }

      gpsPosition.dir = var5;
      return true;
   }
}
