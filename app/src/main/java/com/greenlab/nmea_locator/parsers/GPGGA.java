package com.greenlab.nmea_locator.parsers;

class GPGGA implements SentenceParser {


    public boolean parse(String[] nmea, GPSPosition gpsPosition) {
        gpsPosition.time = nmea[1];
        gpsPosition.lat = NMEAParser.Latitude2Decimal(nmea[2], nmea[3]);
        gpsPosition.LatNS = nmea[3];
        gpsPosition.LonEW = nmea[5];
        gpsPosition.lon = NMEAParser.Longitude2Decimal(nmea[4], nmea[5]);
        int var3;
        if (nmea[6].length() == 0) {
            var3 = 0;
        } else {
            var3 = Integer.parseInt(nmea[6]);
        }

        gpsPosition.quality = var3;
        int var4 = nmea[7].length();
        int var5 = 0;
        if (var4 != 0) {
            var5 = Integer.parseInt(nmea[7]);
        }

        gpsPosition.satellites = var5;
        float var6;
        if (nmea[9].length() == 0) {
            var6 = 0.0F;
        } else {
            var6 = Float.parseFloat(nmea[9]);
        }

        gpsPosition.altitude = var6;
        return true;
    }
}
