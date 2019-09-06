using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Data;
using Init.SIGePro.Validator;
using Init.Utils.Math;

namespace Init.SIGePro.Manager
{
    public partial class CCICalcoloTContributoMgr
    {
        public double CalcoloContributo(string idComune, int idCalcoloTot, int codiceIstanza, string stato)
        {
            CCICalcoloTContributo c = new CCICalcoloTContributo();
            c.Idcomune = idComune;
            c.FkCcictId = idCalcoloTot;
            c.Codiceistanza = codiceIstanza;
            c.Stato = stato.ToUpper();
			
            c = (CCICalcoloTContributo)db.GetClass(c);

            if (c == null) return 0;

            return c.CostocEdificio.Value * (c.Coefficiente.Value + c.Riduzioneperc.Value);
        }


        private CCICalcoloTContributo DataIntegrations(CCICalcoloTContributo cls)
        {
            CCICalcoloTot cTot = new CCICalcoloTotMgr(db).GetById(cls.Idcomune, cls.FkCcictId.Value);

            if (cls.Calcoli == null && (cTot.FkBcctcId == "M1" || cTot.FkBcctcId == "M12"))
            {
                cls.Calcoli = new CCICalcoli();

                cls.Calcoli.Sa =
                cls.Calcoli.Sc =
                cls.Calcoli.Snr =
                cls.Calcoli.St =
                cls.Calcoli.Su =
                cls.Calcoli.SuArt9 =
                cls.Calcoli.I1 =
                cls.Calcoli.I2 =
                cls.Calcoli.I3 =
                cls.Calcoli.Maggiorazione =
                cls.Calcoli.Costocmq =
                cls.Calcoli.CostocmqMaggiorato = 0.0f;

            }

            if (cls.Calcoli != null)
            {
                cls.Calcoli.Idcomune = cls.Idcomune;
                cls.Calcoli.Codiceistanza = cls.Codiceistanza;

                CCICalcoliMgr mgrCalcoli = new CCICalcoliMgr(db);

                if (cls.Calcoli.Id.GetValueOrDefault(int.MinValue) == int.MinValue)
                    cls.Calcoli = mgrCalcoli.Insert(cls.Calcoli);

                cls.FkCcicId = cls.Calcoli.Id;
            }

			cls.Riduzioneperc = 0.0d;

            return cls;
        }

        public CCICalcoloTContributo Update(CCICalcoloTContributo cls)
        {
            Validate(cls, AmbitoValidazione.Update);

            db.Update(cls);

            CCICalcoloTotMgr mgrTot = new CCICalcoloTotMgr(db);

            CCICalcoloTot cTot = mgrTot.GetById(cls.Idcomune, cls.FkCcictId.Value);
            cTot.QuotacontribTotale = mgrTot.CalcolaQuotaContribTotale(cTot);

            mgrTot.Update(cTot);

            return cls;
        }

        public CCICalcoloTContributo GetByIdCalcolo(string idComune, int idCalcolo)
        {
            CCICalcoloTContributo filtro = new CCICalcoloTContributo();
            filtro.Idcomune = idComune;
            filtro.FkCcicId = idCalcolo;

            return (CCICalcoloTContributo)db.GetClass(filtro);
        }

        private void EffettuaCancellazioneACascata(CCICalcoloTContributo cls)
        {
            CCICalcoloDContribAttiv a = new CCICalcoloDContribAttiv();
            a.Idcomune = cls.Idcomune;
            a.FkCcictcId = cls.Id;

            List<CCICalcoloDContribAttiv> lCalcoloDAttiv = new CCICalcoloDContribAttivMgr(db).GetList(a);
            foreach (CCICalcoloDContribAttiv calcoloDAttiv in lCalcoloDAttiv)
            {
                CCICalcoloDContribAttivMgr mgr = new CCICalcoloDContribAttivMgr(db);
                mgr.Delete(calcoloDAttiv);
            }

            CCICalcoloDContributo b = new CCICalcoloDContributo();
            b.Idcomune = cls.Idcomune;
            b.FkCcictcId = cls.Id;

            List<CCICalcoloDContributo> lCalcoloDContrib = new CCICalcoloDContributoMgr(db).GetList(b);
            foreach (CCICalcoloDContributo calcoloDContrib in lCalcoloDContrib)
            {
                CCICalcoloDContributoMgr mgr = new CCICalcoloDContributoMgr(db);
                mgr.Delete(calcoloDContrib);
            }


			var filtroRiduzioni = new CcICalcoloTContributoRiduz
			{
				Idcomune = cls.Idcomune,
				FkCcictcId = cls.Id
			};
			var riduzioniMgr = new CcICalcoloTContributoRiduzMgr(db);
			var listaRiduzioni = riduzioniMgr.GetList(filtroRiduzioni);

			foreach (var riduzione in listaRiduzioni)
			{
				riduzioniMgr.Delete(riduzione);
			}
        }

		public void Delete(CCICalcoloTContributo cls)
		{
			VerificaRecordCollegati(cls);

			EffettuaCancellazioneACascata(cls);

			db.Delete(cls);


			if (cls.FkCcicId.HasValue)
			{
				CCICalcoli c = new CCICalcoli();
				c.Idcomune = cls.Idcomune;
				c.Id = cls.FkCcicId;

				List<CCICalcoli> lCalcoli = new CCICalcoliMgr(db).GetList(c);
				foreach (CCICalcoli calcoli in lCalcoli)
				{
					CCICalcoliMgr mgr = new CCICalcoliMgr(db);
					mgr.Delete(calcoli);
				}
			}
		}

		internal void RicalcolaRiduzioni(string idComune, int idCalcoloTContributo)
		{
			CCICalcoloTContributo c = GetById(idComune, idCalcoloTContributo);
			double riduzione = new CcICalcoloTContributoRiduzMgr(db).GetRiduzioneDaIdContributo(idComune, idCalcoloTContributo);
			c.Riduzioneperc = Arrotondamento.PerEccesso( (c.GetQuotaSenzaRiduzioni() / 100.0d) * riduzione , 2 );
			c.Noteriduzione = "";
			Update(c);
		}
    }
}
