using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace AppConversaoDadosControlCheque.Classes.Negocio
{
    class Cliente_Destino
    {
        private string idObjeto;

        public string IdObjeto
        {
            get { return idObjeto; }
            set { idObjeto = value; }
        }

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
        
        private string numeroCliente;
        public string NumeroCliente
        {
            get { return numeroCliente; }
            set { numeroCliente = value; }
        }

        public Cliente_Destino()
        {
            this.idObjeto = Guid.NewGuid().ToString();
            this.codigoCliente = string.Empty;
            this.nomeCliente = string.Empty;
            this.telefoneCliente = string.Empty;
            this.ruaCliente = string.Empty;
            this.bairroCliente = string.Empty;
            this.cidadeCliente = string.Empty;
            this.complementoCliente = string.Empty;
            this.numeroCliente = string.Empty;

        }
    }
}
