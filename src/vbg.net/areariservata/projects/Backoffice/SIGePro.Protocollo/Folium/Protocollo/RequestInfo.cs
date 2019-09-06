using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.ProtocolloFoliumService;
using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.Folium.Flusso;
using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Protocollo.ProtocolloServices;

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
        public readonly string UoDestinatarioInterno;
        public readonly string RuoloDestinatarioInterno;

        public RequestInfo(VerticalizzazioniWrapper vert, IDatiProtocollo datiProto, string note, IEnumerable<IAnagraficaAmministrazione> anagrafiche, string operatore)
        {
            this.Flusso = FlussoAdapter.FromVbgToWs(datiProto.ProtoIn.Flusso);
            this.Registro = vert.Registro;
            this.Oggetto = datiProto.ProtoIn.Oggetto;
            this.UfficioCompetente = datiProto.Uo;
            this.Anagrafiche = anagrafiche;
            this.UoDestinatarioInterno = "";
            this.RuoloDestinatarioInterno = "";

            if (datiProto.Flusso == ProtocolloConstants.COD_INTERNO)
            {
                this.UfficioCompetente = datiProto.Amministrazione.PROT_UO;
                this.Anagrafiche = new AmministrazioneService[] { new AmministrazioneService(datiProto.AmministrazioniInterne[0]) };
                this.UoDestinatarioInterno = datiProto.AmministrazioniInterne[0].PROT_UO;
                this.RuoloDestinatarioInterno = datiProto.AmministrazioniInterne[0].PROT_RUOLO;
            }

            this.VociTitolario = new string[] { datiProto.ProtoIn.Classifica };
            this.Note = note;
            this.Regole = vert;

            this.Allegati = datiProto.ProtoIn.Allegati;
            this.Smistamento = datiProto.ProtoIn.TipoSmistamento;
            this.Ruolo = datiProto.Ruolo;
            this.Operatore = operatore;
        }
    }
}
