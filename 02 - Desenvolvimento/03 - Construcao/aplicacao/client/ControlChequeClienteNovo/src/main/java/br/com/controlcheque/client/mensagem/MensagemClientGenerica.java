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
public class MensagemClientGenerica {
//MENSAGENS GENERICAS

    public static String MENSAGEM_CAMPO_NOME_OBRIGATORIO = "CAMPO NOME OBRIGATÓRIO.";
    public static String MENSAGEM_CAMPO_TELEFONE_OBRIGATORIO = "CAMPO TELEFONE OBRIGATÓRIO.";
    public static String MENSAGEM_CAMPO_DESCRICAO_OBRIGATORIO = "CAMPO DESCRIÇÃO OBRIGATÓRIO.";
    public static String MENSAGEM_CAMPO_CODIGO_OBRIGATORIO = "CAMPO CÓDIGO OBRIGATÓRIO.";
    public static String MENSAGEM_ERRO_CONEXAO_BANCO = "ERRO AO CONECTAR AO SERVER. ENTRE EM CONTATO COM O SUPORTE TÉCNICO.";
    public static String MENSAGEM_CADASTRO_SUCESSO = "CADASTRO FEITO COM SUCESSO";
    public static String MENSAGEM_ALTERACAO_SUCESSO = "ALTERAÇÃO FEITA COM SUCESSO";
    public static String MENSAGE_ERRO_INICIALIZAR_TELA = "ERRO AO INICIALIZAR TELA. ENTRE EM CONTATO COM O SUPORTE TÉCNICO";

    //PEGO OS CODIGOS DOS ERROS DAS TRANSACOES E DO SERVER E TRATA NO CLIENT
    public static String getMensagemErro(String codigoErro) {
        if (codigoErro.equals(ClientConstantes.CODIGO_ERRO_CODIGO_OBRIGATORIO)) {
            return MENSAGEM_CAMPO_CODIGO_OBRIGATORIO;
        }

        if (codigoErro.equals(ClientConstantes.CODIGO_ERRO_NOME_OBRIGATORIO)) {
            return MENSAGEM_CAMPO_NOME_OBRIGATORIO;
        }

        if (codigoErro.equals(ClientConstantes.CODIGO_ERRO_TELEFONE_OBRIGATORIO)) {
            return MENSAGEM_CAMPO_TELEFONE_OBRIGATORIO;
        }

        if (codigoErro.equals(ClientConstantes.CODIGO_ERRO_DESCRICAO_OBRIGATORIO)) {
            return MENSAGEM_CAMPO_DESCRICAO_OBRIGATORIO;
        }

        if (codigoErro.equals(ClientConstantes.CODIGO_ERRO_CONEXAO_BANCO)) {
            return MENSAGEM_ERRO_CONEXAO_BANCO;
        }

        return "ERRO NÃO ENCONTRADO- ANALISAR =" + codigoErro;
    }
}
