using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace AppConversaoDadosControlCheque.Classes.Negocio
{
   public  class Banco_Destino
    {
        private string idObjeto;
        private string codigoBanco;
        private string descricaoBanco;

        public Banco_Destino()
        {
            this.idObjeto = string.Empty;
            this.codigoBanco = string.Empty;
            this.descricaoBanco = string.Empty;
        }

        public string IdObjeto
        {
            get
            {
                return this.idObjeto;
            }
            set
            {
                this.idObjeto = value;
            }
        }

        public string CodigoBanco
        {
            get
            {
                return this.codigoBanco;
            }
            set
            {
                this.codigoBanco = value;
            }
        }

        public string DescricaoBanco
        {
            get
            {
                return this.descricaoBanco;
            }
            set
            {
                this.descricaoBanco = value;
            }
        }
    }
}
