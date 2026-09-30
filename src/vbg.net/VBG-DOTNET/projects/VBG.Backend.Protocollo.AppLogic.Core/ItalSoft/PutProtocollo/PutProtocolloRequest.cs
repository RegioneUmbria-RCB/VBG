using ProtocolloItalSoftService;

namespace VBG.Backend.Protocollo.AppLogic.Core.ItalSoft.PutProtocollo
{
    public class PutProtocolloRequest
    {
        public string Token { get; set; }
        public string TipoProtocollo { get; set; }
        public string Ufficio { get; set; }
        public List<Firmatario> Firmatari { get; set; }
        public string Oggetto { get; set; }
        public string TipoDocumento { get; set; }
        public List<Soggetto> Mittenti { get; set; }
        public List<Soggetto> Destinatari { get; set; }
        public string Classifica { get; set; }
        public List<AllegatoProtocollo> AllegatiProtocollo { get; set; }
        internal datiProtocollo TodatiProtocollo()
        {
            firmatari firmatari = new firmatari()
            {
                firmatario = this
                    .Firmatari?
                    .Select(x => new firmatario
                    {
                        codice = x.CodiceSoggetto,
                        ufficio = x.CodiceUfficio
                    }).ToArray()
            };

            mittenti mittenti = new mittenti()
            {
                mittenteDestinatario = this
                    .Mittenti?
                    .Select(x => new mittenteDestinatario
                    {
                        cap = x.CAP,
                        citta = x.Citta,
                        denominazione = x.Denominazione,
                        codiceFiscale = x.CodiceFiscale,
                        email = x.Email,
                        indirizzo = x.Indirizzo,
                        prov = x.Provincia,
                        ufficio = x.Ufficio
                    })
                    .ToArray()
            };

            destinatari destinatari = new destinatari()
            {
                mittenteDestinatario = this
                    .Destinatari?
                    .Select(x => new mittenteDestinatario
                    {
                        cap = x.CAP,
                        citta = x.Citta,
                        denominazione = x.Denominazione,
                        codiceFiscale = x.CodiceFiscale,
                        email = x.Email,
                        indirizzo = x.Indirizzo,
                        prov = x.Provincia,
                        ufficio = x.Ufficio
                    })
                    .ToArray()
            };

            allegatiPrecaricati allegatiPrecaricati = new allegatiPrecaricati()
            {
                allegatoPrecaricato = this
                    .AllegatiProtocollo
                    .Where(x => x.PreCaricato)
                    .Select(x => new allegatoPrecaricato
                    {
                        idunivoco = x.Id,
                        hashfile = x.ImprontaHash,
                        nomeFile = x.Nome,
                        tipoFile = x.Tipo,
                        estensione = x.Estensione,
                        marcaDocumento = "0",
                        mettiAllaFirma = x.MettiAllaFirma ? "1" : "0"
                    })
                    .ToArray()
            };


            return new datiProtocollo
            {
                dataArrivo = DateTime.Now.ToString("yyyyMMdd"),
                tipoProtocollo = this.TipoProtocollo,
                ufficioOperatore = this.Ufficio,
                firmatari = firmatari,
                oggetto = this.Oggetto,
                tipoDocumento = this.TipoDocumento,
                mittenti = mittenti,
                destinatari = destinatari,
                classificazione = this.Classifica,
                allegato = this
                    .AllegatiProtocollo?
                    .Where(x => !x.PreCaricato && x.Tipo == "PRINCIPALE")?
                    .Select(x => new allegato
                    {
                        tipoFile = x.Tipo,
                        nomeFile = x.Nome,
                        estensione = x.Estensione,
                        stream = Convert.ToBase64String(x.Content)
                    })?
                    .FirstOrDefault(),
                allegatiPrecaricati = allegatiPrecaricati
            };
        }
    }
}
