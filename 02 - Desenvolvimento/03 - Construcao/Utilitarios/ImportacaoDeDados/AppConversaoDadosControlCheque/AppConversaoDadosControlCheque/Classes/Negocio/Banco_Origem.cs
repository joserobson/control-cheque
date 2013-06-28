using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace AppConversaoDadosControlCheque.Classes.Negocio
{
    public class Banco_Origem
    {
        private string codigoBanco;
        private string nomeBanco;

        public Banco_Origem()
        {
            codigoBanco = string.Empty;
            nomeBanco = string.Empty;
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

        public string NomeBanco
        {
            get
            {
                return this.nomeBanco;
            }
            set
            {
                this.nomeBanco = value;
            }
        }
    }
}
