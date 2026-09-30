using Init.Sigepro.FrontEnd.AppLogic.StcService;
using System.Linq;

namespace Init.Sigepro.FrontEnd.AppLogic.InvioDomanda
{


    public partial class InvioIstanzaResult : IInvioIstanzaResult
    {

        public TipoEsitoInvio Esito { get; private set; }
        public string CodiceIstanza { get; private set; }
        public string NumeroIstanza { get; private set; }
        public string UuId { get; }

        public static IInvioIstanzaResult InvioFallito()
        {
            return new InvioIstanzaResult(TipoEsitoInvio.InvioFallito, null);
        }

        public static IInvioIstanzaResult InserimentoFallito()
        {
            return new InvioIstanzaResult(TipoEsitoInvio.InserimentoFallito, null);
        }

        public static IInvioIstanzaResult InvioRiuscito(RiferimentiPraticaType riferimentiPraticaType)
        {
            return new InvioIstanzaResult(TipoEsitoInvio.InvioRiuscito, riferimentiPraticaType);
        }

        public static IInvioIstanzaResult InvioRiuscitoNoBackend(RiferimentiPraticaType riferimentiPraticaType)
        {
            return new InvioIstanzaResult(TipoEsitoInvio.InvioRiuscitoNoBackend, riferimentiPraticaType);
        }

        protected InvioIstanzaResult(TipoEsitoInvio esito, RiferimentiPraticaType riferimentiPraticaType)
        {
            this.Esito = esito;
            this.CodiceIstanza = (riferimentiPraticaType == null) ? "" : riferimentiPraticaType.idPratica;
            this.NumeroIstanza = (riferimentiPraticaType == null) ? "" : riferimentiPraticaType.numeroPratica;
            this.UuId = riferimentiPraticaType?.altriDati?.Where(x => x.nome == "$UUID_ISTANZA$").SelectMany(x => x.valore).FirstOrDefault()?.codice;
        }
    }
}
