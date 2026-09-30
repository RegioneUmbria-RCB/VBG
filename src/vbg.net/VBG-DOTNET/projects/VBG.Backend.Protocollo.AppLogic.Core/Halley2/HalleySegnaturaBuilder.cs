using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;

namespace VBG.Backend.Protocollo.AppLogic.Core.Halley2
{
    public class HalleySegnaturaBuilder
    {

        public class Anagrafica
        {
            public string Nome { get; set; }
            public string Cognome { get; set; }
            public string RagioneSociale { get; set; }
            public string CodiceFiscale { get; set; }
            public string PartitaIva { get; set; }
            public string Comune { get; set; }
            public string Indirizzo { get; set; }
            public string Civico { get; set; }
            public string Provincia { get; set; }
            public string Telefono { get; set; }
            public string Email { get; set; }
        }
        public class Ufficio
        {
            public string UfficioMittente { get; set; }
            public string UfficioDestinat { get; set; }
        }

        [XmlRoot(ElementName = "Protocollo")]
        public class SegnaturaHalley2
        {
            public string Tipo { get; set; }
            public string Spedizione { get; set; }
            public string ProtocolloMittente { get; set; }
            public string DataProtocolloMittente { get; set; }
            public string Oggetto { get; set; }
            public string Data { get; set; }
            public string Ora { get; set; }

            public string CasellaEmail { get; set; }

            public string OggettoEmail { get; set; }

            public string Classificazione { get; set; }

            public List<Anagrafica> Anagrafiche { get; set; }

            public Ufficio Ufficio { get; set; }

            public string Fascicolo { get; set; }
        }

        private string _casellaEmail;
        private string _flusso;
        private IProtocolloSerializer _serializer;
        private DatiProtocolloIn _protoIn;
        private List<IAnagraficaAmministrazione> _anagrafiche;

        public HalleySegnaturaBuilder(IProtocolloSerializer serializer, DatiProtocolloIn protoIn, string casellaEmail, List<IAnagraficaAmministrazione> anagrafiche)
        {
            this._protoIn = protoIn;
            this._flusso = protoIn.Flusso == "A" ? "Arrivo" : "Partenza";
            this._casellaEmail = casellaEmail;
            this._serializer = serializer;
            this._anagrafiche = anagrafiche;
        }

        internal byte[] Build()
        {
            //var segnatura = new SegnaturaHalley2
            //{
            //    Tipo = this._flusso,
            //    Spedizione = this._protoIn.TipoDocumento,
            //    ProtocolloMittente = this._protoIn.NumProtMitt,
            //    DataProtocolloMittente = this._protoIn.DataProtMitt,
            //    Oggetto = this._protoIn.Oggetto,
            //    Data = null,
            //    Ora = null,
            //    CasellaEmail = this._casellaEmail,
            //    OggettoEmail = this._protoIn.Oggetto,
            //    Classificazione = this._protoIn.Classifica,
            //    Anagrafiche =
            //        this._protoIn.Flusso == "A"
            //            ? this._protoIn
            //                .Mittenti?
            //                .Anagrafe?
            //                .Select(x => new Anagrafica
            //                {
            //                    Civico = null,
            //                    CodiceFiscale = x.CODICEFISCALE,
            //                    Cognome = x.NOMINATIVO,
            //                    Comune = x.ComuneResidenza?.COMUNE,
            //                    Email = x.EMAIL,
            //                    Indirizzo = x.INDIRIZZO,
            //                    Nome = x.NOME,
            //                    PartitaIva = x.PARTITAIVA,
            //                    Provincia = x.PROVINCIA,
            //                    RagioneSociale = x.NOMINATIVO,
            //                    Telefono = x.TELEFONO
            //                })?
            //                .ToList()
            //            : this._protoIn
            //                .Destinatari?
            //                .Anagrafe?
            //                .Select(x => new Anagrafica
            //                {
            //                    Civico = null,
            //                    CodiceFiscale = x.CODICEFISCALE,
            //                    Cognome = x.NOMINATIVO,
            //                    Comune = x.ComuneResidenza?.COMUNE,
            //                    Email = x.EMAIL,
            //                    Indirizzo = x.INDIRIZZO,
            //                    Nome = x.NOME,
            //                    PartitaIva = x.PARTITAIVA,
            //                    Provincia = x.PROVINCIA,
            //                    RagioneSociale = x.NOMINATIVO,
            //                    Telefono = x.TELEFONO
            //                })?
            //                .ToList()
            //    ,
            //    Ufficio = new Ufficio
            //    {
            //        UfficioMittente = this._protoIn.Flusso == "A" ? null : this._protoIn.Mittenti?.Amministrazione?.FirstOrDefault()?.PROT_UO,
            //        UfficioDestinat = this._protoIn.Flusso == "A" ? this._protoIn.Destinatari?.Amministrazione?.FirstOrDefault()?.PROT_UO : null
            //    },
            //    Fascicolo = null
            //};

            
            var segnatura = new SegnaturaHalley2
            {
                Tipo = this._flusso,
                Spedizione = this._protoIn.TipoDocumento,
                ProtocolloMittente = this._protoIn.NumProtMitt,
                DataProtocolloMittente = this._protoIn.DataProtMitt,
                Oggetto = this._protoIn.Oggetto,
                Data = null,
                Ora = null,
                CasellaEmail = this._casellaEmail,
                OggettoEmail = string.IsNullOrEmpty(this._protoIn.CorpoMail) ? this._protoIn.Oggetto : this._protoIn.CorpoMail,
                Classificazione = this._protoIn.Classifica,
                Anagrafiche =
                    this._protoIn.Flusso == "A"
                        ? this._anagrafiche?
                        .Select(x => new Anagrafica
                            {
                                Civico = null,
                                CodiceFiscale = x.CodiceFiscale,
                                Cognome = x.Cognome,
                                Comune = x.ComuneResidenza?.DenominazioneComune,
                                Email = x.Pec,
                                Indirizzo = x.Indirizzo,
                                Nome = x.Nome,
                                PartitaIva = x.PartitaIva,
                                Provincia = x.Provincia,
                                RagioneSociale = x.Denominazione,
                                Telefono = x.Telefono
                            })?
                            .ToList()
                        : this._anagrafiche?
                            .Select(x => new Anagrafica
                            {
                                Civico = null,
                                CodiceFiscale = x.CodiceFiscale,
                                Cognome = x.Cognome,
                                Comune = x.ComuneResidenza?.DenominazioneComune,
                                Email = x.Pec,
                                Indirizzo = x.Indirizzo,
                                Nome = x.Nome,
                                PartitaIva = x.PartitaIva,
                                Provincia = x.Provincia,
                                RagioneSociale = x.Denominazione,
                                Telefono = x.Telefono
                            })?
                            .ToList()
                ,
                Ufficio = new Ufficio
                {
                    UfficioMittente = this._protoIn.Flusso == "A" ? null : this._protoIn.Mittenti?.Amministrazione?.FirstOrDefault()?.PROT_UO,
                    UfficioDestinat = this._protoIn.Flusso == "A" ? this._protoIn.Destinatari?.Amministrazione?.FirstOrDefault()?.PROT_UO : null
                },
                Fascicolo = null
            };

            var segnaturaStr = this.SerializzaSegnatura(segnatura);

            return Encoding.UTF8.GetBytes( segnaturaStr);
        }

        private string SerializzaSegnatura(SegnaturaHalley2 segnatura)
        {
            return this._serializer.Serialize(ProtocolloLogsConstants.SegnaturaXmlFileName, segnatura);
        }
    }
}
