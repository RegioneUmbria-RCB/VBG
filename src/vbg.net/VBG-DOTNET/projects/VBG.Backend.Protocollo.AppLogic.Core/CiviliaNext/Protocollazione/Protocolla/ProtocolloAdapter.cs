
using VBG.Backend.Protocollo.AppLogic.Shared.Data;

namespace VBG.Backend.Protocollo.AppLogic.Core.CiviliaNext.Protocollazione.Protocolla
{
    public class ProtocolloAdapter
    {
        private Dictionary<string, string> TipoProtocollo = new Dictionary<string, string>
        {
            { "A","INGRESSO" },
            { "I", "INTERNO" },
            { "P", "USCITA" }
        };

        private readonly ParametriRegoleInfo _parametri;
        private readonly DatiProtocolloIn _datiProtocollazione;

        public ProtocolloAdapter(ParametriRegoleInfo parametri, DatiProtocolloIn datiProtocollazione)
        {
            this._parametri = parametri;
            this._datiProtocollazione = datiProtocollazione;
        }

        public ProtocollazioneRequest CreaRequest()
        {

            return new ProtocollazioneRequest
            {
                Pratica = new Pratica
                {
                    AllegatiList = null,
                    Assegnata = false,
                    Classificazione = this.GetClassificazione(),
                    CodiceLivelloOrganigramma = this._parametri.CodiceLivelloOrganigramma,
                    DataConsegnaDocumento = null,
                    DataRegistrazione = null,
                    IdCodiceAOO = this._parametri.IdCodiceAOO,
                    IdCorrispondentiList = this.GetCorrispondenti(),
                    InviaMail = this._parametri.InviaMail,
                    IsFromModificaAllegato = false,
                    IsFromModificaCorrispondente = false,
                    MotivoModifica = null,
                    Note = null,
                    NumeroProtocollo = null,
                    Oggetto = _datiProtocollazione.Oggetto,
                    TipoProtocollo = this.TipoProtocollo[this._datiProtocollazione.Flusso],
                },
                CodiceLivelloOrganigramma = this._parametri.CodiceLivelloOrganigramma,
                IdAutore = this._parametri.IdOperatore.ToString(),
                IdRegistro = this._parametri.IdRegistro
            };
        }

        private Classificazione GetClassificazione()
        {
            var classifica = this._datiProtocollazione.Classifica.Split(Convert.ToChar("."));

            return new Classificazione
            {
                CodiceTitolo = classifica[0],
                CodiceClasse = classifica.Length > 1 ? $"{classifica[0]}.{classifica[1]}" : null,
                CodiceSottoClasse = classifica.Length == 3 ? $"{classifica[0]}.{classifica[1]}.{classifica[2]}" : null
            };
        }

        private IEnumerable<IndividuoProtocolloWS> GetCorrispondenti()
        {
            switch (this._datiProtocollazione.Flusso)
            {
                case "A":
                    {
                        return this.RecuperaCorrispondentiDaMittenti();
                    }
                case "P":
                    {
                        return this.RecuperaCorrispondentiDaDestinatari();
                    }
                default:
                    {
                        return new List<IndividuoProtocolloWS>(); // nella protocollazione interna non sono ammessi i corrispondendi
                    }
            }
        }

        private List<IndividuoProtocolloWS> RecuperaCorrispondentiDaMittenti()
        {
            List<IndividuoProtocolloWS> corrispondenti = new List<IndividuoProtocolloWS>();
            if (this._datiProtocollazione.Mittenti.Amministrazione.Count > 0)
            {
                corrispondenti.AddRange(this._datiProtocollazione
                                        .Mittenti
                                        .Amministrazione
                                        .Select(x => new IndividuoProtocolloWS
                                        {
                                            Denominazione = x.AMMINISTRAZIONE,
                                            Email = String.IsNullOrEmpty(x.PEC) ? x.EMAIL : x.PEC,
                                            TipoIndividuoProtocollo = "NONCERTIFICATO"
                                        })
                                    );
            }

            if (this._datiProtocollazione.Mittenti.Anagrafe.Count > 0)
            {
                corrispondenti.AddRange(this._datiProtocollazione
                                        .Mittenti
                                        .Anagrafe
                                        .Select(x => new IndividuoProtocolloWS
                                        {
                                            Denominazione = x.GetNomeCompleto(),
                                            Email = x.PecProtocollazione,
                                            TipoIndividuoProtocollo = "NONCERTIFICATO"
                                        })
                                    );
            }
            return corrispondenti;
        }

        private List<IndividuoProtocolloWS> RecuperaCorrispondentiDaDestinatari()
        {
            List<IndividuoProtocolloWS> corrispondenti = new List<IndividuoProtocolloWS>();
            if (this._datiProtocollazione.Destinatari.Amministrazione.Count > 0)
            {
                corrispondenti.AddRange(this._datiProtocollazione
                                        .Destinatari
                                        .Amministrazione
                                        .Select(x => new IndividuoProtocolloWS
                                        {
                                            Denominazione = x.AMMINISTRAZIONE,
                                            Email = String.IsNullOrEmpty(x.PEC) ? x.EMAIL : x.PEC,
                                            TipoIndividuoProtocollo = "NONCERTIFICATO"
                                        })
                                    );
            }

            if (this._datiProtocollazione.Destinatari.Anagrafe.Count > 0)
            {
                corrispondenti.AddRange(this._datiProtocollazione
                                        .Destinatari
                                        .Anagrafe
                                        .Select(x => new IndividuoProtocolloWS
                                        {
                                            Denominazione = x.GetNomeCompleto(),
                                            Email = x.PecProtocollazione,
                                            TipoIndividuoProtocollo = "NONCERTIFICATO"
                                        })
                                    );
            }
            return corrispondenti;
        }
    }
}
