
using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using PersonalLib2.Sql;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
    public partial class CcICalcoloTContributoRiduzMgr
    {
        public class ImportoRiduzione
        {
            public int IdRiduzione { get; set; }
            public string Descrizione { get; set; }
            public bool Selezionato { get; set; }
            public double ImportoCausale { get; set; }
            public double Importo { get; set; }
            public string Note { get; set; }
        }

        private readonly DataBase _db;

        public CcICalcoloTContributoRiduzMgr(DataBase dataBase) { this._db = dataBase; }

        //public CcICalcoloTContributoRiduz GetById(string idcomune, int id)
        //{
        //    var c = new CcICalcoloTContributoRiduz();

        //    c.Idcomune = idcomune;
        //    c.Id = id;

        //    return (CcICalcoloTContributoRiduz)this.db.GetClass(c);
        //}

        public List<CcICalcoloTContributoRiduz> GetList(CcICalcoloTContributoRiduz filtro)
        {
            return this._db.GetClassList(filtro).ToList<CcICalcoloTContributoRiduz>();
        }

        private bool Validate(CcICalcoloTContributoRiduz cls, AmbitoValidazione ambitoValidazione)
        {
            var pValidator = new ClassValidator(cls);

            return pValidator.RequiredFieldValidator(this._db, ambitoValidazione);
        }

        // TODO: ottimizzare con una sola query
        public IEnumerable<ImportoRiduzione> GetImportiRiduzioni(string idComune, int idTipoCausale, int idTContributo)
        {
            FormattableString sql = $@"
            select 
                CC_CAUSALIRIDUZIONIR.id as RIDUZIONE_ID,
                CC_CAUSALIRIDUZIONIR.descrizione as RIDUZIONE_DESCR,
                CC_CAUSALIRIDUZIONIR.RiduzionePerc as RIDUZIONE_PERC
            from 
                CC_CAUSALIRIDUZIONIR 
            where 
                idcomune = {idComune} and 
                FK_CCCRT_ID = {idTipoCausale} 
            order by descrizione asc";

            var riduzioni = this._db.ExecuteReader(sql, dr => new ImportoRiduzione
            {
                IdRiduzione = dr.GetInt("RIDUZIONE_ID").Value,
                Descrizione = dr.GetString("RIDUZIONE_DESCR"),
                Selezionato = false,
                ImportoCausale = dr.GetDouble("RIDUZIONE_PERC").GetValueOrDefault(0),
                Importo = dr.GetDouble("RIDUZIONE_PERC").GetValueOrDefault(0),
                Note = ""
            }).ToArray();

            foreach (var riduzione in riduzioni)
            {
                var idRiduzione = riduzione.IdRiduzione;

                sql = $@"SELECT
							riduzioneperc,
							note
						FROM
							cc_icalcoloTcontributo_riduz
						WHERE
							cc_icalcoloTcontributo_riduz.idcomune = {idComune} AND
							cc_icalcoloTcontributo_riduz.fk_ccictc_id = {idTContributo} AND
							cc_icalcoloTcontributo_riduz.fk_cccrr_id = {idRiduzione}";

                var risultato = this._db.ExecuteReader(sql,
                                    dr => new
                                    {
                                        Importo = Convert.ToDouble(dr["riduzioneperc"]),
                                        Note = dr["note"].ToString()
                                    })
                                    .FirstOrDefault();

                if (risultato != null)
                {
                    riduzione.Selezionato = true;
                    riduzione.Importo = risultato.Importo;
                    riduzione.Note = risultato.Note;
                }
            }

            return riduzioni;
        }

        public List<CcICalcoloTContributoRiduz> GetListByIdTestataContributoUseForeign(string idComune, int idTestata)
        {
            FormattableString sql = $@"
                SELECT *
                FROM CC_ICALCOLOTCONTRIBUTO_RIDUZ
                WHERE IDCOMUNE = {idComune} AND FK_CCICTC_ID = {idTestata}";

            return this._db.GetClassList<CcICalcoloTContributoRiduz>(sql, new GetClassListFlags(useForeignEnum.Yes));
        }

        internal IEnumerable<CcICalcoloTContributoRiduz> GetListByIdTestataContributo(string idcomune, int idTestata)
        {
            FormattableString sql = $@"
                SELECT *
                FROM CC_ICALCOLOTCONTRIBUTO_RIDUZ
                WHERE IDCOMUNE = {idcomune} AND FK_CCICTC_ID = {idTestata}";

            return this._db.GetClassList<CcICalcoloTContributoRiduz>(sql);
        }

        public decimal GetRiduzioneDaIdContributo(string idComune, int idTContributo)
        {
            var riduzioni = this.GetListByIdTestataContributo(idComune, idTContributo);

            var ret = 0.0m;

            foreach (var r in riduzioni)
                ret += r.Riduzioneperc.GetValueOrDefault(0);

            return ret;
        }

        public void Delete(CcICalcoloTContributoRiduz cls)
        {
            this._db.Delete(cls);

            this.OnPostDelete(cls);
        }

        public CcICalcoloTContributoRiduz Insert(CcICalcoloTContributoRiduz cls, bool ignoraRicalcoloPostInsert = false)
        {
            this.Validate(cls, AmbitoValidazione.Insert);

            this._db.Insert(cls);

            if (!ignoraRicalcoloPostInsert)
            {
                this.OnPostInsert(cls);
            }

            return cls;
        }

        //public CcICalcoloTContributoRiduz Update(CcICalcoloTContributoRiduz cls)
        //{
        //    this.Validate(cls, AmbitoValidazione.Update);

        //    this.db.Update(cls);

        //    this.OnPostUpdate(cls);

        //    return cls;
        //}

        private void OnPostInsert(CcICalcoloTContributoRiduz cls)
        {
            new CCICalcoloTContributoMgr(this._db).RicalcolaRiduzioni(cls.Idcomune, cls.FkCcictcId.Value);
        }

        private void OnPostDelete(CcICalcoloTContributoRiduz cls)
        {
            new CCICalcoloTContributoMgr(this._db).RicalcolaRiduzioni(cls.Idcomune, cls.FkCcictcId.Value);
        }

        private void OnPostUpdate(CcICalcoloTContributoRiduz cls)
        {
            new CCICalcoloTContributoMgr(this._db).RicalcolaRiduzioni(cls.Idcomune, cls.FkCcictcId.Value);
        }


        public void AggiornaRiduzioniByIdTContributo(string idComune, int idTContributo, IEnumerable<CcICalcoloTContributoRiduz> listaRiduzioni)
        {
            try
            {
                this._db.BeginTransaction();

                // Elimino tutte le righe di o_icalcolocontribr_riduz collegate al ContribTAttuale
                FormattableString deleteSql = $@"
                    DELETE 
                    FROM 
                        CC_ICALCOLOTCONTRIBUTO_RIDUZ 
                    WHERE 
                        IDCOMUNE = {idComune} AND 
                        FK_CCICTC_ID = {idTContributo}";

                this._db.ExecuteNonQuery(deleteSql);

                foreach (var riduzione in listaRiduzioni)
                {
                    riduzione.FkCcictcId = idTContributo;
                    riduzione.Idcomune = idComune;

                    this.Insert(riduzione, ignoraRicalcoloPostInsert: true);
                }

                // Ricalcolo le riduzioni sul contributo
                new CCICalcoloTContributoMgr(this._db).RicalcolaRiduzioni(idComune, idTContributo);

                this._db.CommitTransaction();
            }
            catch (Exception)
            {
                this._db.RollbackTransaction();

                throw;
            }
        }


    }
}
