namespace Sigepro.net.WebServices.WsAreaRiservata.WcfServices.EndoFrontoffice
{
    public class RisultatoCaricamentoGerarchiaEndo
    {
        public int Famiglia { get; set; }
        public int Categoria { get; set; }
        public int Endo { get; set; }

        public RisultatoCaricamentoGerarchiaEndo()
        {
            Famiglia = -1;
            Categoria = -1;
            Endo = -1;
        }
    }
}
