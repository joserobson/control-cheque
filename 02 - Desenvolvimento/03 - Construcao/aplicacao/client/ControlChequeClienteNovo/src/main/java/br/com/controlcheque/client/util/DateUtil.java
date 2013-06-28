/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.controlcheque.client.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author Robson
 */
public class DateUtil {

    public static XMLGregorianCalendar formatFromString(String formatar) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date data = format.parse(formatar);
            return dateAsXMLGregorianCalendar(data);
        } catch (ParseException ex) {
            Logger.getLogger(MetodosUtil.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "ERRO AO CONVERTER DATA", "Atenção", JOptionPane.ERROR_MESSAGE);

        }

        return null;
    }

    // Converts a java.util.Date into an instance of XMLGregorianCalendar
    public static XMLGregorianCalendar dateAsXMLGregorianCalendar(java.util.Date date) {
        if (date == null) {
            return null;
        } else {
            DatatypeFactory df = null;
            try {
                df = DatatypeFactory.newInstance();
            } catch (DatatypeConfigurationException ex) {
                Logger.getLogger(MetodosUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTimeInMillis(date.getTime());
            return df.newXMLGregorianCalendar(gc);
        }
    }

    /**
     * Converts an XMLGregorianCalendar to an instance of java.util.Date
     *
     * @param xgc Instance of XMLGregorianCalendar or a null reference
     * @return java.util.Date instance whose value is based upon the
     *  value in the xgc parameter. If the xgc parameter is null then
     *  this method will simply return null.
     */
    public static java.util.Date asDate(XMLGregorianCalendar xgc) {
        if (xgc == null) {
            return null;
        } else {
            return xgc.toGregorianCalendar().getTime();
        }
    }

    /**
     * Converter Gregoriam to String
     * @param xgc
     * @return 
     */
    public static String asString(XMLGregorianCalendar xgc) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date date = asDate(xgc);
        return format.format(date);
    }
}
