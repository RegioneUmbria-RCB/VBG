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
    public class MittentiDestinatariArrivo : ILeggiProtoMittentiDestinatari
    {
        MittentiDestinatariVersioneFactory _factory;

        public MittentiDestinatariArrivo(MittentiDestinatariVersioneFactory factoryVersione)
        {
            _factory = factoryVersione;
        }

        public string InCaricoA
        {
            get { return _factory.Destinatari.ToList()[0].AmministrazioneProtocollo.CodiceAmministrazione; }
        }

        public string InCaricoADescrizione
        {
            get { return _factory.Destinatari.ToList()[0].AmministrazioneProtocollo.Denominazione; }
        }

        public MittDestOut[] GetMittenteDestinatario()
        {
            return _factory.Mittenti.Select(x => new MittDestOut
            {
                IdSoggetto = x.PersonaProtocollo.id,
                CognomeNome = x.PersonaProtocollo.Denominazione
            }).ToArray();
        }


        public string Flusso
        {
            get { return ProtocolloConstants.COD_ARRIVO; }
        }
    }
}
