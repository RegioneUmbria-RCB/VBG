using System.Collections.Generic;
using System.Linq;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;


namespace VBG.Backend.Protocollo.AppLogic.Legacy.StudioK.LeggiProtocollo
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

        public MittDestOutType[] GetMittenteDestinatario()
        {
            return _destinatari.Select(x => new MittDestOutType { CognomeNome = ((Persona)x.Item).Denominazione }).ToArray();
        }

        public string Flusso
        {
            get { return ProtocolloConstants.COD_PARTENZA; }
        }
    }
}
