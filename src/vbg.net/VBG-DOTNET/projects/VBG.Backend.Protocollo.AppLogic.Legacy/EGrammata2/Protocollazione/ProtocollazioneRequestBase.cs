using System.Collections.Generic;
using VBG.Backend.Protocollo.AppLogic.Legacy.EGrammata2.Protocollazione.Segnatura.Request;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.EGrammata2.Protocollazione
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
