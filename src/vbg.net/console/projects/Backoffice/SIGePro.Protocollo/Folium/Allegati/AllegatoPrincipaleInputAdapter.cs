using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.WsDataClass;

namespace Init.SIGePro.Protocollo.Folium.Allegati
{
    public class AllegatoPrincipaleInputAdapter : IAllegatiAdapter
    {
        long _idProtocollo;
        string _nomeFile;

        public AllegatoPrincipaleInputAdapter(long idProtocollo, string nomeFile)
        {
            this._idProtocollo = idProtocollo;
            this._nomeFile = nomeFile;
        }

        public IEnumerable<AllOut> Adatta()
        {
            if (String.IsNullOrEmpty(this._nomeFile))
            {
                return null;
            }

            var retVal = new List<AllOut>();
            retVal.Add(new AllOut
            {
                IDBase = "0",
                Serial = this._nomeFile,
                Commento = $"Allegato Principale ({this._nomeFile})"
            });

            return retVal;
        }
    }
}
