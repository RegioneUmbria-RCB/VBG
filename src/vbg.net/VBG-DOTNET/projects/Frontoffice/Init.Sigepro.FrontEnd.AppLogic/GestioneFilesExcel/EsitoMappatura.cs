using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneFilesExcel
{
    class EsitoMappatura
    {
        public readonly int IdCampo;
        public readonly string Valore;
        public readonly string NomeCampo;

        public EsitoMappatura(int idCampo, string nomeCampo, string valore)
        {
            this.NomeCampo = nomeCampo;
            this.IdCampo = idCampo;
            this.Valore = valore;
        }

        
    }
}
