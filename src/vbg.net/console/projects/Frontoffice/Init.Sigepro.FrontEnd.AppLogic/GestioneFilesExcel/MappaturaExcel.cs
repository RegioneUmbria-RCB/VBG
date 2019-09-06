using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneFilesExcel
{
    public class MappaturaExcel
    {
        public readonly int IdCampo;
        public readonly string Espressione;
        public readonly string NomeCampo;

        public MappaturaExcel(int idCampo, string nomeCampo, string espressione)
        {
            this.NomeCampo = nomeCampo;
            this.IdCampo = idCampo;
            this.Espressione = espressione;
        }

        
    }
}
