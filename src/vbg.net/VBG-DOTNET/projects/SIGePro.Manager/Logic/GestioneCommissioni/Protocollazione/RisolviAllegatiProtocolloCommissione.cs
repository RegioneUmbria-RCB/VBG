using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.GestioneCommissioni.Protocollazione
{
    public class RisolviAllegatiProtocolloCommissione : IRisolviAllegatiProtocolloCommissione
    {
        private readonly int _codiceOggetto;
        private readonly string _nomeFile;

        public RisolviAllegatiProtocolloCommissione(DataBase db, string idComune, int codiceOggetto, string nomeFile)
        {
            _codiceOggetto = codiceOggetto;
            _nomeFile = nomeFile;

            if (String.IsNullOrEmpty(_nomeFile))
            {
                _nomeFile = new OggettiMgr(db).GetNomeFile(idComune, codiceOggetto);
            }
        }

        public IEnumerable<AllegatoProtocolloCommissione> Allegati => new[]{
            new AllegatoProtocolloCommissione(this._codiceOggetto, this._nomeFile)
         };
    }
}
