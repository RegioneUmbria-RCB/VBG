using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Core.PaDoc.LeggiProtocollo
{
    public class LeggiProtocolloArrivo : ILeggiProtoMittentiDestinatari
    {
        rispostaRisultatoMittente _mittente;
        rispostaRisultatoDestinatario[] _destinatari;

        public LeggiProtocolloArrivo(rispostaRisultatoMittente mittente, rispostaRisultatoDestinatario[] destinatari)
        {
            _mittente = mittente;
            _destinatari = destinatari;
        }

        public string InCaricoA
        {
            get { return _destinatari[0].GetValoreDestinatario(ItemsChoiceType1.codice_ufficio); }
        }

        public string InCaricoADescrizione
        {
            get { return " - "; }
        }

        public MittDestOutType[] GetMittenteDestinatario()
        {
            return new MittDestOutType[] { new MittDestOutType { CognomeNome = _mittente.GetValoreMittente(ItemsChoiceType.denominazione) } };
        }

        public string Flusso
        {
            get { return ProtocolloConstants.COD_ARRIVO; }
        }
    }
}
