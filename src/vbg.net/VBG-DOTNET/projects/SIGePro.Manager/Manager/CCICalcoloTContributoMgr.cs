using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using Init.Utils.Math;

namespace Init.SIGePro.Manager
{
    public partial class CCICalcoloTContributoMgr
    {
        public decimal CalcoloContributo(string idComune, int idCalcoloTot, int codiceIstanza, string stato)
        {
            var c = new CCICalcoloTContributo();
            c.Idcomune = idComune;
            c.FkCcictId = idCalcoloTot;
            c.Codiceistanza = codiceIstanza;
            c.Stato = stato.ToUpper();

            c = (CCICalcoloTContributo)this.db.GetClass(c);

            if (c == null) return 0;

            return c.CostocEdificio.Value * (c.Coefficiente.Value + c.Riduzioneperc.Value);
        }


        private CCICalcoloTContributo DataIntegrations(CCICalcoloTContributo cls)
        {
            var cTot = new CCICalcoloTotMgr(this.db).GetById(cls.Idcomune, cls.FkCcictId.Value);

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
                cls.Calcoli.CostocmqMaggiorato = 0.0m;

            }

            if (cls.Calcoli != null)
            {
                cls.Calcoli.Idcomune = cls.Idcomune;
                cls.Calcoli.Codiceistanza = cls.Codiceistanza;

                var mgrCalcoli = new CCICalcoliMgr(this.db);

                if (cls.Calcoli.Id.GetValueOrDefault(int.MinValue) == int.MinValue)
                    cls.Calcoli = mgrCalcoli.Insert(cls.Calcoli);

                cls.FkCcicId = cls.Calcoli.Id;
            }

            cls.Riduzioneperc = 0.0m;

            return cls;
        }

        public CCICalcoloTContributo Update(CCICalcoloTContributo cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            var mgrTot = new CCICalcoloTotMgr(this.db);

            var cTot = mgrTot.GetById(cls.Idcomune, cls.FkCcictId.Value);
            cTot.QuotacontribTotale = mgrTot.CalcolaQuotaContribTotale(cTot);

            mgrTot.Update(cTot);

            return cls;
        }

        public CCICalcoloTContributo GetByIdCalcolo(string idComune, int idCalcolo)
        {
            var filtro = new CCICalcoloTContributo();
            filtro.Idcomune = idComune;
            filtro.FkCcicId = idCalcolo;

            return (CCICalcoloTContributo)this.db.GetClass(filtro);
        }

        private void EffettuaCancellazioneACascata(CCICalcoloTContributo cls)
        {
            if (cls.Id.HasValue)
            {
                var lCalcoloDAttiv = new CCICalcoloDContribAttivMgr(this.db).GetListByIdTestataContributo(cls.Idcomune, cls.Id.Value);
                var calcoloDContribMgr = new CCICalcoloDContribAttivMgr(this.db);

                foreach (var calcoloDAttiv in lCalcoloDAttiv)
                {
                    calcoloDContribMgr.Delete(calcoloDAttiv);
                }

                var lCalcoloDContrib = new CCICalcoloDContributoMgr(this.db).GetListByIdTestataContributo(cls.Idcomune, cls.Id.Value);
                var calcoloDContributoMgr = new CCICalcoloDContributoMgr(this.db);

                foreach (var calcoloDContrib in lCalcoloDContrib)
                {
                    calcoloDContributoMgr.Delete(calcoloDContrib);
                }

                var riduzioniMgr = new CcICalcoloTContributoRiduzMgr(this.db);
                var listaRiduzioni = riduzioniMgr.GetListByIdTestataContributo(cls.Idcomune, cls.Id.Value);

                foreach (var riduzione in listaRiduzioni)
                {
                    riduzioniMgr.Delete(riduzione);
                }
            }
        }

        public void Delete(CCICalcoloTContributo cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);


            if (cls.FkCcicId.HasValue)
            {
                var c = new CCICalcoli();
                c.Idcomune = cls.Idcomune;
                c.Id = cls.FkCcicId;

                var lCalcoli = new CCICalcoliMgr(this.db).GetList(c);
                foreach (var calcoli in lCalcoli)
                {
                    var mgr = new CCICalcoliMgr(this.db);
                    mgr.Delete(calcoli);
                }
            }
        }

        internal void RicalcolaRiduzioni(string idComune, int idCalcoloTContributo)
        {
            var c = this.GetById(idComune, idCalcoloTContributo);
            var riduzione = new CcICalcoloTContributoRiduzMgr(this.db).GetRiduzioneDaIdContributo(idComune, idCalcoloTContributo);
            c.Riduzioneperc = Arrotondamento.PerEccesso((c.GetQuotaSenzaRiduzioni() / 100.0m) * riduzione, 2);
            c.Noteriduzione = "";
            this.Update(c);
        }
    }
}
