/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.controlcheque.client.util;

/**
 *
 * @author Robson
 */
public class ClientConstantes {

    /*CONSTANTES CLIENT
     * 
     */
    
    public static String CODIGO_ERRO_DATA_BOMPARA_INVALIDA = "CLIENT_001";
    public static String CODIGO_ERRO_DATA_EMISSAO_INVALIDA = "CLIENT_002";
    /*CONSTANTES DO PROJETO SERVER*/
    public static String CODIGO_ERRO_SALVAR_CLIENTE = "SERVER_001";
    public static String CODIGO_ERRO_BUSCAR_TODOS_CLIENTE = "SERVER_002";
    public static String CODIGO_ERRO_CLIENTE_GERAR_CODIGO_SEQUENCIAL = "SERVER_003";
    public static String CODIGO_ERRO_CONEXAO_BANCO = "SERVER_004";
    public static String CODIGO_ERRO_SALVAR_BANCO = "SERVER_005";
    public static String CODIGO_ERRO_BUSCAR_TODOS_BANCO = "SERVER_006";
    public static String CODIGO_ERRO_DELETAR_BANCO = "SERVER_007";
    public static String CODIGO_ERRO_SALVAR_CHEQUE = "SERVER_008";
    public static String CODIGO_ERRO_BUSCAR_TODOS_CHEQUE = "SERVER_009";
    public static String CODIGO_ERRO_DELETAR_CHEQUE = "SERVER_010";
    public static String CODIGO_ERRO_BUSCAR_CLIENTES_POR_BANCO = "SERVER_011";
    public static String CODIGO_ERRO_CHEQUE_GERAR_CODIGO_SEQUENCIAL = "SERVER_012";

    /*CONSTANTES DO PROJETO TRANSACAO*/
    public static String CODIGO_ERRO_CODIGO_OBRIGATORIO = "TRANS_001";
    public static String CODIGO_ERRO_NOME_OBRIGATORIO = "TRANS_002";
    public static String CODIGO_ERRO_TELEFONE_OBRIGATORIO = "TRANS_003";
    public static String CODIGO_ERRO_DESCRICAO_OBRIGATORIO = "TRANS_004";
    public static String CODIGO_MENSAGEM_OBJETO_NAO_PODE_SER_REMOVIDO = "TRANS_005";

    public static String CODIGO_ERRO_BANCO_OBRIGATORIO = "TRANS_CHEQUE_001";
    public static String CODIGO_ERRO_TITULAR_OBRIGATORIO = "TRANS_CHEQUE_002";
    public static String CODIGO_ERRO_VALOR_OBRIGATORIO = "TRANS_CHEQUE_003";
    public static String CODIGO_ERRO_DATA_BOMPARA_OBRIGATORIO = "TRANS_CHEQUE_004";
    public static String CODIGO_ERRO_RECEBIDE_OBRIGATORIO = "TRANS_CHEQUE_005";
}
