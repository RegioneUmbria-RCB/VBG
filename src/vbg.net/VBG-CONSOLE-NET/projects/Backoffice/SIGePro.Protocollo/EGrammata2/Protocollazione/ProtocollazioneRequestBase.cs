using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.EGrammata2.Protocollazione.Segnatura.Request;
using Init.SIGePro.Data;

namespace Init.SIGePro.Protocollo.EGrammata2.Protocollazione
{
    public class ProtocollazioneRequestBase
    {
        protected readonly IDatiProtocollo DatiProtocollo;
        protected readonly Classificazione Classifica;
        protected readonly List<ProtocolloAllegati> Allegati;

        public ProtocollazioneRequestBase(IDatiProtocollo datiProto, string classifica, List<ProtocolloAllegati> allegati)
        {
            DatiProtocollo = datiProto;
            Classifica = GetClassificaAdattata(classifica);
            Allegati = allegati;
        }

        private Classificazione GetClassificaAdattata(string classifica)
        {
            var cl = new ClassificazioneAdapter(classifica);
            return cl.Adatta();
        }
    }
}
