package br.com.controlcheque.client;

import br.com.controlcheque.client.telas.TelaPrincipalForm;
import br.com.controlcheque.client.util.LogUtil;

/**
 *
 */
public class App {

    public static TelaPrincipalForm telaPrincipal = null;
    private static final int RUN_PORT = 8666;

    public static void main(String[] args) {
        try {
            LogUtil.logInformacao("INICIALIZANDO PROGRAMA CONTROL CHEQUE");

            java.net.ServerSocket ss = new java.net.ServerSocket(RUN_PORT);

            if (telaPrincipal == null) {
                telaPrincipal = new TelaPrincipalForm();
            }
            telaPrincipal.setVisible(true);
        } catch (java.net.BindException ex) {
            LogUtil.logDescricaoErro(App.class, ex);
            System.exit(1);
        } catch (java.io.IOException ex) {
            ex.printStackTrace();
            System.exit(1);
        }
    }
}
