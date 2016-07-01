using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloFoliumService;

namespace Init.SIGePro.Protocollo.Folium.LeggiProtocollo
{
    public class FoliumLeggiProtocolloInputAdapter
    {
        public readonly Ricerca Request;

        string _numeroProtocollo;
        string _annoProtocollo;
        string _registro;

        public FoliumLeggiProtocolloInputAdapter(string numeroProtocollo, string annoProtocollo, string registro)
        {
            _numeroProtocollo = numeroProtocollo;
            _annoProtocollo = annoProtocollo;
            _registro = registro;

            Request = CreaRequest();
        }

        private Ricerca CreaRequest()
        {
            DateTime dataDa = new DateTime(Convert.ToInt32(_annoProtocollo), 1, 1);
            DateTime dataA = new DateTime(Convert.ToInt32(_annoProtocollo), 12, 31);

            var request = new Ricerca 
            { 
                numeroDa = _numeroProtocollo,
                numeroA = _numeroProtocollo,
                dataProtocolloDa = dataDa,
                dataProtocolloA = dataA,
                registro = _registro
            };

            return request;
        }
    }
}
