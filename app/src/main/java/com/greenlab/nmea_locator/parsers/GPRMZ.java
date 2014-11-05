package com.greenlab.nmea_locator.parsers;

class GPRMZ implements SentenceParser {

   public boolean parse(String[] nmea, GPSPosition gpsPosition) {
      gpsPosition.altitude = Float.parseFloat(nmea[1]);
      return true;
   }
}
