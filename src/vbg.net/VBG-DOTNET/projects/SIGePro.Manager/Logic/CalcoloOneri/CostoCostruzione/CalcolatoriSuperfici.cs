using Init.SIGePro.Data;
using Init.Utils.Math;
using System;
using System.Collections.Generic;

namespace Init.SIGePro.Manager.Logic.CalcoloOneri.CostoCostruzione
{
    public class CalcolatoreSuperficiBase
    {
        protected List<CCICalcoliDettaglioT> m_superficiAbitabili;

        public decimal SuperficieTotale()
        {
            var alloggi = 0;
            var su = 0.0m;

            this.SuperficieEAlloggiNellIntervallo(int.MinValue, int.MaxValue, out alloggi, out su);

            return Arrotondamento.PerEccesso(su, 2);
        }

        public void SuperficieEAlloggiNellIntervallo(int iMin, int iMax, out int alloggi, out decimal superficieTot)
        {
            var fMin = (decimal)iMin;
            var fMax = (decimal)iMax;

            var iAlloggi = 0;
            var fSu = 0.0m;

            this.m_superficiAbitabili.ForEach(delegate (CCICalcoliDettaglioT supa)
            {
                if (supa.Su > fMin && supa.Su <= fMax)
                {
                    var numeroAlloggi = supa.Alloggi.GetValueOrDefault(int.MinValue) == int.MinValue ? 1 : supa.Alloggi.Value;

                    fSu += (supa.Su.GetValueOrDefault(0) * numeroAlloggi);
                    iAlloggi += numeroAlloggi;
                }
            });

            alloggi = iAlloggi;
            superficieTot = Arrotondamento.PerEccesso(fSu, 2);
        }
    }

    public class CalcolatoreSuperficiPerTipo : CalcolatoreSuperficiBase
    {
        public CalcolatoreSuperficiPerTipo(CCICalcoliDettaglioTMgr mgr, string idComune, int idCalcolo, int idSuperficie)
        {
            if (idSuperficie == int.MinValue)
                throw new ArgumentException("Non è possibile eseguire i calcoli perchè il codice del tipo superficie non è valido.");

            var filtroDt = new CCICalcoliDettaglioT();
            filtroDt.Idcomune = idComune;
            filtroDt.FkCcicId = idCalcolo;
            filtroDt.FkCctsId = idSuperficie;

            this.m_superficiAbitabili = mgr.GetList(filtroDt);
        }
    }

    public class CalcolatoreSuperficiPerDettaglio : CalcolatoreSuperficiBase
    {
        public CalcolatoreSuperficiPerDettaglio(CCICalcoliDettaglioTMgr mgr, string idComune, int idCalcolo, int idDettaglioSuperficie)
        {
            if (idDettaglioSuperficie == int.MinValue)
                throw new ArgumentException("Non è possibile eseguire i calcoli perchè non è stato stabilito in configurazione il tipo di dettaglio di superficie.");

            var filtroDt = new CCICalcoliDettaglioT();
            filtroDt.Idcomune = idComune;
            filtroDt.FkCcicId = idCalcolo;
            filtroDt.FkCcdsId = idDettaglioSuperficie;

            this.m_superficiAbitabili = mgr.GetList(filtroDt);
        }
    }
}
