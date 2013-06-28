/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.controlcheque.servico.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jose Robson
 */
public class ServiceUtil {

    private Properties getProperties() {
        Properties props = null;
        InputStream in = getClass().getClassLoader().getResourceAsStream("serviceProperties.properties");
        try {
            if (in != null) {
                props = new Properties();
                props.load(in);
                in.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return props;
    }

    /**
     * Funcao para gerar backup do sistema
     *
     * @throws IOException
     */
    public void gerarBackup() throws IOException {
        try {
            String caminhoBat = getProperties().getProperty("caminho.bat.gerar.backup").trim();
            Runtime.getRuntime().exec(caminhoBat);
        } catch (IOException ex) {
            Logger.getLogger(ServiceUtil.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }
}
