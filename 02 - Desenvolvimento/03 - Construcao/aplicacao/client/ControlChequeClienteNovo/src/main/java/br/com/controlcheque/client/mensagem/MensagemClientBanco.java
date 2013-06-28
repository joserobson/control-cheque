/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.controlcheque.client.mensagem;

import br.com.controlcheque.client.util.ClientConstantes;

/**
 *
 * @author Robson
 */
public class MensagemClientBanco {

    //MENSAGENS PARA BANCO
    public static String MENSAGEM_ERRO_SALVAR_BANCO = "ERRO AO SALVAR BANCO.";
    public static String MENSAGEM_CADASTRO_SUCESSO_BANCO="BANCO CADASTRADO COM SUCESSO.";
    public static String MENSAGEM_ALTERAR_SUCESSO_BANCO="BANCO ALTERADO COM SUCESSO.";
    public static String MENSAGEM_ERRO_CONSULTA_BANCOS="ERRO AO CONSULTA BANCOS. ENTRE EM CONTATO COM O SUPORTE TÉCNICO.";
    public static String MENSAGEM_ERRO_DELETAR_BANCO = "ERRO AO DELETAR BANCO.";
    public static String MENSAGEM_AVISO_BANCO_NAO_PODE_REMOVIDO = "BANCO NÃO PODE SER REMOVIDO.BANCO ASSOCIADO A UM CHEQUE.";

    public static String getMensagemErro(String codigoErro)
    {        
        if (codigoErro.equals(ClientConstantes.CODIGO_ERRO_SALVAR_BANCO))
            return MENSAGEM_ERRO_SALVAR_BANCO;

        if (codigoErro.equals(ClientConstantes.CODIGO_ERRO_BUSCAR_TODOS_BANCO))
            return MENSAGEM_ERRO_CONSULTA_BANCOS;

        if (codigoErro.equals(ClientConstantes.CODIGO_ERRO_DELETAR_BANCO))
            return MENSAGEM_ERRO_DELETAR_BANCO;

        if (codigoErro.equals(ClientConstantes.CODIGO_MENSAGEM_OBJETO_NAO_PODE_SER_REMOVIDO))
            return MENSAGEM_AVISO_BANCO_NAO_PODE_REMOVIDO;

        return "ERRO NÃO ENCONTRADO- ANALISAR = " + codigoErro;
    }
}
