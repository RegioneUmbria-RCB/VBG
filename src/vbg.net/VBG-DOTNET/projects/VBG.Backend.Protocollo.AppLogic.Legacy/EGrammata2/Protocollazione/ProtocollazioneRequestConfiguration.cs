using System.Collections.Generic;
using System.Linq;
using VBG.Backend.Protocollo.AppLogic.Legacy.EGrammata2.LeggiProtocollo;
using VBG.Backend.Protocollo.AppLogic.Legacy.EGrammata2.Protocollazione.Segnatura.Request;
using VBG.Backend.Protocollo.AppLogic.Legacy.EGrammata2.Verticalizzazioni;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.Services;


namespace VBG.Backend.Protocollo.AppLogic.Legacy.EGrammata2.Protocollazione
{
    public class ProtocollazioneRequestConfiguration
    {
        public readonly IDatiProtocollo DatiProto;
        public readonly Classificazione Titolario;
        public readonly List<ProtocolloAllegati> Allegati;
        public readonly UO UoStrutturaUffici;
        public readonly LeggiProtocolloService LeggiProtoWrapper;
        public readonly VerticalizzazioniConfiguration Vert;
        public readonly ResolveDatiProtocollazioneService DatiProtoService;

        public ProtocollazioneRequestConfiguration(IDatiProtocollo datiProto, LeggiProtocolloService leggiProtoWrapper, ResolveDatiProtocollazioneService datiProtoService, VerticalizzazioniConfiguration vert)
        {
            DatiProto = datiProto;
            LeggiProtoWrapper = leggiProtoWrapper;
            DatiProtoService = datiProtoService;
            Vert = vert;
            UoStrutturaUffici = ProtocollazioneRequestUfficiAdapter.Adatta(datiProto.Uo);

            var cl = new ClassificazioneAdapter(datiProto.ProtoIn.Classifica);
            Titolario = cl.Adatta();

            Allegati = datiProto.ProtoIn.RecuperaAllegati().ToList();
        }
    }
}
