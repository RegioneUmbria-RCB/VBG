using Init.SIGePro.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.PagamentiParma
{
    public class RateizzazioneEmissioniParmaPagoPA : RateizzazioneEmissioniParmaBase
    {
        private readonly TipologiaCosapParmaPagoPA _tipologia;

        public RateizzazioneEmissioniParmaPagoPA(TipologiaCosapParmaPagoPA tipologia, Istanze istanza, PeriodoCosap periodo, int totaleGiorni, string datiOccupazione, IEnumerable<RataCosap> rate, bool usaAzienda) 
            : base(istanza, new PeriodoCosapCompleto(periodo, totaleGiorni), datiOccupazione, rate, usaAzienda)
        {
            _tipologia = tipologia;
        }

        public RateizzazioneEmissioniParmaPagoPA(TipologiaCosapParmaPagoPA tipologia, DateTime dataEmissione, Istanze istanza, PeriodoCosap periodo, int totaleGiorni, string datiOccupazione, IEnumerable<RataCosap> rate, bool usaAzienda) 
            : base(dataEmissione, istanza, new PeriodoCosapCompleto(periodo, totaleGiorni), datiOccupazione, rate, usaAzienda)
        {
            _tipologia = tipologia;
        }

        protected override string DecodificaTipoCosap()
        {
            switch (this._tipologia)
            {
                case TipologiaCosapParmaPagoPA.Cosap:
                    return "Cosap";

                case TipologiaCosapParmaPagoPA.CosapDehors:
                    return "Cosap Dehors";

                case TipologiaCosapParmaPagoPA.CosapGenerica:
                    return "Cosap Generica";

                case TipologiaCosapParmaPagoPA.MezziPubblicitari:
                    return "Mezzi pubblicitari";

                default:
                    return this._tipologia.ToString();
            }
        }

        protected override int IdTipologia => (int)this._tipologia;

        protected override string DatiAggiuntivi => String.Empty;
    }
}
