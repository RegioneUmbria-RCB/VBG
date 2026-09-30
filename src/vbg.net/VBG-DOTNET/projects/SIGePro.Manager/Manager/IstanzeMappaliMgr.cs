using Init.SIGePro.Data;
using Init.SIGePro.Exceptions.IstanzeMappali;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;

namespace Init.SIGePro.Manager
{
    public partial class IstanzeMappaliMgr
    {
        private IstanzeMappali DataIntegrations(IstanzeMappali cls)
        {
            IstanzeMappali retVal = (IstanzeMappali)cls.Clone();

            if (string.IsNullOrEmpty(cls.Codicecatasto) && cls.Catasto != null)
            {
                CatastoMgr mgr = new CatastoMgr(this.db);
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

            this.RequiredFieldValidate(cls, ambitoValidazione);

            this.ForeignValidate(cls);

        }


        private void ForeignValidate(IstanzeMappali cls)
        {
            #region ISTANZEMAPPALI.FKCODICEISTANZA
            if (cls.Fkcodiceistanza.GetValueOrDefault(int.MinValue) != int.MinValue)
            {
                var conditions = new List<KeyValuePair<string, string>>
                {
                    new KeyValuePair<string, string>("CODICEISTANZA", cls.Fkcodiceistanza.ToString()),
                    new KeyValuePair<string, string>("IDCOMUNE", cls.Idcomune)
                };
                if (this.recordCount("ISTANZE", "CODICEISTANZA", conditions) == 0)
                {
                    throw (new RecordNotfoundException(cls, "ISTANZEMAPPALI.FKCODICEISTANZA (" + cls.Fkcodiceistanza + ") non trovato nella tabella ISTANZE"));
                }
            }
            #endregion

            #region ISTANZEMAPPALI.CODICECATASTO
            if (!String.IsNullOrEmpty(cls.Codicecatasto))
            {
                var conditions = new List<KeyValuePair<string, string>>
                {
                    new KeyValuePair<string, string>("CODICE", cls.Codicecatasto)
                };
                if (this.recordCount("CATASTO", "CODICE", conditions) == 0)
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

                sql = String.Format(sql, this.db.Specifics.QueryParameterName("Foglio"),
                                            this.db.Specifics.QueryParameterName("Particella"),
                                            this.db.Specifics.QueryParameterName("Sub"),
                                            this.db.Specifics.QueryParameterName("IdComune"),
                                            this.db.Specifics.QueryParameterName("CodiceIstanza"));

                try
                {
                    if (this.db.Connection.State == ConnectionState.Closed)
                    {
                        this.db.Connection.Open();
                        closeCnn = true;
                    }

                    using (IDbCommand cmd = this.db.CreateCommand(sql))
                    {
                        cmd.Parameters.Add(this.db.CreateParameter("Foglio", String.IsNullOrEmpty(cls.Foglio) ? DBNull.Value : (object)cls.Foglio));
                        cmd.Parameters.Add(this.db.CreateParameter("Particella", String.IsNullOrEmpty(cls.Particella) ? DBNull.Value : (object)cls.Particella));
                        cmd.Parameters.Add(this.db.CreateParameter("Sub", String.IsNullOrEmpty(cls.Sub) ? DBNull.Value : (object)cls.Sub));
                        cmd.Parameters.Add(this.db.CreateParameter("IdComune", cls.Idcomune));
                        cmd.Parameters.Add(this.db.CreateParameter("CodiceIstanza", cls.Fkcodiceistanza));

                        cmd.ExecuteNonQuery();
                    }
                }
                finally
                {
                    if (closeCnn)
                        this.db.Connection.Close();
                }

            }

            return cls;
        }

        public IstanzeMappali GetPrimarioByIdStradario(string idComune, int idStradario)
        {
            var mappali = this.db.GetClassList(new IstanzeMappali
            {
                Idcomune = idComune,
                FkIdIstanzeStradario = idStradario,
                Primario = 1
            });

            if (mappali != null && mappali.Count > 0)
                return mappali.ToList<IstanzeMappali>().First();

            mappali = this.db.GetClassList(new IstanzeMappali
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