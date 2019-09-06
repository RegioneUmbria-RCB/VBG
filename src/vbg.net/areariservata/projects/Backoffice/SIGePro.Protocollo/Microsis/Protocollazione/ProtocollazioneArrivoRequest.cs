using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Reflection;
using Init.SIGePro.Protocollo.Microsis.Protocollazione.TipoPersona;
using Init.SIGePro.Data;

namespace Init.SIGePro.Protocollo.Microsis.Protocollazione
{
    [Serializable]
    public class ProtocollazioneArrivoRequest
    {
        public string TipoDocumento { get; set; }
        public string Oggetto { get; set; }
        public string CodiceTitolario { get; set; }
        public string Ufficio { get; set; }
        public string DataDocumento { get; set; }
        public string ModalitaTrasmissione { get; set; }
        public string Riservatezza { get; set; }
        public string Originale { get; set; }
        public string PartitaIvaMittente { get; set; }
        public string RagioneSocialeMittente { get; set; }
        public string CodiceFiscaleMittente { get; set; }
        public string NomeMittente { get; set; }
        public string CognomeMittente { get; set; }
        public string ProtocolloMittente { get; set; }
        public string DataProtocolloMittente { get; set; }
        public string Note { get; set; }
        public string ServizioDestinatario { get; set; }
        public string TipoDate { get; set; }

        public ProtocollazioneArrivoRequest()
        {

        }

        public ProtocollazioneArrivoRequest(IDatiProtocollo datiProto, List<IAnagraficaAmministrazione> anagrafiche)
        {
            this.TipoDocumento = datiProto.ProtoIn.TipoDocumento;
            this.Oggetto = datiProto.ProtoIn.Oggetto;
            this.CodiceTitolario = datiProto.ProtoIn.Classifica;
            this.Ufficio = datiProto.Ruolo;
            this.DataDocumento = DateTime.Now.ToString("dd/MM/yyyy");
            this.ModalitaTrasmissione = datiProto.ProtoIn.TipoSmistamento;
            this.Riservatezza = "NO";
            this.Originale = "SI";

            var factoryPersona = new TipoPersonaFactory();
            var metadatiMittente = factoryPersona.CreateFactory(anagrafiche[0]);
            factoryPersona.Valida(metadatiMittente);
            this.PartitaIvaMittente = metadatiMittente.PartitaIva;
            this.RagioneSocialeMittente = metadatiMittente.RagioneSociale;
            this.CodiceFiscaleMittente = metadatiMittente.CodiceFiscale;
            this.NomeMittente = metadatiMittente.Nome;
            this.CognomeMittente = metadatiMittente.Cognome;
            this.ProtocolloMittente = metadatiMittente.Protocollo;
            this.DataProtocolloMittente = "";
            this.Note = "";
            this.ServizioDestinatario = datiProto.Uo;
            this.TipoDate = "IT";
        }
    }
}
