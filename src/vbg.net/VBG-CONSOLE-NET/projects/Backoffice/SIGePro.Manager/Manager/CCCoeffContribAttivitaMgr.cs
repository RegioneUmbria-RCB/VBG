using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Data;
using Init.SIGePro.Validator;
using System.Data;
using Init.SIGePro.Exceptions;

namespace Init.SIGePro.Manager
{
    public partial class CCCoeffContribAttivitaMgr
    {
        public CCCoeffContribAttivita GetByClass(CCCoeffContribAttivita pClass)
        {
            return (CCCoeffContribAttivita)db.GetClass(pClass);
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
            cls = DataIntegrations(cls);

            if (Update(cls) == 0)
            {
                Validate(cls, AmbitoValidazione.Insert);
                db.Insert(cls);
                cls = (CCCoeffContribAttivita)ChildDataIntegrations(cls);

                ChildInsert(cls);
            }

            return cls;
        }

        public int Update(CCCoeffContribAttivita cls)
        {
            int retVal = 0;
            bool internalOpen = false;

            //TODO: query
            string cmdText = "UPDATE " +
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

            if (db.Connection.State == ConnectionState.Closed)
            {
                internalOpen = true;
                db.Connection.Open();
            }

            using (IDbCommand cmd = db.CreateCommand(cmdText))
            {
                retVal = cmd.ExecuteNonQuery();
            }

            if ((db.Connection.State == ConnectionState.Open) && internalOpen)
            {
                db.Connection.Close();
            }

            return retVal;
        }

        public void Delete(CCCoeffContribAttivita cls)
        {

            VerificaRecordCollegati(cls);

            //TODO: query
            bool internalOpen = false;
            string cmdText = "DELETE FROM " +
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

            if (db.Connection.State == ConnectionState.Closed)
            {
                internalOpen = true;
                db.Connection.Open();
            }

            using (IDbCommand cmd = db.CreateCommand(cmdText))
            {
                cmd.ExecuteNonQuery();
            }

            if ((db.Connection.State == ConnectionState.Open) && internalOpen)
            {
                db.Connection.Close();
            }
        }

        public void DeleteById(string idComune, int id)
        {
            CCCoeffContribAttivita cls = new CCCoeffContribAttivita();
            cls.Idcomune = idComune;
            cls.Id = id;

            db.Delete(cls);

        }

        private void VerificaRecordCollegati(CCCoeffContribAttivita cls)
        {
            if (recordCount("CC_ICALCOLO_DCONTRIBATTIV", "FK_CCCCA_ID", "where IDCOMUNE = '" + cls.Idcomune + "' and FK_CCCCA_ID = " + cls.Id.ToString()) > 0)
				throw new ReferentialIntegrityException("CC_ICALCOLO_DCONTRIBATTIV", "una o più righe con id comune " + cls.Idcomune + " contengono il valore " + cls.Id + " nella colonna FK_CCCCA_ID");

        }

        public void DeleteSingleRow(string idComune, string software , int idValiditaCoefficiente, int idDestinazione , int? idCondizioniAttivita)
        {
			var wasInTransaction = db.IsInTransaction;
	
			try
			{
				if(!wasInTransaction)
					db.BeginTransaction();

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

				var listaCoefficienti = GetList(filtro);

				foreach (var coefficiente in listaCoefficienti)
				{
					VerificaRecordCollegati(coefficiente);

					db.Delete(coefficiente);
				}

				if(!wasInTransaction)
					db.CommitTransaction();
			}
			catch (Exception ex)
			{
				if (!wasInTransaction)
					db.RollbackTransaction();

				throw;
			}
        }
    }
}
