using ProtocolloItalSoftService;
using System.Collections.Generic;
using System.Linq;

namespace VBG.Backend.Protocollo.AppLogic.Core.ItalSoft.GetProtocollo
{
    public class GetProtocolloResponse
    {
        public string Anno { get; set; }
        public string Numero { get; set; }
        public string Data { get; set; }
        public string Ora { get; set; }
        public string Oggetto { get; set; }
        public string Tipo { get; set; }
        public IEnumerable<Soggetto> Mittenti { get; set; }
        public string CodiceFascicolo { get; set; }
        public IEnumerable<Soggetto> Destinatari { get; set; }
        public string Id { get; set; }
        internal Classifica Classifica { get; set; }
        internal IEnumerable<AllegatoProtocollo> Allegati { get; set; }

        internal static GetProtocolloResponse FromitemProtocollo(itemProtocollo response)
        {
            if (response == null)
            {
                return null;
            }

            return new GetProtocolloResponse
            {
                Anno = response.annoProtocollo,
                Numero = response.numeroProtocollo,
                Data = response.dataProtocollo,
                Ora = response.oraProtocollo,
                Id = response.rowID,
                Oggetto = response.oggetto,
                Classifica = new Classifica
                {
                    Codice = response.classificazione,
                    Descrizione = response.classificazione_Descrizione
                },
                Tipo = response.tipoProtocollo,
                Mittenti = response.tipoProtocollo != "A"
                            ? new List<Soggetto>()
                                {
                                    new Soggetto
                                    {
                                        Codice = response.codiceUfficioUtenteDiInserimento,
                                        Denominazione = response.descrizioneUfficioUtenteDiInserimento
                                    }
                                }
                            :
                            response
                            .mittenti?
                            .Select(x => new Soggetto
                            {
                                Codice = x.codice,
                                Denominazione = x.denominazione,
                                Indirizzo = x.indirizzo,
                                CAP = x.cap,
                                Citta = x.citta,
                                Provincia = x.prov,
                                Email = x.email,
                                CodiceFiscale = x.codiceFiscale
                            }),
                Allegati = response
                            .allegati?
                            .Select(x => new AllegatoProtocollo
                            {
                                Id = x.id,
                                Tipo = x.tipoFile,
                                Nome = x.nomeFile,
                                Estensione = x.estensione
                            }),
                CodiceFascicolo = response.codiceFascicolo,
                Destinatari = response.tipoProtocollo == "A"
                                ? new List<Soggetto>()
                                {
                                    new Soggetto
                                    {
                                        Codice = response.codiceUfficioUtenteDiInserimento,
                                        Denominazione = response.descrizioneUfficioUtenteDiInserimento
                                    }
                                }
                                : response
                                .destinatari?
                                .Select(x => new Soggetto
                                {
                                    Codice = x.codice,
                                    Denominazione = x.denominazione,
                                    Indirizzo = x.indirizzo,
                                    CAP = x.cap,
                                    Citta = x.citta,
                                    Provincia = x.prov,
                                    Email = x.email,
                                    CodiceFiscale = x.codiceFiscale
                                }),

            };
        }
    }
}