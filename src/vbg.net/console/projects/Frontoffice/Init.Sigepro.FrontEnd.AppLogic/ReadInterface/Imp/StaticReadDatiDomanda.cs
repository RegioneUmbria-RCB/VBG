using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.ReadInterface.Imp
{
    public class StaticReadDatiDomanda : IReadDatiDomanda
    {
        IDomandaOnlineReadInterface _domanda;
        PresentazioneIstanzaDataKey _dataKey;

        public StaticReadDatiDomanda(IDomandaOnlineReadInterface domanda, PresentazioneIstanzaDataKey dataKey)
        {
            this._domanda = domanda;
            this._dataKey = dataKey;
        }


        public IDomandaOnlineReadInterface Domanda
        {
            get
            {
                return this._domanda;
            }
        }

        public PresentazioneIstanzaDataKey DomandaDataKey
        {
            get { return this._dataKey; }
        }
    }
}
