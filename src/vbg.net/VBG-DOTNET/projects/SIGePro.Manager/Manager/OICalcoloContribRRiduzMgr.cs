
using Init.SIGePro.Data;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Linq;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
    public partial class OICalcoloContribRRiduzMgr
    {
        #region gestione dei callback per gli eventi del database
        public override void RegisterHandlers()
        {
            this.Deleted += new DeletedDelegate(this.OnDeleted);
            this.Inserted += new InsertedDelegate(this.OnInserted);
            this.Updated += new UpdatedDelegate(this.OnUpdated);
        }

        private void OnUpdated(OICalcoloContribRRiduz cls)
        {
            this.RicalcolaTotaleContribR(cls);
        }

        private void OnInserted(OICalcoloContribRRiduz cls)
        {
            this.RicalcolaTotaleContribR(cls);
        }

        private void OnDeleted(OICalcoloContribRRiduz cls)
        {
            this.RicalcolaTotaleContribR(cls);
        }

        private void RicalcolaTotaleContribR(OICalcoloContribRRiduz cls)
        {
            var contribrMgr = new OICalcoloContribRMgr(this.db);
            contribrMgr.RicalcolaRiduzioniECostoTotale(contribrMgr.GetById(cls.Idcomune, cls.FkOiccrId.GetValueOrDefault(int.MinValue)), true);
        }

        #endregion


        internal decimal CalcolaTotaleRiduzioni(OICalcoloContribR contribR)
        {
            var l = this.GetListaRiduzioniDaContribR(contribR);

            return l.Sum(x => x.Riduzioneperc.GetValueOrDefault(0.0m));
        }

        public DataSet GetRiduzioniPerTipoCausale(string idComune, int idContribT, int idTipoDestinazione, int idTipoCausale)
        {
            // Preparo il dataset
            var ds = new DataSet();
            var dt = new DataTable();

            dt.Columns.Add(new DataColumn("idCausale", typeof(int)));
            dt.Columns.Add(new DataColumn("descrizioneCausale", typeof(string)));
            dt.Columns.Add(new DataColumn("percentuale", typeof(double)));

            var tipiOnereTmp = new OICalcoloContribTMgr(this.db).GetListaTipiOnere(idComune, idContribT);
            var tipiOnere = new List<int>();

            foreach (var idTipoOnere in tipiOnereTmp)
            {
                var rigaRiduzione = new OICalcoloContribRMgr(this.db).GetByContribTTipoOnereDestinazione(idComune, idContribT, idTipoOnere, idTipoDestinazione);

                if (rigaRiduzione!.Riduzioneperc == 0 && rigaRiduzione!.Riduzione != 0.0m) continue;

                tipiOnere.Add(idTipoOnere);
            }

            foreach (var idTipoOnere in tipiOnere)
            {
                dt.Columns.Add(new DataColumn(idTipoOnere.ToString() + "_selezionato", typeof(bool)));
                dt.Columns.Add(new DataColumn(idTipoOnere.ToString() + "_percentuale", typeof(double)));
                dt.Columns.Add(new DataColumn(idTipoOnere.ToString() + "_note", typeof(string)));
            }

            ds.Tables.Add(dt);

            // Leggo la lista delle riduzioni presenti per il tipo causale passato
            var listaRiduzioni = new OCausaliRiduzioniRMgr(this.db).GetListByIdTipoCausale(idComune, idTipoCausale);

            var contribRMgr = new OICalcoloContribRMgr(this.db);

            foreach (var riduzione in listaRiduzioni)
            {
                var dr = dt.NewRow();

                dr["idCausale"] = riduzione.Id;
                dr["descrizioneCausale"] = riduzione.Descrizione;
                dr["percentuale"] = riduzione.Riduzioneperc;

                foreach (var idTipoOnere in tipiOnere)
                {
                    var contribR = contribRMgr.GetByContribTTipoOnereDestinazione(idComune, idContribT, idTipoOnere, idTipoDestinazione);
                    var contribRRiduz = this.GetByIdContribRTipoCausale(idComune, contribR.Id.GetValueOrDefault(int.MinValue), riduzione.Id.GetValueOrDefault(int.MinValue));

                    dr[idTipoOnere.ToString() + "_selezionato"] = contribRRiduz != null;
                    dr[idTipoOnere.ToString() + "_percentuale"] = contribRRiduz != null ? contribRRiduz.Riduzioneperc : 0.0d;
                    dr[idTipoOnere.ToString() + "_note"] = contribRRiduz != null ? contribRRiduz.Note : String.Empty;
                }

                dt.Rows.Add(dr);
            }


            return ds;
        }

        public OICalcoloContribRRiduz GetByIdContribRTipoCausale(string idComune, int idContribr, int idCausale)
        {
            var filtro = new OICalcoloContribRRiduz();
            filtro.Idcomune = idComune;
            filtro.FkOiccrId = idContribr;
            filtro.FkOcrrId = idCausale;

            return this.db.GetClass(filtro);
        }

        public List<OICalcoloContribRRiduz> GetRiduzioniDaContribtDestinazioneTipoOnere(string idComune, int idContribt, int idDestinazione, int idTipoOnere)
        {
            FormattableString sql = $@"SELECT  o_icalcolocontribr_riduz.*
							            FROM    o_icalcolocontribr,
									            o_icalcolocontribr_riduz
							            WHERE   o_icalcolocontribr_riduz.idcomune    = o_icalcolocontribr.idcomune
								            AND o_icalcolocontribr_riduz.fk_oiccr_id = o_icalcolocontribr.id
								            AND o_icalcolocontribr.idcomune			 = {idComune}
								            AND o_icalcolocontribr.fk_oto_id         = {idTipoOnere}
								            AND o_icalcolocontribr.fk_ode_id         = {idDestinazione}
								            AND o_icalcolocontribr.fk_oicct_id       = {idContribt}";

            return this.db.GetClassList<OICalcoloContribRRiduz>(sql, new GetClassListFlags
            {
                UseForeign = PersonalLib2.Sql.useForeignEnum.Yes,
                SingleRowException = false
            });


        }

        public List<OICalcoloContribRRiduz> GetListaRiduzioniDaContribR(OICalcoloContribR contribR)
        {
            var filtro = new OICalcoloContribRRiduz();
            filtro.Idcomune = contribR.Idcomune;
            filtro.FkOiccrId = contribR.Id;

            return this.GetList(filtro);
        }
    }
}
