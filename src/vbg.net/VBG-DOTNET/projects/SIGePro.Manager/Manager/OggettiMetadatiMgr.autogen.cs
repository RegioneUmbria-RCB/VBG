

using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
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
        private readonly DataBase _database;
        public OggettiMetadatiMgr(DataBase dataBase) : base(dataBase)
        {
            this._database = dataBase;
        }

        public OggettiMetadati GetById(string idcomune, int? codiceoggetto, string chiave)
        {
            var c = new OggettiMetadati();


            c.Idcomune = idcomune;
            c.Codiceoggetto = codiceoggetto;
            c.Chiave = chiave;

            return (OggettiMetadati)this.db.GetClass(c);
        }

        public IEnumerable<OggettiMetadati> GetMetadatiVerificaFirma(string idComune, int codiceoggetto)
        {
            var chiavi = new string[] { "FIRMA_DIGITALE_PRESENTE", "FIRMA_DIGITALE_VALIDA", "FIRMA_DIGITALE_NUMERO_FIRME", "FIRMA_DIGITALE_FIRMATARI" };


            var sql = $@"SELECT 
								CHIAVE, VALORE 
							FROM 
								OGGETTI_METADATI 
							WHERE 
								IDCOMUNE = {this._database.Specifics.QueryParameterName("IdComune")} AND 
								CODICEOGGETTO = {this._database.Specifics.QueryParameterName("CodiceOggetto")} AND 
								CHIAVE IN ('{String.Join("','", chiavi)}')";

            return this._database.ExecuteReader(sql, mp =>
            {
                mp.AddParameter("IdComune", idComune);
                mp.AddParameter("CodiceOggetto", codiceoggetto);
            },
            dr => new OggettiMetadati
            {
                Chiave = dr.GetString("CHIAVE"),
                Codiceoggetto = codiceoggetto,
                Idcomune = idComune,
                Valore = dr.GetString("VALORE")
            });

        }

        public List<OggettiMetadati> GetList(OggettiMetadati filtro)
        {
            return this.db.GetClassList(filtro).ToList<OggettiMetadati>();
        }

        public OggettiMetadati Insert(OggettiMetadati cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

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
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

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

            sql = String.Format(sql, this._database.Specifics.QueryParameterName("IdComune"),
                                     this._database.Specifics.QueryParameterName("CodiceIstanza"),
                                     this._database.Specifics.QueryParameterName("Chiave"),
                                     this._database.Specifics.QueryParameterName("Valore"));

            bool closecnn = false;

            if (this._database.Connection.State == ConnectionState.Closed)
            {
                this._database.Connection.Open();
                closecnn = true;
            }
            try
            {
                using (IDbCommand cmd = this._database.CreateCommand(sql))
                {
                    cmd.Parameters.Add(this._database.CreateParameter("IdComune", idComune));
                    cmd.Parameters.Add(this._database.CreateParameter("CodiceIstanza", codiceIstanza));
                    cmd.Parameters.Add(this._database.CreateParameter("Chiave", chiave));
                    cmd.Parameters.Add(this._database.CreateParameter("Valore", valore));

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
                    this._database.Connection.Close();
            }
        }


        public void Delete(OggettiMetadati cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void VerificaRecordCollegati(OggettiMetadati cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void EffettuaCancellazioneACascata(OggettiMetadati cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(OggettiMetadati cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


