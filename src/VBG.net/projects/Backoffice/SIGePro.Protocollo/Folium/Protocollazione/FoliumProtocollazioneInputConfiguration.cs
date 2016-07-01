using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.Folium.Verticalizzazioni;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.ProtocolloFoliumService;
using Init.SIGePro.Protocollo.Folium.Protocollazione.MittentiDestinatari;
using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.Folium.Flusso;

namespace Init.SIGePro.Protocollo.Folium.Protocollazione
{
    public class FoliumProtocollazioneInputConfiguration
    {
        public readonly string Flusso;
        public readonly string Registro;
        public readonly string Oggetto;
        public readonly MittenteDestinatario[] MittenteDestinatario;
        public readonly string UfficioCompetente;
        public readonly string[] VociTitolario;
        //public bool IsContenuto { get; private set; }
        public string NomeFileContenuto { get; private set; }
        public byte[] Contenuto { get; private set; }

        public FoliumProtocollazioneInputConfiguration(DatiProtocolloIn protoIn, FoliumVerticalizzazioniAdapter vert, IDatiProtocollo datiProto)
        {
            Flusso = FoliumFlussoAdapter.FromVbgToWs(protoIn.Flusso);
            Registro = vert.Registro;
            Oggetto = protoIn.Oggetto;
            MittenteDestinatario = CreaMittenteDestinatario(datiProto, vert.CodiceCC);
            UfficioCompetente = datiProto.Amministrazione.PROT_UO;
            VociTitolario = new string[] { protoIn.Classifica };

            //ValorizzaAllegatoPrimario(protoIn.Allegati);
        }

        private MittenteDestinatario[] CreaMittenteDestinatario(IDatiProtocollo datiProto, string codicePerConoscenza)
        {
            try
            {
                var list = MittentiDestinatariFactory.Create(datiProto);
                var adapter = new MittentiDestinatariAdapter(list, codicePerConoscenza);
                return adapter.Adatta();
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE GENERATO DURANTE LA MAPPATURA DEI DATI DEI MITTENTI / DESTINATARI", ex);
            }
        }

        /*private void ValorizzaAllegatoPrimario(List<ProtocolloAllegati> all)
        {
            IsContenuto = all.Count > 0;
            if (all.Count > 0)
            {
                var allegatoPrimario = all.First();
                NomeFileContenuto = allegatoPrimario.NOMEFILE;
                Contenuto = allegatoPrimario.OGGETTO;
            }
        }*/
    }
}
