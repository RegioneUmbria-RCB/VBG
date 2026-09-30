using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Core.PaDoc.LeggiProtocollo
{
    public class LeggiProtocolloPartenza : ILeggiProtoMittentiDestinatari
    {
        rispostaRisultatoMittente _mittente;
        rispostaRisultatoDestinatario[] _destinatari;

        public LeggiProtocolloPartenza(rispostaRisultatoMittente mittente, rispostaRisultatoDestinatario[] destinatari)
        {
            _mittente = mittente;
            _destinatari = destinatari;
        }

        public string InCaricoA
        {
            get { return _mittente.GetValoreMittente(ItemsChoiceType.codice_ufficio); }
        }

        public string InCaricoADescrizione
        {
            get { return " - "; }
        }

        public MittDestOutType[] GetMittenteDestinatario()
        {
            return _destinatari.Select(x => new MittDestOutType { CognomeNome = x.GetValoreDestinatario(ItemsChoiceType1.denominazione) }).ToArray();
        }

        public string Flusso
        {
            get { return ProtocolloConstants.COD_PARTENZA; }
        }
    }
}
