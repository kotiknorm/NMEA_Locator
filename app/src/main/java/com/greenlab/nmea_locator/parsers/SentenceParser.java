package com.greenlab.nmea_locator.parsers;

interface SentenceParser {

   boolean parse(String[] nmea, GPSPosition gpsPosition);
}
