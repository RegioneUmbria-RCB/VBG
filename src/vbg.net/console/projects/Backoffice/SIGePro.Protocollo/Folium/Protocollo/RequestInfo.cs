using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.ProtocolloFoliumService;
using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.Folium.Flusso;

namespace Init.SIGePro.Protocollo.Folium.Protocollo
{
    public class RequestInfo
    {
        public readonly string Flusso;
        public readonly string Registro;
        public readonly string Oggetto;
        public readonly string UfficioCompetente;
        public readonly string[] VociTitolario;
        public string Note { get; private set; }
        public readonly VerticalizzazioniWrapper Regole;
        public readonly IEnumerable<IAnagraficaAmministrazione> Anagrafiche;
        public readonly IEnumerable<ProtocolloAllegati> Allegati;
        public readonly string Smistamento;
        public readonly string Ruolo;
        public readonly string Operatore;

        public RequestInfo(VerticalizzazioniWrapper vert, IDatiProtocollo datiProto, string note, IEnumerable<IAnagraficaAmministrazione> anagrafiche, string operatore)
        {
            this.Flusso = FlussoAdapter.FromVbgToWs(datiProto.ProtoIn.Flusso);
            this.Registro = vert.Registro;
            this.Oggetto = datiProto.ProtoIn.Oggetto;
            this.UfficioCompetente = datiProto.Amministrazione.PROT_UO;
            this.VociTitolario = new string[] { datiProto.ProtoIn.Classifica };
            this.Note = note;
            this.Regole = vert;
            this.Anagrafiche = anagrafiche;
            this.Allegati = datiProto.ProtoIn.Allegati;
            this.Smistamento = datiProto.ProtoIn.TipoSmistamento;
            this.Ruolo = datiProto.Ruolo;
            this.Operatore = operatore;
        }
    }
}
