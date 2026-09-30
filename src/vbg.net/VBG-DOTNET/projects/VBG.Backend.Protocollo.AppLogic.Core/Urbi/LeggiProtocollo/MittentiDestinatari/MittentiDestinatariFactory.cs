using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;

namespace VBG.Backend.Protocollo.AppLogic.Core.Urbi.LeggiProtocollo.MittentiDestinatari
{
    public class MittentiDestinatariFactory
    {
        public static ILeggiProtoMittentiDestinatari Create(string flusso, List<Corrispondente> corrispondenti, List<UfficioMittente> ufficiMittenti, List<UfficioDestinatario> ufficiDestinatari)
        {
            if (flusso == "A")
            {
                return new MittentiDestinatariArrivo(ufficiDestinatari[0].Descrizione, corrispondenti);
            }
            else if (flusso == "P")
            {
                return new MittentiDestinatariPartenza(ufficiMittenti[0].Descrizione, corrispondenti);
            }
            else if (flusso == "I")
            {
                return new MittentiDestinatariInterno(ufficiMittenti[0].Descrizione, ufficiDestinatari[0].Descrizione);
            }
            else
                throw new Exception(String.Format("FLUSSO {0} NON RICONOSCIUTO", flusso));
        }
    }
}
