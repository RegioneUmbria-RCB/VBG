using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.WsDataClass;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.PaDoc.LeggiProtocollo
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

        public MittDestOut[] GetMittenteDestinatario()
        {
            return _destinatari.Select(x => new MittDestOut { CognomeNome = x.GetValoreDestinatario(ItemsChoiceType1.denominazione) }).ToArray();
        }

        public string Flusso
        {
            get { return ProtocolloConstants.COD_PARTENZA; }
        }
    }
}
