using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Data.Odbc;
using MySql.Data.MySqlClient;

namespace AppConversaoDadosControlCheque.Classes.Persistencia.Conexao
{
    public class ConexaoBD
    {
        private static MySqlConnection conexao_Destino;
        private static OdbcConnection conexao_Origem;
        private static MySqlTransaction transacao;
        //private static String string_Conexao;

        //Conexao Banco Destino SqlServer
        public static Boolean CriarConexao_Destino()
        {

            Boolean retorno = true;
            try
            {
                if (conexao_Destino == null)
                {
                    string strConexao = AppConversaoDadosControlCheque.Properties.Settings.Default.Conexao_Banco_Mysql;
                    conexao_Destino = new MySqlConnection(strConexao);
                }
                conexao_Destino.Open();
            }
            catch (Exception ex)
            {
                retorno = false;
                conexao_Destino = null;

                //log
                /*LogErros erro = new LogErros();
                erro.Mensagem_Erro = ex.Message;
                erro.Mensagem_Descricao = Mensagem_Erro.ERRO_CRIAR_CONEXAO + " BANCO DESTINO ";
                erro.Hora_Erro = DateTime.Now.ToString();
                erro.GerarLog();*/
            }

            return retorno;
        }

        //Conexao Banco Origem Acess
        public static Boolean CriarConexao_Origem()
        {

            Boolean retorno = true;
            try
            {
                if (conexao_Origem == null)
                {
                    string strConexao = AppConversaoDadosControlCheque.Properties.Settings.Default.Conexao_Banco_Paradox;
                    conexao_Origem = new OdbcConnection(strConexao);                    
                }
                conexao_Origem.Open();
            }
            catch (Exception ex)
            {
                retorno = false;
                conexao_Origem = null;

                //log
                /*LogErros erro = new LogErros();
                erro.Mensagem_Erro = ex.Message;
                erro.Mensagem_Descricao = Mensagem_Erro.ERRO_CRIAR_CONEXAO + " BANCO ORIGEM";
                erro.Hora_Erro = DateTime.Now.ToString();
                erro.GerarLog();*/
            }

            return retorno;
        }

        public static void fechaConexao_Destino()
        {
            if (conexao_Destino.State.Equals(System.Data.ConnectionState.Open))                
                conexao_Destino.Close();
            conexao_Destino = null;
        }

        public static void fechaConexao_Origem()
        {
            if (conexao_Origem.State.Equals(System.Data.ConnectionState.Open))
                conexao_Origem.Close();
            conexao_Origem = null;
        }

        public static MySqlConnection getConexao_Destino()
        {
            return conexao_Destino;
        }

        public static OdbcConnection getConexao_Origem()
        {
            return conexao_Origem;
        }

        /*public static bool CriaTransacao()
        {
            bool retorno = true;
            try
            {
                if (transacao != null)
                    transacao = null;
            }
            finally
            {
                try
                {
                    transacao = conexao_Destino.BeginTransaction(System.Data.IsolationLevel.ReadCommitted);
                }
                catch (Exception ex)
                {
                    retorno = false;
                    LogErros erro = new LogErros();
                    erro.Mensagem_Erro = ex.Message;
                    erro.Mensagem_Descricao = Mensagem_Erro.ERRO_CRIAR_TRANSACAO;
                    erro.Hora_Erro = DateTime.Now.ToString();
                    erro.GerarLog();
                }
            }
            return retorno;
        }*/

        public static MySqlTransaction getTransacao()
        {
            return transacao;
        }            
    }
}
