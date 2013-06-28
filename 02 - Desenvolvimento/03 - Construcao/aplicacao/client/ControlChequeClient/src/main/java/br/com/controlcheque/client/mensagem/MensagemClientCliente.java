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
public class MensagemClientCliente {

    //MENSAGENS PARA CLIENTE
    public static String MENSAGEM_ERRO_GERAR_CODIGO_CLIENTE = "ERRO AO GERAR CÓDIGO CLIENTE. ENTRE EM CONTATO COM O SUPORTE TÉCNICO.";
    public static String MENSAGEM_ERRO_SALVAR_CLIENTE = "ERRO AO SALVAR CLIENTE.";
    public static String MENSAGEM_CADASTRO_SUCESSO_CLIENTE = "CLIENTE CADASTRADO COM SUCESSO.";
    public static String MENSAGEM_ALTERAR_SUCESSO_CLIENTE = "CLIENTE ALTERADO COM SUCESSO.";
    public static String MENSAGEM_ERRO_CONSULTA_CLIENTES = "ERRO AO CONSULTA CLIENTES. ENTRE EM CONTATO COM O SUPORTE TÉCNICO.";

    public static String getMensagemErro(String codigoErro) {
        if (codigoErro.equals(ClientConstantes.CODIGO_ERRO_CLIENTE_GERAR_CODIGO_SEQUENCIAL)) {
            return MENSAGEM_ERRO_GERAR_CODIGO_CLIENTE;
        }

        if (codigoErro.equals(ClientConstantes.CODIGO_ERRO_SALVAR_CLIENTE)) {
            return MENSAGEM_ERRO_SALVAR_CLIENTE;
        }

        if (codigoErro.equals(ClientConstantes.CODIGO_ERRO_BUSCAR_TODOS_CLIENTE)) {
            return MENSAGEM_ERRO_CONSULTA_CLIENTES;
        }

        return "ERRO NÃO ENCONTRADO- ANALISAR =" + codigoErro;
    }
}
