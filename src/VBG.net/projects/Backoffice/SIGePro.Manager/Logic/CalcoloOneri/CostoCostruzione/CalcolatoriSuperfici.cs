using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using Init.Utils.Math;

namespace Init.SIGePro.Manager.Logic.CalcoloOneri.CostoCostruzione
{
	public class CalcolatoreSuperficiBase
	{
		protected List<CCICalcoliDettaglioT> m_superficiAbitabili;

		public double SuperficieTotale()
		{
			int alloggi = 0;
			double su = 0;

			SuperficieEAlloggiNellIntervallo(int.MinValue, int.MaxValue, out alloggi, out su);

			return Arrotondamento.PerEccesso(su, 2);
		}

		public void SuperficieEAlloggiNellIntervallo(int iMin, int iMax, out int alloggi, out double superficieTot)
		{
			float fMin = (float)iMin;
			float fMax = (float)iMax;

			int iAlloggi = 0;
			double fSu = 0.0f;

			m_superficiAbitabili.ForEach(delegate(CCICalcoliDettaglioT supa)
			{
				if (supa.Su > fMin && supa.Su <= fMax)
				{
                    int numeroAlloggi = supa.Alloggi.GetValueOrDefault(int.MinValue) == int.MinValue ? 1 : supa.Alloggi.Value;

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

			CCICalcoliDettaglioT filtroDt = new CCICalcoliDettaglioT();
			filtroDt.Idcomune = idComune;
			filtroDt.FkCcicId = idCalcolo;
			filtroDt.FkCctsId = idSuperficie;

			m_superficiAbitabili = mgr.GetList(filtroDt);
		}
	}

	public class CalcolatoreSuperficiPerDettaglio : CalcolatoreSuperficiBase
	{
		public CalcolatoreSuperficiPerDettaglio(CCICalcoliDettaglioTMgr mgr, string idComune, int idCalcolo, int idDettaglioSuperficie)
		{
			if (idDettaglioSuperficie == int.MinValue)
				throw new ArgumentException("Non è possibile eseguire i calcoli perchè non è stato stabilito in configurazione il tipo di dettaglio di superficie.");

			CCICalcoliDettaglioT filtroDt = new CCICalcoliDettaglioT();
			filtroDt.Idcomune = idComune;
			filtroDt.FkCcicId = idCalcolo;
			filtroDt.FkCcdsId = idDettaglioSuperficie;

			m_superficiAbitabili = mgr.GetList(filtroDt);
		}
	}
}
