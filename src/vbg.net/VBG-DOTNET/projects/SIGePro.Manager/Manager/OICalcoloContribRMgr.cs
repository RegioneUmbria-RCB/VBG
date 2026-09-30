using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using Init.Utils.Math;
using System;
using System.Collections.Generic;
using System.Linq;

namespace Init.SIGePro.Manager
{
    public partial class OICalcoloContribRMgr
    {
        public OICalcoloContribR? GetByContribTTipoOnereDestinazione(string idComune, int idContribt, int idTipoOnere, int idDestinazione)
        {
            FormattableString sql = $"select * from O_ICALCOLOCONTRIBR where IDCOMUNE={idComune} and FK_OICCT_ID={idContribt} and FK_OTO_ID={idTipoOnere} and FK_ODE_ID={idDestinazione}";

            return this.db.GetClassList<OICalcoloContribR>(sql).FirstOrDefault();
        }

        public OICalcoloContribR Update(OICalcoloContribR cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public List<OICalcoloContribR> GetListDaContribT(string idComune, int idContribT)
        {
            var filtro = new OICalcoloContribR();
            filtro.Idcomune = idComune;
            filtro.FkOicctId = idContribT;

            return this.GetList(filtro);
        }

        public void UpdateRiduzioneDaIdDestinazioneTipoOnere(string idComune, int idContribt, int idDestinazione, int idTipoOnere, decimal riduzione)
        {
            var filtro = new OICalcoloContribR();
            filtro.Idcomune = idComune;
            filtro.FkOicctId = idContribt;
            filtro.FkOdeId = idDestinazione;
            filtro.FkOtoId = idTipoOnere;

            var cls = (OICalcoloContribR)this.db.GetClass(filtro);

            if (cls == null)
                throw new ArgumentException("Impossibile trovare OICalcoloContribR per idContribt=" + idContribt + ", IdTipoOnere=" + idTipoOnere + ", idDestinazione=" + idDestinazione);

            cls.Riduzione = riduzione;

            this.RicalcolaRiduzioniECostoTotale(cls, false);
        }

        private void EffettuaCancellazioneACascata(OICalcoloContribR cls)
        {
            var riduzMgr = new OICalcoloContribRRiduzMgr(this.db);

            var lst = riduzMgr.GetListaRiduzioniDaContribR(cls);

            foreach (var rid in lst)
                riduzMgr.Delete(rid);

        }

        /// <summary>
        /// Ricalcola il totale della percentuale di riduzione, il totale diduzione e il costo totale dell'onere
        /// </summary>
        /// <remarks>
        /// Effettua anche il salvataggio della riga
        /// </remarks>
        /// <param name="cls"></param>
        public void RicalcolaRiduzioniECostoTotale(OICalcoloContribR cls, bool ricalcolaRiduzione)
        {
            var ridMgr = new OICalcoloContribRRiduzMgr(this.db);

            cls.Riduzioneperc = cls.Id.HasValue ? ridMgr.CalcolaTotaleRiduzioni(cls) : 0.0m;

            if (ricalcolaRiduzione)
            {
                var riduzione = (cls.Costom.GetValueOrDefault(0.0m) / 100.0m) * cls.Riduzioneperc.GetValueOrDefault(0.0m);

                cls.Riduzione = Arrotondamento.PerEccesso(riduzione, 2);
            }

            var costoTot = cls.SuperficieCubatura.GetValueOrDefault(0.0m) * (cls.Costom.GetValueOrDefault(0.0m) + cls.Riduzione.GetValueOrDefault(0.0m));
            cls.Costotot = Arrotondamento.PerEccesso(costoTot, 2);

            if (!cls.Id.HasValue)
                this.Insert(cls);
            else
                this.Update(cls);

            var tmgr = new OICalcoloContribTMgr(this.db);
            var testata = tmgr.GetById(cls.Idcomune!, cls.FkOicctId!.Value);

            var idComune = testata.Idcomune;
            var idContribT = testata.Id!.Value;
            var codiceIstanza = testata.Codiceistanza!.Value;

            tmgr.ElaboraBto(idComune, idContribT, codiceIstanza);
        }



    }
}
