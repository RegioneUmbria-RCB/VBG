using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.WsDataClass;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.StudioK.LeggiProtocollo
{
    public class LeggiProtocolloPartenza : ILeggiProtoMittentiDestinatari
    {
        Amministrazione _mittente;
        IEnumerable<Destinatario> _destinatari;

        public LeggiProtocolloPartenza(Amministrazione mittente, IEnumerable<Destinatario> destinatari)
        {
            _mittente = mittente;
            _destinatari = destinatari;
        }

        public string InCaricoA
        {
            get
            {
                return _mittente.CodiceAmministrazione;
            }
        }

        public string InCaricoADescrizione
        {
            get { return _mittente.Denominazione; }
        }

        public MittDestOut[] GetMittenteDestinatario()
        {
            return _destinatari.Select(x => new MittDestOut { CognomeNome = ((Persona)x.Item).Denominazione }).ToArray();
        }

        public string Flusso
        {
            get { return ProtocolloConstants.COD_PARTENZA; }
        }
    }
}
