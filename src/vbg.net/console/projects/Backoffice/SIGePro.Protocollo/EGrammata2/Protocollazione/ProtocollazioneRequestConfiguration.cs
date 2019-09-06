using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.EGrammata2.Protocollazione.Segnatura.Request;
using Init.SIGePro.Protocollo.ProtocolloServices;
using Init.SIGePro.Protocollo.EGrammata2.LeggiProtocollo;
using Init.SIGePro.Protocollo.EGrammata2.Verticalizzazioni;

namespace Init.SIGePro.Protocollo.EGrammata2.Protocollazione
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

            Allegati = datiProto.ProtoIn.Allegati;
        }
    }
}
