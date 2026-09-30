
			
using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Manager;
using PersonalLib2.Data;
using Init.SIGePro.Data;

using Init.SIGePro.Validator;
using PersonalLib2.Sql;
using System.Data;

namespace Init.SIGePro.Manager
{

	///
	/// File generato automaticamente dalla tabella OGGETTI_METADATI per la classe OggettiMetadati il 24/05/2013 16.50.59
	///
	///						ELENCARE DI SEGUITO EVENTUALI MODIFICHE APPORTATE MANUALMENTE ALLA CLASSE
	///				(per tenere traccia dei cambiamenti nel caso in cui la classe debba essere generata di nuovo)
	/// -
	/// -
	/// -
	/// - 
	///
	///	Prima di effettuare modifiche al template di MyGeneration in caso di dubbi contattare Nicola Gargagli ;)
	///
	public partial class OggettiMetadatiMgr : BaseManager
	{
        DataBase _database;
		public OggettiMetadatiMgr(DataBase dataBase) : base(dataBase) 
        {
            _database = dataBase;
        }

		public OggettiMetadati GetById(string idcomune, int? codiceoggetto, string chiave)
		{
			OggettiMetadati c = new OggettiMetadati();
			
			
			c.Idcomune = idcomune;
			c.Codiceoggetto = codiceoggetto;
			c.Chiave = chiave;
			
			return (OggettiMetadati)db.GetClass(c);
		}

		public List<OggettiMetadati> GetList(OggettiMetadati filtro)
		{
			return db.GetClassList( filtro ).ToList< OggettiMetadati>();
		}

		public OggettiMetadati Insert(OggettiMetadati cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (OggettiMetadati)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private OggettiMetadati ChildInsert(OggettiMetadati cls)
		{
			return cls;
		}

		private OggettiMetadati DataIntegrations(OggettiMetadati cls)
		{
			return cls;
		}
		

		public OggettiMetadati Update(OggettiMetadati cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

        public int? TrovaRiepilogoDomanda(int codiceIstanza, string idComune, string chiave, string valore)
        {
            int? retVal = (int?)null;

            string sql = @"SELECT OGGETTI_METADATI.CODICEOGGETTO 
                            FROM OGGETTI_METADATI, 
                                DOCUMENTIISTANZA 
                            WHERE DOCUMENTIISTANZA.IDCOMUNE = OGGETTI_METADATI.IDCOMUNE  
                            AND DOCUMENTIISTANZA.CODICEOGGETTO = OGGETTI_METADATI.CODICEOGGETTO 
                            AND OGGETTI_METADATI.IDCOMUNE = {0}                            
                            AND DOCUMENTIISTANZA.CODICEISTANZA = {1} 
                            AND OGGETTI_METADATI.CHIAVE = {2}
                            AND OGGETTI_METADATI.VALORE = {3}";

            sql = String.Format(sql, _database.Specifics.QueryParameterName("IdComune"),
                                     _database.Specifics.QueryParameterName("CodiceIstanza"),
                                     _database.Specifics.QueryParameterName("Chiave"),
                                     _database.Specifics.QueryParameterName("Valore"));

            bool closecnn = false;

            if (_database.Connection.State == ConnectionState.Closed)
            {
                _database.Connection.Open();
                closecnn = true;
            }
            try
            {
                using (IDbCommand cmd = _database.CreateCommand(sql))
                {
                    cmd.Parameters.Add(_database.CreateParameter("IdComune", idComune));
                    cmd.Parameters.Add(_database.CreateParameter("CodiceIstanza", codiceIstanza));
                    cmd.Parameters.Add(_database.CreateParameter("Chiave", chiave));
                    cmd.Parameters.Add(_database.CreateParameter("Valore", valore));

                    using (IDataReader dr = cmd.ExecuteReader())
                    {
                        while (dr.Read())
                            retVal = Convert.ToInt32(dr["CODICEOGGETTO"].ToString());
                        
                        return retVal;
                    }
                }
            }
            finally
            {
                if (closecnn)
                    _database.Connection.Close();
            }
        }


		public void Delete(OggettiMetadati cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(OggettiMetadati cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(OggettiMetadati cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(OggettiMetadati cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			