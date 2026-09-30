using Init.SIGePro.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Manager.WsParmaPagamenti;

namespace Init.SIGePro.Manager.Logic.PagamentiParma
{
    public abstract class RateizzazioneEmissioniParmaBase
    {
        protected static class Constants
        {
            public const int Id = 0;
            public const int NumeroRuolo = 0;
            public const int NaturaGiuridicaPF = 1;
            public const int NaturaGiuridicaPG = 2;
            public const int CodServizio = 0;
            public const int CodEmissione = 0;
            public static bool? ChiusuraPartita = null;
            public const string TipoAtto = "BOLLETTA";
            public const bool ChiusuraRata = false;
            public const string CausaleRigaTestuale = "TXT";
            public const string CausaleRigaImporto = "IMP";
            public const string DescrizioneRigaImponibile = "Totale imponibile";
        }

        private readonly DateTime _dataEmissione;
        private readonly Istanze _istanza;
        private readonly PeriodoCosapCompleto _periodo;
        private readonly IEnumerable<RataCosap> _rate;

        private readonly string _datiOccupazione;

        private readonly bool _usaAzienda = false;


        public RateizzazioneEmissioniParmaBase(Istanze istanza, PeriodoCosapCompleto periodo, string datiOccupazione, IEnumerable<RataCosap> rate, bool usaAzienda)
            : this(DateTime.Now, istanza, periodo, datiOccupazione, rate, usaAzienda)
        {
        }

        public RateizzazioneEmissioniParmaBase(DateTime dataEmissione, Istanze istanza, PeriodoCosapCompleto periodo, string datiOccupazione, IEnumerable<RataCosap> rate, bool usaAzienda)
        {
            _dataEmissione = dataEmissione;
            _istanza = istanza;
            _periodo = periodo;
            _rate = rate;
            _datiOccupazione = datiOccupazione;
            _usaAzienda = usaAzienda;
        }

        public Emissione[] GetEmissioni()
        {

            return new[]
            {
                GetEmissione()
            };
        }

        public Emissione GetEmissione()
        {
            //var codPartita = $"{this._istanza.IDCOMUNE}-{this._istanza.NUMEROISTANZA}-{this._istanza.DATA.Value.ToString("yyyyMMdd")}";
            var codPartita = $"{this._istanza.NUMEROISTANZA}";
            var emissione = new Emissione
            {
                Id = Constants.Id,
                NumeroRuolo = Constants.NumeroRuolo,
                DataEmissione = this._dataEmissione,
                CodPartita = codPartita,
                AnnoRiferimento = this._istanza.DATA.Value.Year,
                CodServizio = Constants.CodServizio,
                CodEmissione = Constants.CodEmissione,
                ChiusuraPartita = Constants.ChiusuraPartita,
                Descrizione = this.GetStringaDescrizione(),
                Periodo = this._periodo.ToString(),
                TipoAtto = Constants.TipoAtto,
                Fascicolo = (int?)null,
                AnnoFascicolo = (int?)null,
                Sottofascicolo = (int?)null,
                ProtocolloDomanda = (int?)null,
                AnnoProtocolloDomanda = (int?)null,
                ProtocolloAutorizzazione = (int?)null,
                AnnoProtocolloAutorizzazione = (int?)null,
                NumeroRepertorio = (int?)null,
            };

            if (this._usaAzienda)
            {
                PopolaDatiAzienda(emissione);
            }
            else
            {
                PopolaDatiRichiedente(emissione);
            }

            var i = 1;

            emissione.Rate = this._rate.Select(x => new Rata
            {
                Id = (int?)null,
                CodServizio = emissione.CodServizio,
                CodPartita = emissione.CodPartita,
                ChiusuraRata = Constants.ChiusuraRata,
                DataScadenza = x.DataScadenza,
                Importo = x.Importo,
                NumeroRata = i++,
                AnnoRiferimento = emissione.AnnoRiferimento,
                AnnoRiferimentoRata = (int?)null
            }).ToArray();

            emissione.Righe = new[]
            {
                new Riga
                {
                    Id = (int?)null,
                    CodServizio = emissione.CodServizio,
                    CodPartita = emissione.CodPartita,
                    AnnoRiferimento = emissione.AnnoRiferimento,
                    AnnoRiferimento2 = (int?)null,
                    CausaleRiga = Constants.CausaleRigaTestuale,
                    Codicetributo = String.Empty,
                    Imponibile = 0,
                    Imposta = 0,
                    Descrizione = emissione.Descrizione
                },
                new Riga
                {
                    Id = (int?)null,
                    CodServizio = emissione.CodServizio,
                    CodPartita = emissione.CodPartita,
                    AnnoRiferimento = emissione.AnnoRiferimento,
                    AnnoRiferimento2 = (int?)null,
                    CausaleRiga = Constants.CausaleRigaImporto,
                    Codicetributo = String.Empty,
                    Imponibile = this._rate.Sum( x => x.Importo),
                    Imposta = this._rate.Sum( x => x.Importo),
                    Descrizione = Constants.DescrizioneRigaImponibile
                },
            };

            return emissione;
        }

        private void PopolaDatiRichiedente(Emissione emissione)
        {
            emissione.NaturaGiur = this._istanza.Richiedente.TIPOANAGRAFE == "F" ?
                                Constants.NaturaGiuridicaPF :
                                Constants.NaturaGiuridicaPG;
            emissione.Cognome = this._istanza.Richiedente.NOMINATIVO;
            emissione.Nome = this._istanza.Richiedente.NOME;
            emissione.Sesso = this._istanza.Richiedente.SESSO;
            emissione.DataNascita = this._istanza.Richiedente.DATANASCITA;
            emissione.CodComuneNascita = this._istanza.Richiedente.ComuneNascita?.CODICEISTAT;
            emissione.Codfispiva = this._istanza.Richiedente.CODICEFISCALE;
            emissione.Indirizzo = this._istanza.Richiedente.INDIRIZZO;
            emissione.Civico = "";
            emissione.Cap = this._istanza.Richiedente.CAP;
            emissione.CodComuneResidenza = this._istanza.Richiedente.ComuneCorrispondenza?.CODICEISTAT;
            emissione.DesComune = this._istanza.Richiedente.ComuneCorrispondenza?.COMUNE;
        }

        private void PopolaDatiAzienda(Emissione emissione)
        {
            var azienda = this._istanza.AziendaRichiedente;

            emissione.NaturaGiur = azienda.TIPOANAGRAFE == "F" ?
                    Constants.NaturaGiuridicaPF :
                    Constants.NaturaGiuridicaPG;
            emissione.Codfispiva = String.IsNullOrEmpty(azienda.CODICEFISCALE) ? azienda.PARTITAIVA : azienda.CODICEFISCALE;
            emissione.DenominazioneSociale = azienda.NOMINATIVO;
            emissione.Indirizzo = azienda.INDIRIZZO;
            emissione.Civico = "";
            emissione.Cap = azienda.CAP;
            emissione.CodComuneResidenza = azienda.ComuneCorrispondenza?.CODICEISTAT;
            emissione.DesComune = azienda.ComuneCorrispondenza?.COMUNE;
        }

        public DtoOutInserimentoEmissioni Notifica(IPagamentiParmaProxy endpoint)
        {
            string tipologia = IdTipologia.ToString();
            return endpoint.InserisciEmissioniByCodiceAnno(tipologia, this);
        }

        public DtoEsitoPagameti NotificaPagoPa(IPagamentiParmaProxy endpoint)
        {
            int tipologia = IdTipologia;
            return endpoint.InserisciEmissioniAvvisoPagoPA(tipologia, this);
        }

        protected abstract string DecodificaTipoCosap();

        protected abstract int IdTipologia { get; }

        protected abstract string DatiAggiuntivi { get; }


        private string GetStringaDescrizione()
        {
            var sb = new StringBuilder();

            sb.Append(this.DecodificaTipoCosap());
            sb.Append(" ");

            if (this._istanza.StradarioPrimario != null)
            {
                sb.Append("sita in ").Append(this._istanza.StradarioPrimario?.ToString());
                sb.Append(" ");
            }

            if (!String.IsNullOrEmpty(this._periodo.ToString()))
            {
                sb.Append(this._periodo.ToString());
                sb.Append(" ");
            }

            if (!String.IsNullOrEmpty(this._datiOccupazione))
            {
                sb.Append(this._datiOccupazione);
                sb.Append(" ");
            }


            sb.Append("per ")
                .Append(this._istanza.Richiedente.ToString())
                .Append(" (richiedente)");

            if (this._istanza.AziendaRichiedente != null)
            {
                sb.Append($", {this._istanza.AziendaRichiedente?.ToString()}");
            }

            if (!String.IsNullOrEmpty(this.DatiAggiuntivi))
            {
                sb.Append(" ").Append(this.DatiAggiuntivi);
            }

            return sb.ToString();
        }
    }
}
