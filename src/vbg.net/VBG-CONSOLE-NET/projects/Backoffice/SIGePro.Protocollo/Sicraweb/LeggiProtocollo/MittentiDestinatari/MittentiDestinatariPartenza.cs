using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Protocollo.WsDataClass;

namespace Init.SIGePro.Protocollo.Sicraweb.LeggiProtocollo.MittentiDestinatari
{
    public class MittentiDestinatariPartenza : ILeggiProtoMittentiDestinatari
    {
        MittentiDestinatariVersioneFactory _factory;

        public MittentiDestinatariPartenza(MittentiDestinatariVersioneFactory factoryVersione) 
        {
            _factory = factoryVersione;
        }

        public string InCaricoA
        {
            get { return _factory.Mittenti.ToList()[0].AmministrazioneProtocollo.CodiceAmministrazione; }
        }

        public string InCaricoADescrizione
        {
            get { return _factory.Mittenti.ToList()[0].AmministrazioneProtocollo.Denominazione; }
        }

        public MittDestOut[] GetMittenteDestinatario()
        {
            return _factory.Destinatari.Select(x => new MittDestOut
            {
                IdSoggetto = x.PersonaProtocollo.id,
                CognomeNome = x.PersonaProtocollo.Denominazione
            }).ToArray();
        }


        public string Flusso
        {
            get { return ProtocolloConstants.COD_PARTENZA; }
        }
    }
}
