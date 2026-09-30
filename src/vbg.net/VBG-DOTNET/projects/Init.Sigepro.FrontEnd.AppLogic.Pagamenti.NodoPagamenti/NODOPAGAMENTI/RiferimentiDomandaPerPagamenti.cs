using VBG.Pagamenti.NodoPagamenti.Shared;

namespace Init.Sigepro.FrontEnd.AppLogic.Pagamenti.NodoPagamenti.NODOPAGAMENTI
{
    internal class RiferimentiDomandaPerPagamenti : IRiferimentiDomandaPerPagamenti
    {
        public string IdComune { get; set; }

        public string Software { get; set; }

        public int IdPresentazione { get; set; }

        public string CodiceUnivocoDomanda { get; set; }
    }
}
