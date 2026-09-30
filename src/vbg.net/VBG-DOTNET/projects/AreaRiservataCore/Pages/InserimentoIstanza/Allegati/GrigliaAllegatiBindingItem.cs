using AreaRiservataCore.Pages.InserimentoIstanza.Allegati.AllegatoLibero;
using Init.Sigepro.FrontEnd.AppLogic.GestioneConversioneFiles;


namespace AreaRiservataCore.Pages.InserimentoIstanza.Allegati
{
    public class GrigliaAllegatiBindingItem
    {
        public int Id { get; set; }

        public string Descrizione { get; set; }

        public CategoriaBindingItem TipoAllegato { get; set; } = default!;

        public bool Richiesto { get; set; }

        public bool RichiedeFirmaDigitale { get; set; }

        public bool CanDownloadAsPdfCompilabile { get; set; }
        public bool CanDownloadAsPdf { get; set; }
        public bool CanDownloadAsRtf { get; set; }
        public bool CanDownloadAsDoc { get; set; }
        public bool CanDownloadAsOdt { get; set; }
        public bool CanDownloadAsFileSenzaPrecompilazione { get; set; }
        public bool CanDownloadAsFile { get; set; }

        public bool SupportaConversione => this.CanDownloadAsPdf || this.CanDownloadAsPdfCompilabile || this.CanDownloadAsRtf || this.CanDownloadAsRtf || this.CanDownloadAsDoc || this.CanDownloadAsOdt;

        public bool MostraBottoneAllega
        {
            get
            {
                if (this.SoloFirma)
                    return false;

                return !this.CodiceOggetto.HasValue;
            }
        }

        public string NomeFile { get; set; } = "";

        public int? CodiceOggetto { get; set; }

        public int? CodiceOggettoModello { get; set; }

        public int? IdDomanda { get; set; }

        public int Ordine { get; set; }

        public string Note { get; set; } = "";

        public bool FirmatoDigitalmente { get; set; }

        public bool MostraBottonecompila
        {
            get
            {
                if (this.SoloFirma)
                    return false;

                //var setting = ConfigurationManager.AppSettings["attiva.compilazione.documenti.online"];

                //if (String.IsNullOrEmpty(setting) || setting.ToUpper() != "TRUE")
                //    return false;

                return !this.CodiceOggetto.HasValue && (this.CanDownloadAsPdfCompilabile || this.CanDownloadAsDoc || this.CanDownloadAsRtf);
            }
        }

        public bool SoloFirma { get; set; }

        public bool ConsenteDownloadModello
        {
            get
            {
                return this.CanDownloadAsDoc || this.CanDownloadAsOdt || this.CanDownloadAsPdf || this.CanDownloadAsPdfCompilabile || this.CanDownloadAsRtf || this.CanDownloadAsFileSenzaPrecompilazione;
            }
        }

        private string[] _estensioniAmmesse = new string[0];
        public string EstensioniAmmesse
        {
            get { return string.Join(",", this._estensioniAmmesse); }
            set { this._estensioniAmmesse = this.ParseEstensioniAmmesse(value); }
        }
        public int DimensioneMassima { get; set; }

        public FormatoConversioneEnum? FormatoConversione
        {
            get
            {
                if (this.CanDownloadAsPdf)
                    return FormatoConversioneEnum.PDF;

                if (this.CanDownloadAsPdfCompilabile)
                    return FormatoConversioneEnum.PDFC;

                if (this.CanDownloadAsRtf)
                    return FormatoConversioneEnum.RTF;

                if (this.CanDownloadAsDoc)
                    return FormatoConversioneEnum.DOC;

                if (this.CanDownloadAsOdt)
                    return FormatoConversioneEnum.ODT;

                return null;
            }
        }

        private string[] ParseEstensioniAmmesse(string estensioniAmmesse)
        {
            return [.. estensioniAmmesse.Split(',').Select(x => (x.StartsWith('.') ? x.Substring(1) : x).Trim())];
        }
    }
}
