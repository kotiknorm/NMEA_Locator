package com.greenlab.nmea_locator.parsers;

class GPGLL implements SentenceParser {

    public boolean parse(String[] nmea, GPSPosition gpsPosition) {
        gpsPosition.lat = NMEAParser.Latitude2Decimal(nmea[1], nmea[2]);
        gpsPosition.lon = NMEAParser.Longitude2Decimal(nmea[3], nmea[4]);
        gpsPosition.LatNS = nmea[2];
        gpsPosition.LonEW = nmea[4];
        gpsPosition.time = nmea[5];
        return true;
    }
}
