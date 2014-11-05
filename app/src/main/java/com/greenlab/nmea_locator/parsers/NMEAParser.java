package com.greenlab.nmea_locator.parsers;


import java.util.HashMap;
import java.util.Map;

public class NMEAParser {

    private final Map<String, SentenceParser> sentenceParsers;
    private GPSPosition position;

    public NMEAParser() {
        sentenceParsers = new HashMap();
        position = new GPSPosition();
        sentenceParsers.put("GPGGA", new GPGGA());
        sentenceParsers.put("GPGGL", new GPGLL());
        sentenceParsers.put("GPRMC", new GPRMC());
        sentenceParsers.put("GPRMZ", new GPRMZ());
        sentenceParsers.put("GPVTG", new GPVTG());
    }

    protected static float Latitude2Decimal(String var0, String var1) {
        int var2 = var0.length();
        float var3 = 0.0F;
        if (var2 > 0) {
            var3 = Float.parseFloat(var0.substring(2)) / 60.0F + Float.parseFloat(var0.substring(0, 2));
        }

        return var3;
    }

    protected static float Longitude2Decimal(String var0, String var1) {
        int var2 = var0.length();
        float var3 = 0.0F;
        if (var2 > 0) {
            var3 = Float.parseFloat(var0.substring(3)) / 60.0F + Float.parseFloat(var0.substring(0, 3));
        }

        return var3;
    }

    public GPSPosition parse(String nmea) {
        if (nmea.startsWith("$")) {
            String[] value = nmea.substring(1).split(",");
            String type = value[0];

            try {
                if (sentenceParsers.containsKey(type)) {
                    sentenceParsers.get(type).parse(value, position);
                }

                position.gpType = type;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return position;
    }
}
