using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace AppConversaoDadosControlCheque.Classes.Negocio
{
    class Cliente_Origem
    {
        private string codigoCliente;
        public string CodigoCliente
        {
            get { return codigoCliente; }
            set { codigoCliente = value; }
        }
        
        private string nomeCliente;
        public string NomeCliente
        {
            get { return nomeCliente; }
            set { nomeCliente = value; }
        }
        
        private string telefoneCliente;
        public string TelefoneCliente
        {
            get { return telefoneCliente; }
            set { telefoneCliente = value; }
        }
        
        private string ruaCliente;
        public string RuaCliente
        {
            get { return ruaCliente; }
            set { ruaCliente = value; }
        }
        
        private string bairroCliente;
        public string BairroCliente
        {
            get { return bairroCliente; }
            set { bairroCliente = value; }
        }
        
        private string cidadeCliente;
        public string CidadeCliente
        {
            get { return cidadeCliente; }
            set { cidadeCliente = value; }
        }
        
        private string complementoCliente;
        public string ComplementoCliente
        {
            get { return complementoCliente; }
            set { complementoCliente = value; }
        }
        
        private int numeroCliente;
        public int NumeroCliente
        {
            get { return numeroCliente; }
            set { numeroCliente = value; }
        }

        public Cliente_Origem()
        {
            this.codigoCliente = string.Empty;
            this.nomeCliente = string.Empty;
            this.telefoneCliente = string.Empty;
            this.ruaCliente = string.Empty;
            this.bairroCliente = string.Empty;
            this.cidadeCliente = string.Empty;
            this.complementoCliente = string.Empty;
            this.numeroCliente = 0;

        }
    }
}
