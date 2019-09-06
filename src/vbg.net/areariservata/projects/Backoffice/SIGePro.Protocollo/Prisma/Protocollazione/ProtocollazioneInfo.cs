using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using static Init.SIGePro.Protocollo.ProtocolloEnumerators.ProtocolloEnum;

namespace Init.SIGePro.Protocollo.Prisma.Protocollazione
{
    public class ProtocollazioneInfo
    {
        public string CodiceUo { get; private set; }
        public IEnumerable<IAnagraficaAmministrazione> Anagrafiche { get; private set; }
        public string TipoDocumento { get; private set; }
        public string Classifica { get; private set; }
        public IEnumerable<ProtocolloAllegati> Allegati { get; private set; }
        public ParametriRegoleWrapper ParametriRegola { get; private set; }
        public string Oggetto { get; private set; }
        public string Flusso { get; private set; }
        public string DenominazioneUfficio;
        public ProtocollazioneServiceWrapper ProtocollazioneSrv;
        public string TipoSmistamento { get; private set; }
        public ListaMittDest Mittenti { get; private set; }
        public ListaMittDest Destinatari { get; private set; }
        public AmbitoProtocollazioneEnum Ambito { get; private set; }
        public string CodiceRuolo { get; private set; }

        public ProtocollazioneInfo(string codiceUo, string denominazioneUo, DatiProtocolloIn protoIn, IEnumerable<IAnagraficaAmministrazione> anagrafiche, ParametriRegoleWrapper parametriRegola, ProtocollazioneServiceWrapper protocollazioneSrv, AmbitoProtocollazioneEnum ambito, string ruolo)
        {
            this.CodiceUo = codiceUo;
            this.Anagrafiche = anagrafiche;
            this.TipoDocumento = protoIn.TipoDocumento;
            this.Classifica = protoIn.Classifica;
            this.Allegati = protoIn.Allegati;
            this.ParametriRegola = parametriRegola;
            this.Flusso = protoIn.Flusso;
            this.DenominazioneUfficio = denominazioneUo;
            this.ProtocollazioneSrv = protocollazioneSrv;
            this.TipoSmistamento = protoIn.TipoSmistamento;
            this.Oggetto = protoIn.Oggetto;
            this.Mittenti = protoIn.Mittenti;
            this.Destinatari = protoIn.Destinatari;
            this.Ambito = ambito;
            this.CodiceRuolo = ruolo;
        }
    }
}
