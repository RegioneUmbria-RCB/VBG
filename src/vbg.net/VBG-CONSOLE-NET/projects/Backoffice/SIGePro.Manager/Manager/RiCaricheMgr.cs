using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Data;

namespace Init.SIGePro.Manager
{
    public partial class RiCaricheMgr
    {
        public RiCariche GetRiCaricaByInQualitaDi(int codiceInQualitaDi, string idComune)
        {
            var tsMgr = new TipiSoggettoMgr(db, idComune);
            TipiSoggetto tipoSoggetto = tsMgr.GetById(codiceInQualitaDi);

            if (tipoSoggetto == null || String.IsNullOrEmpty(tipoSoggetto.FK_RICA_CODICE))
                return null;

            var caricheMgr = new RiCaricheMgr(db);
            var carica = caricheMgr.GetById(tipoSoggetto.FK_RICA_CODICE);
            
            return carica;
        }
    }
}
