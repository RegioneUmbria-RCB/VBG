using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Data;
using Init.SIGePro.Manager.WsParmaPagamenti;

namespace Init.SIGePro.Manager.Logic.PagamentiParma
{
    public class RateizzazioneEmissioniParma : RateizzazioneEmissioniParmaBase
    {

        private readonly TipologiaCosapParma _tipologia;

        public RateizzazioneEmissioniParma(TipologiaCosapParma tipologia, Istanze istanza, PeriodoCosap periodo, int totaleGiorni, string datiOccupazione, IEnumerable<RataCosap> rate)
            :base(DateTime.Now, istanza, new PeriodoCosapCompleto(periodo, totaleGiorni), datiOccupazione, rate, false)
        {
            this._tipologia = tipologia;
        }

        public RateizzazioneEmissioniParma(TipologiaCosapParma tipologia, DateTime dataEmissione, Istanze istanza, PeriodoCosap periodo, int totaleGiorni, string datiOccupazione, IEnumerable<RataCosap> rate)
            : base(dataEmissione, istanza, new PeriodoCosapCompleto(periodo, totaleGiorni), datiOccupazione, rate, false)
        {
            this._tipologia = tipologia;
        }

        protected override int IdTipologia => (int)this._tipologia;

        protected override string DatiAggiuntivi => String.Empty;

        protected override string DecodificaTipoCosap()
        {
            switch(this._tipologia)
            {
                case TipologiaCosapParma.Cosap:
                    return "Cosap";

                case TipologiaCosapParma.CosapDehors:
                    return "Cosap Dehors";

                case TipologiaCosapParma.CosapGenerica:
                    return "Cosap Generica";

                default:
                    return this._tipologia.ToString();
            }
        }


    }
}
