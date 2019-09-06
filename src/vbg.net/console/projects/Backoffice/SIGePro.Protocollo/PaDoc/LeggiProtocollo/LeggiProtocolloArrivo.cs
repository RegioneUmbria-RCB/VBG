using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.WsDataClass;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.PaDoc.LeggiProtocollo
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

        public MittDestOut[] GetMittenteDestinatario()
        {
            return new MittDestOut[] { new MittDestOut { CognomeNome = _mittente.GetValoreMittente(ItemsChoiceType.denominazione) } };
        }

        public string Flusso
        {
            get { return ProtocolloConstants.COD_ARRIVO; }
        }
    }
}
