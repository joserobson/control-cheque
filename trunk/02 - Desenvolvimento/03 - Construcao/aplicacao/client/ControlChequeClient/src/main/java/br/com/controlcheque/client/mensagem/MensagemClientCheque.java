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
public class MensagemClientCheque {

    public static String MENSAGEM_ERRO_GERAR_CODIGO_CHEQUE = "ERRO AO GERAR CÓDIGO DO CHEQUE. ENTRE EM CONTATO COM O SUPORTE TÉCNICO.";
    public static String MENSAGEM_ERRO_SALVAR_CHEQUE = "ERRO AO SALVAR CHEQUE.";
    public static String MENSAGEM_CADASTRO_SUCESSO_CHEQUE = "CHEQUE CADASTRADO COM SUCESSO.";
    public static String MENSAGEM_ALTERAR_SUCESSO_CHEQUE = "CHEQUE ALTERADO COM SUCESSO.";
    public static String MENSAGEM_ERRO_CONSULTA_CHEQUES = "ERRO AO CONSULTA CHEQUES. ENTRE EM CONTATO COM O SUPORTE TÉCNICO.";
    public static String MENSAGEM_DATA_BOMPARA_INVALIDA="CAMPO DATA BOMPARA INVÁLIDA.";
    public static String MENSAGEM_DATA_EMISSAO_INVALIDA="CAMPO DATA EMISSÃO INVÁLIDA.";

    public static String getMensagemErro(String codigoErro) {
        if (codigoErro.equals(ClientConstantes.CODIGO_ERRO_CHEQUE_GERAR_CODIGO_SEQUENCIAL)) {
            return MENSAGEM_ERRO_GERAR_CODIGO_CHEQUE;
        }

        if (codigoErro.equals(ClientConstantes.CODIGO_ERRO_SALVAR_CHEQUE)) {
            return MENSAGEM_ERRO_SALVAR_CHEQUE;
        }

        if (codigoErro.equals(ClientConstantes.CODIGO_ERRO_BUSCAR_TODOS_CLIENTE)) {
            return MENSAGEM_ERRO_CONSULTA_CHEQUES;
        }
        
        if (codigoErro.equals(ClientConstantes.CODIGO_ERRO_DATA_BOMPARA_INVALIDA))
        {
            return MENSAGEM_DATA_BOMPARA_INVALIDA;
        }
        
        if (codigoErro.equals(ClientConstantes.CODIGO_ERRO_DATA_EMISSAO_INVALIDA))
        {
            return MENSAGEM_DATA_EMISSAO_INVALIDA;
        }
        return "ERRO NÃO ENCONTRADO - ANALISAR =" + codigoErro;
    }
}
