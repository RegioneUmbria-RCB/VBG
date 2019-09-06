using System;
using System.Linq;
using System.Collections;
using System.Data;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions.IstanzeMappali;
using PersonalLib2.Data;
using Init.SIGePro.Validator;
using System.Collections.Generic;

namespace Init.SIGePro.Manager 
{ 	
	public partial class IstanzeMappaliMgr
	{
        private IstanzeMappali DataIntegrations(IstanzeMappali cls)
        {
            IstanzeMappali retVal = (IstanzeMappali)cls.Clone();

            if (string.IsNullOrEmpty(cls.Codicecatasto) && cls.Catasto != null)
            { 
                CatastoMgr mgr = new CatastoMgr( this.db );
                cls.Codicecatasto = mgr.GetByClass(cls.Catasto).CODICE;
            }

            return retVal;
        }

		private void Validate(IstanzeMappali cls, AmbitoValidazione ambitoValidazione)
		{
            if (string.IsNullOrEmpty(cls.Codicecatasto))
                cls.Codicecatasto = "F";

            if (cls.Primario.GetValueOrDefault(int.MinValue) == int.MinValue)
				cls.Primario = 0;

			if (cls.Primario != 0 && cls.Primario != 1)
				throw (new TypeMismatchException(cls, "Impossibile inserire" + cls.Primario + " in ISTANZEMAPPALI.PRIMARIO"));

			RequiredFieldValidate(cls, ambitoValidazione);

			ForeignValidate(cls);

		}


		private void ForeignValidate(IstanzeMappali cls)
		{
			#region ISTANZEMAPPALI.FKCODICEISTANZA
            if (cls.Fkcodiceistanza.GetValueOrDefault(int.MinValue) != int.MinValue)
			{
				if (this.recordCount("ISTANZE", "CODICEISTANZA", "WHERE CODICEISTANZA = '" + cls.Fkcodiceistanza + "' AND IDCOMUNE = '" + cls.Idcomune + "'") == 0)
				{
					throw (new RecordNotfoundException(cls, "ISTANZEMAPPALI.FKCODICEISTANZA (" + cls.Fkcodiceistanza + ") non trovato nella tabella ISTANZE"));
				}
			}
			#endregion

			#region ISTANZEMAPPALI.CODICECATASTO
			if (!String.IsNullOrEmpty(cls.Codicecatasto))
			{
				if (this.recordCount("CATASTO", "CODICE", "WHERE CODICE = '" + cls.Codicecatasto + "'") == 0)
				{
					throw (new RecordNotfoundException(cls, "ISTANZEMAPPALI.CODICECATASTO (" + cls.Codicecatasto + ") non trovato nella tabella CATASTO"));
				}
			}
			#endregion
		}


		private IstanzeMappali ChildDataIntegrations(IstanzeMappali cls)
		{
			if (cls.Primario == 1)
			{
				bool closeCnn = false;

				string sql = "UPDATE ISTANZE SET FOGLIO = {0}, PARTICELLA = {1}, SUB = {2} WHERE IDCOMUNE = {3} AND CODICEISTANZA = {4}";

				sql = String.Format(sql, db.Specifics.QueryParameterName("Foglio"),
											db.Specifics.QueryParameterName("Particella"),
											db.Specifics.QueryParameterName("Sub"),
											db.Specifics.QueryParameterName("IdComune"),
											db.Specifics.QueryParameterName("CodiceIstanza"));

				try
				{
					if (db.Connection.State == ConnectionState.Closed)
					{
						db.Connection.Open();
						closeCnn = true;
					}

					using (IDbCommand cmd = db.CreateCommand(sql))
					{
						cmd.Parameters.Add( db.CreateParameter("Foglio" , String.IsNullOrEmpty(cls.Foglio) ? DBNull.Value : (object)cls.Foglio ) );
						cmd.Parameters.Add(db.CreateParameter("Particella", String.IsNullOrEmpty(cls.Particella) ? DBNull.Value : (object)cls.Particella));
						cmd.Parameters.Add(db.CreateParameter("Sub", String.IsNullOrEmpty(cls.Sub) ? DBNull.Value : (object)cls.Sub));
						cmd.Parameters.Add(db.CreateParameter("IdComune", cls.Idcomune ));
						cmd.Parameters.Add(db.CreateParameter("CodiceIstanza", cls.Fkcodiceistanza ));

						cmd.ExecuteNonQuery();
					}
				}
				finally
				{
					if (closeCnn)
						db.Connection.Close();
				}

			}

			return cls;
		}

		public IstanzeMappali GetPrimarioByIdStradario(string idComune, int idStradario)
		{
			var mappali = db.GetClassList(new IstanzeMappali
			{
				Idcomune = idComune,
				FkIdIstanzeStradario = idStradario,
				Primario = 1
			});

			if (mappali != null && mappali.Count > 0)
				return mappali.ToList<IstanzeMappali>().First();

			mappali = db.GetClassList(new IstanzeMappali
			{
				Idcomune = idComune,
				FkIdIstanzeStradario = idStradario
			});

			if (mappali != null && mappali.Count > 0)
				return mappali.ToList<IstanzeMappali>().First();

			return null;
		}

	}
}