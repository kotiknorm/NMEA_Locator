package com.greenlab.nmea_locator.parsers;

class GPVTG implements SentenceParser {

   public boolean parse(String[] nmea, GPSPosition gpsPosition) {
      gpsPosition.dir = Float.parseFloat(nmea[3]);
      return true;
   }
}
