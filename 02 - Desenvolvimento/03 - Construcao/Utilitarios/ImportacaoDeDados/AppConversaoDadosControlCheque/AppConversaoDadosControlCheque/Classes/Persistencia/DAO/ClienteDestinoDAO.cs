using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Collections;
using AppConversaoDadosControlCheque.Classes.Persistencia.Conexao;
using AppConversaoDadosControlCheque.Classes.Negocio;
using MySql.Data.MySqlClient;

namespace AppConversaoDadosControlCheque.Classes.Persistencia.DAO
{
    class ClienteDestinoDAO
    {
        //COMANDOS SQL USADOS        

        private string sql_Insert_Cliente =
           "INSERT INTO tb_Cliente " +
           "(idCliente,codigoCliente,nomeCliente,telefoneCliente,ruaCliente,bairroCliente,cidadeCliente" +
           ",complementoCliente,numeroCliente) " +
           "VALUES " +
           "('{0}','{1}','{2}','{3}','{4}','{5}','{6}','{7}','{8}')";

        private string sql_Deleta_Cliente =
            "DELETE FROM tb_Cliente";


        //METODO PARA INSERIR UM Cliente
        public bool Inserir_Cliente(IList lstClientes)
        {
            string insert_Cliente = string.Empty;
            try
            {                
                if (ConexaoBD.CriarConexao_Destino())
                {
                    foreach (Cliente_Destino cliente in lstClientes)
                    {
                        //INSERINDO NA TABELA CLIENTE                        
                            insert_Cliente = string.Format(sql_Insert_Cliente, cliente.IdObjeto, cliente.CodigoCliente, 
                            cliente.NomeCliente,cliente.TelefoneCliente,cliente.RuaCliente,cliente.BairroCliente,cliente.CidadeCliente,
                            cliente.ComplementoCliente,cliente.NumeroCliente);
                        MySqlCommand cmd_Insert_Cliente = new MySqlCommand(insert_Cliente, ConexaoBD.getConexao_Destino());
                        cmd_Insert_Cliente.ExecuteNonQuery();
                        cmd_Insert_Cliente.Dispose();
                    }

                }
            }
            catch (Exception ex)
            {
                //implementar update caso dê erro
                throw new Exception("SQL ERRO: " + insert_Cliente);
            }
            finally
            {
                ConexaoBD.fechaConexao_Destino();
            }
            return true;
        }

        public bool Deletar_Cliente()
        {
            try
            {
                if (ConexaoBD.CriarConexao_Destino())
                {

                    MySqlCommand cmd_Deleta_Cliente = new MySqlCommand(sql_Deleta_Cliente, ConexaoBD.getConexao_Destino());
                    cmd_Deleta_Cliente.ExecuteNonQuery();
                    cmd_Deleta_Cliente.Dispose();
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
    }
}
