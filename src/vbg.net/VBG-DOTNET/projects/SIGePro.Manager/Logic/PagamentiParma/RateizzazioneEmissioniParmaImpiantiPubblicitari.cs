using Init.SIGePro.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.PagamentiParma
{
    public class RateizzazioneEmissioniParmaImpiantiPubblicitari : RateizzazioneEmissioniParmaBase
    {
        private readonly TipologiaCosapParmaPagoPA _tipologia;
        private readonly string _descrizione;

        public RateizzazioneEmissioniParmaImpiantiPubblicitari(TipologiaCosapParmaPagoPA tipologia, Istanze istanza, string descrizione, IEnumerable<RataCosap> rate, bool usaAzienda)
            :base(istanza, new NullPeriodoCosap(), String.Empty, rate, usaAzienda)
        {
            _tipologia = tipologia;
            _descrizione = descrizione;
        }
        protected override string DecodificaTipoCosap()
        {
            return "Imposta di Pubblicità";
        }

        protected override int IdTipologia => (int)this._tipologia;

        protected override string DatiAggiuntivi => this._descrizione;

    }
}
