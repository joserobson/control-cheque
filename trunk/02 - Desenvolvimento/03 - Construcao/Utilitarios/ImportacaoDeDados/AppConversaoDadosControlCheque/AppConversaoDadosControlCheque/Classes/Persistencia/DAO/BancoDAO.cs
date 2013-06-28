using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using AppConversaoDadosControlCheque.Classes.Persistencia.Conexao;
using MySql.Data.MySqlClient;
using AppConversaoDadosControlCheque.Classes.Negocio;
using System.Collections;
using System.Data.Odbc;

namespace AppConversaoDadosControlCheque.Classes.Persistencia.DAO
{
    class BancoDAO
    {
        //COMANDOS SQL USADOS        

        private string sql_Insert_Banco =
           "INSERT INTO tb_Banco " +
           "(idBanco,codigoBanco" +
           ",descricaoBanco) " +
           "VALUES " +
           "('{0}','{1}','{2}')";

        private string sql_Update_Banco =
            "Update tb_Banco " +
            "set codigoBanco = '{0}' " +
            "descricaoBanco = '{1}' " +
            "where idBanco = '{3}' ";

        private string sql_Deleta_Banco =
            "DELETE FROM tb_banco";

        private string sql_consulta_banco =
            "Select idBanco from tb_Banco Where codigoBanco = '{0}'";

        //METODO PARA INSERIR UM BANCO
        public bool Inserir_Banco(IList lstBancos)
        {
            try
            {
                if (ConexaoBD.CriarConexao_Destino())
                {
                    foreach (Banco_Destino banco in lstBancos)
                    {
                        //INSERINDO NA TABELA CLIENTE                        
                        string insert_Banco = string.Format(sql_Insert_Banco, banco.IdObjeto, banco.CodigoBanco, banco.DescricaoBanco);
                        MySqlCommand cmd_Insert_Banco = new MySqlCommand(insert_Banco, ConexaoBD.getConexao_Destino());
                        cmd_Insert_Banco.ExecuteNonQuery();
                        cmd_Insert_Banco.Dispose();
                    }

                }
            }
            catch (Exception ex)
            {               
                //implementar update caso dê erro
                throw ex;
            }
            finally
            {
                ConexaoBD.fechaConexao_Destino();
            }
            return true;
        }

        public bool Deletar_Banco()
        {
            try
            {
                if (ConexaoBD.CriarConexao_Destino())
                {

                    MySqlCommand cmd_Deleta_Banco = new MySqlCommand(sql_Deleta_Banco, ConexaoBD.getConexao_Destino());
                    cmd_Deleta_Banco.ExecuteNonQuery();
                    cmd_Deleta_Banco.Dispose();
                }
            }
            catch (Exception ex)
            {
                //implementar update caso dê erro
                throw ex;
            }
            finally
            {
                ConexaoBD.fechaConexao_Destino();
            }
            return true;
        }

        public string consultaIdBanco(string codBanco)
        {
            try
            {
                 if (ConexaoBD.CriarConexao_Destino())
                 {
                    string consulta_Banco = String.Format(sql_consulta_banco, codBanco);
                    MySqlCommand cmd_Consulta_Banco = new MySqlCommand(consulta_Banco, ConexaoBD.getConexao_Destino());
                    string retorno = (string)cmd_Consulta_Banco.ExecuteScalar();
                    cmd_Consulta_Banco.Dispose();
                    return retorno;
                }
            }
            catch (Exception ex)
            {
                return null;
                throw ex;
            }
            finally
            {
                ConexaoBD.fechaConexao_Destino();
            }
            return null;
        }
    }
}
