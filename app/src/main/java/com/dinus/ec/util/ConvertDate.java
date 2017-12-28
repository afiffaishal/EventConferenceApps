package com.dinus.ec.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by crocodic-mbp-9 on 5/5/17.
 */

public class ConvertDate {

    public ConvertDate() {
    }

    public String getDateProfile(String strConvert) {
        String save = "";
        if (!strConvert.equalsIgnoreCase("") || !strConvert.isEmpty()) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat formatSave = new SimpleDateFormat("dd MMM yyyy");
            Date dateConvert = null;
            long longConvert = 0;
            try {
                dateConvert = format.parse(strConvert);
                longConvert = dateConvert.getTime();
            } catch (Exception e) {
                e.printStackTrace();
            }
            save = formatSave.format(longConvert);
        }
        return save;
    }

    public String getTimeAgenda(String strConvert) {
        String save = "";
        if (!strConvert.equalsIgnoreCase("") || !strConvert.isEmpty()) {
            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
            SimpleDateFormat formatSave = new SimpleDateFormat("HH.mm");
            Date dateConvert = null;
            long longConvert = 0;
            try {
                dateConvert = format.parse(strConvert);
                longConvert = dateConvert.getTime();
            } catch (Exception e) {
                e.printStackTrace();
            }
            save = formatSave.format(longConvert);
        }
        return save;
    }

    public String getDateSend(String strConvert) {
        String save = "";
        if (!strConvert.equalsIgnoreCase("") || !strConvert.isEmpty()) {
            SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy");
            SimpleDateFormat formatSave = new SimpleDateFormat("yyyy-MM-dd");
            Date dateConvert = null;
            long longConvert = 0;
            try {
                dateConvert = format.parse(strConvert);
                longConvert = dateConvert.getTime();
            } catch (Exception e) {
                e.printStackTrace();
            }
            save = formatSave.format(longConvert);
        }
        return save;
    }

    public String getDate(Long lngConvert) {
        String save = "";
        SimpleDateFormat formatSave = new SimpleDateFormat("HHmm");
        save = formatSave.format(lngConvert);
        return save;
    }

    public String getDateShow(String strConvert) {
        String save = "";
        if (!strConvert.equalsIgnoreCase("") || !strConvert.isEmpty()) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat formatSave = new SimpleDateFormat("dd MMM yyyy, HH:mm");
            Date dateConvert = null;
            long longConvert = 0;
            try {
                dateConvert = format.parse(strConvert);
                longConvert = dateConvert.getTime();
            } catch (Exception e) {
                e.printStackTrace();
            }
            save = formatSave.format(longConvert);
        }
        return save;
    }

    public int getHourServer(String strConvert) {
        int save = 0;
        if (!strConvert.equalsIgnoreCase("") || !strConvert.isEmpty()) {
            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
            SimpleDateFormat formatSave = new SimpleDateFormat("HH");
            Date dateConvert = null;
            long longConvert = 0;
            try {
                dateConvert = format.parse(strConvert);
                longConvert = dateConvert.getTime();
            } catch (Exception e) {
                e.printStackTrace();
            }
            save = Integer.parseInt(formatSave.format(longConvert));
        }
        return save;
    }

    public int getMinServer(String strConvert) {
        int save = 0;
        if (!strConvert.equalsIgnoreCase("") || !strConvert.isEmpty()) {
            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
            SimpleDateFormat formatSave = new SimpleDateFormat("mm");
            Date dateConvert = null;
            long longConvert = 0;
            try {
                dateConvert = format.parse(strConvert);
                longConvert = dateConvert.getTime();
            } catch (Exception e) {
                e.printStackTrace();
            }
            save = Integer.parseInt(formatSave.format(longConvert));
        }
        return save;
    }

    public int getSecondServer(String strConvert) {
        int save = 0;
        if (!strConvert.equalsIgnoreCase("") || !strConvert.isEmpty()) {
            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
            SimpleDateFormat formatSave = new SimpleDateFormat("ss");
            Date dateConvert = null;
            long longConvert = 0;
            try {
                dateConvert = format.parse(strConvert);
                longConvert = dateConvert.getTime();
            } catch (Exception e) {
                e.printStackTrace();
            }
            save = Integer.parseInt(formatSave.format(longConvert));
        }
        return save;
    }

}
