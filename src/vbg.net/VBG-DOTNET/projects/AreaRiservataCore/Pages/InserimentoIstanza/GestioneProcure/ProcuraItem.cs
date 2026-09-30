namespace AreaRiservataCore.Pages.InserimentoIstanza.GestioneProcure
{
    public class ProcuraItem
    {
        public int? IdDomanda { get; set; }
        public string? CodiceFiscaleProcuratore { get; set; }
        public string? CodiceFiscaleProcurato { get; set; }
        public string? NomeProcurato { get; set; }
        public string? NomeProcuratore { get; set; }
        public int? CodiceOggetto { get; set; }
        public bool AllegatoPresente { get; set; }
        // public string? PathDownload { get; set; }
        public string? NomeFile { get; set; }
        public bool IsFirmatoDigitalmente { get; set; }
        public bool RichiedeFirmaDigitale { get; set; }
        public bool RichiedeCaricamentoDocumentoIdentita { get; set; }
        public int? DocIdentitaCodiceOggetto { get; set; }
        public bool DocIdentitaPresente { get; set; }
        // public string? DocIdentitaPathDownload { get; set; }
        public string? DocIdentitaNomeFile { get; set; }

        public bool AllegatoNecessitaFirma
        {
            get
            {
                return this.AllegatoPresente && this.RichiedeFirmaDigitale && !this.IsFirmatoDigitalmente;
            }
        }
    }
}
