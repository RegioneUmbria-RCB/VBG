using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using Init.SIGePro.Manager.Validator;
using System;
using System.Collections.Generic;
using System.Data;

namespace Init.SIGePro.Manager
{
    public partial class CCCoeffContribAttivitaMgr
    {
        public CCCoeffContribAttivita GetByClass(CCCoeffContribAttivita pClass)
        {
            return (CCCoeffContribAttivita)this.db.GetClass(pClass);
        }

        private CCCoeffContribAttivita DataIntegrations(CCCoeffContribAttivita cls)
        {
            if (cls.Coefficiente.GetValueOrDefault(Single.MinValue) == Single.MinValue)
                cls.Coefficiente = 0;

            return cls;
        }

        public CCCoeffContribAttivita Insert(CCCoeffContribAttivita cls)
        {
            throw new NotImplementedException("Il metodo CCCoeffContribAttivitaMgr.Insert non è implementabile. Utilizzare il metodo CCCoeffContribAttivitaMgr.Save");
            /*
            cls = DataIntegrations(cls);

            Validate(cls, AmbitoValidazione.Insert);

            db.Insert(cls);

            cls = (CCCoeffContribAttivita)ChildDataIntegrations(cls);

            ChildInsert(cls);

            return cls;*/
        }

        public CCCoeffContribAttivita Save(CCCoeffContribAttivita cls)
        {
            cls = this.DataIntegrations(cls);

            if (this.Update(cls) == 0)
            {
                this.Validate(cls, AmbitoValidazione.Insert);
                this.db.Insert(cls);

                this.ChildInsert(cls);
            }

            return cls;
        }

        public int Update(CCCoeffContribAttivita cls)
        {
            var retVal = 0;
            var internalOpen = false;

            //TODO: query
            var cmdText = "UPDATE " +
                                "CC_COEFFCONTRIB_ATTIVITA " +
                             "SET " +
                                "COEFFICIENTE = " + cls.Coefficiente.ToString().Replace(",", ".") + " " +
                             "WHERE " +
                                "IDCOMUNE = '" + cls.Idcomune + "' AND " +
                                "SOFTWARE = '" + cls.Software + "' AND " +
                                "FK_CCVC_ID = " + cls.FkCcvcId.ToString() + " AND " +
                                "FK_CCDE_ID = " + cls.FkCcdeId.ToString() + " AND " +
                                "FK_CCCA_ID = " + cls.FkCccaId.ToString();

            if (cls.Id.GetValueOrDefault(int.MinValue) != int.MinValue)
                cmdText += " AND ID = " + cls.Id.Value.ToString();

            if (this.db.Connection.State == ConnectionState.Closed)
            {
                internalOpen = true;
                this.db.Connection.Open();
            }

            using (IDbCommand cmd = this.db.CreateCommand(cmdText))
            {
                retVal = cmd.ExecuteNonQuery();
            }

            if ((this.db.Connection.State == ConnectionState.Open) && internalOpen)
            {
                this.db.Connection.Close();
            }

            return retVal;
        }

        public void Delete(CCCoeffContribAttivita cls)
        {

            this.VerificaRecordCollegati(cls);

            //TODO: query
            var internalOpen = false;
            var cmdText = "DELETE FROM " +
                                "CC_COEFFCONTRIB_ATTIVITA " +
                             "WHERE " +
                                "IDCOMUNE = '" + cls.Idcomune + "' AND " +
                                "SOFTWARE = '" + cls.Software + "' AND " +
                                "FK_CCVC_ID = " + cls.FkCcvcId.ToString();

            if (cls.Id.GetValueOrDefault(int.MinValue) != int.MinValue)
                cmdText += " AND ID = " + cls.Id.ToString();

            if (cls.FkCcdeId.GetValueOrDefault(int.MinValue) != int.MinValue)
                cmdText += " AND FK_CCDE_ID = " + cls.FkCcdeId.ToString();

            if (cls.FkCccaId.GetValueOrDefault(int.MinValue) != int.MinValue)
                cmdText += " AND FK_CCCA_ID = " + cls.FkCccaId.ToString();

            if (this.db.Connection.State == ConnectionState.Closed)
            {
                internalOpen = true;
                this.db.Connection.Open();
            }

            using (IDbCommand cmd = this.db.CreateCommand(cmdText))
            {
                cmd.ExecuteNonQuery();
            }

            if ((this.db.Connection.State == ConnectionState.Open) && internalOpen)
            {
                this.db.Connection.Close();
            }
        }

        public void DeleteById(string idComune, int id)
        {
            CCCoeffContribAttivita cls = new CCCoeffContribAttivita();
            cls.Idcomune = idComune;
            cls.Id = id;

            this.db.Delete(cls);

        }

        private void VerificaRecordCollegati(CCCoeffContribAttivita cls)
        {
            var conditions = new List<KeyValuePair<string, string>>
            {
                new KeyValuePair<string, string>("IDCOMUNE", cls.Idcomune),
                new KeyValuePair<string, string>("FK_CCCCA_ID", cls.Id.ToString())
            };
            if (this.recordCount("CC_ICALCOLO_DCONTRIBATTIV", "FK_CCCCA_ID", conditions) > 0)
                throw new ReferentialIntegrityException("CC_ICALCOLO_DCONTRIBATTIV", "una o più righe con id comune " + cls.Idcomune + " contengono il valore " + cls.Id + " nella colonna FK_CCCCA_ID");
        }

        public void DeleteSingleRow(string idComune, string software, int idValiditaCoefficiente, int idDestinazione, int? idCondizioniAttivita)
        {
            var wasInTransaction = this.db.IsInTransaction;

            try
            {
                if (!wasInTransaction)
                    this.db.BeginTransaction();

                var filtro = new CCCoeffContribAttivita
                {
                    Idcomune = idComune,
                    Software = software,
                    FkCcvcId = idValiditaCoefficiente,
                    FkCcdeId = idDestinazione
                };

                if (idCondizioniAttivita.GetValueOrDefault(int.MinValue) == int.MinValue)
                {
                    filtro.OthersWhereClause.Add("FK_CCCA_ID IS NULL");
                }
                else
                {
                    filtro.OthersWhereClause.Add("FK_CCCA_ID = " + idCondizioniAttivita.Value.ToString());
                }

                var listaCoefficienti = this.GetList(filtro);

                foreach (var coefficiente in listaCoefficienti)
                {
                    this.VerificaRecordCollegati(coefficiente);

                    this.db.Delete(coefficiente);
                }

                if (!wasInTransaction)
                    this.db.CommitTransaction();
            }
            catch (Exception)
            {
                if (!wasInTransaction)
                    this.db.RollbackTransaction();

                throw;
            }
        }
    }
}
