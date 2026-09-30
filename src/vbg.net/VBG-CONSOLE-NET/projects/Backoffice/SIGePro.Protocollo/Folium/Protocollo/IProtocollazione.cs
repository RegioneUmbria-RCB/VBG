using Init.SIGePro.Protocollo.WsDataClass;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Folium.Protocollo
{
    public interface IProtocollazione
    {
        DatiProtocolloRes Protocolla();
        void InserisciAllegati(long idProtocollo);
        void InviaMail(long idProtocollo);
        void Assegna(long idProtocollo);
    }
}
