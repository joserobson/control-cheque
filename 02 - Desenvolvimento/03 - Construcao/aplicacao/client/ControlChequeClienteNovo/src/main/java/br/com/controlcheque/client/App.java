package br.com.controlcheque.client;

import br.com.controlcheque.client.telas.LoginForm;
import br.com.controlcheque.client.telas.TelaPrincipalForm;
import br.com.controlcheque.client.util.LogUtil;
import br.com.controlcheque.service.Usuario;
import javax.swing.UIManager;

/**
 *
 */
public class App {

    public static TelaPrincipalForm telaPrincipal = null;
    public static Usuario usuarioLogado = null;
    private static final int RUN_PORT = 8666;
    private static String lookAndFeel = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";

    public static void main(String[] args) {
        try {

            LogUtil.logInformacao("INICIALIZANDO PROGRAMA CONTROL CHEQUE");
            aplicarLookAndFeel();
            java.net.ServerSocket ss = new java.net.ServerSocket(RUN_PORT);
            new LoginForm().setVisible(true);

        } catch (Exception ex) {
            LogUtil.logDescricaoErro(App.class, ex);
            System.exit(1);
        }
    }

    //aplica tema referente a versão do windows instalada
    public static void aplicarLookAndFeel() throws Exception {
        try {
            UIManager.setLookAndFeel(lookAndFeel);
        } catch (Exception ex) {
            throw ex;
        }
    }

    public static Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public static void setUsuarioLogado(Usuario usuarioLogado) {
        App.usuarioLogado = usuarioLogado;
    }
}
