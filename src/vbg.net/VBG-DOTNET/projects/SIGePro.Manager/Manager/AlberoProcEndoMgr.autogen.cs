using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;
using System.Data;

namespace Init.SIGePro.Manager
{
    ///
    /// File generato automaticamente dalla tabella ALBEROPROC_ENDO per la classe AlberoprocAREndo il 29/08/2011 16.45.07
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
    public partial class AlberoProcEndoMgr : BaseManager
    {
        public AlberoProcEndoMgr(DataBase dataBase) : base(dataBase) { }

        public AlberoProcEndo GetById(string idcomune, int fkscid, int codiceInventario)
        {
            var c = new AlberoProcEndo();

            c.Idcomune = idcomune;
            c.FkScid = fkscid;
            c.CodiceInventario = codiceInventario;

            return this.db.GetClass(c);
        }

        public List<AlberoProcEndo> GetList(AlberoProcEndo filtro)
        {
            return this.db.GetClassList(filtro).ToList<AlberoProcEndo>();
        }

        public List<AlberoProcEndo> GetBySoftwareeInventario(string idComune, string software, int codiceInventario)
        {
            string sql = @"SELECT alberoproc_endo.*
                            FROM alberoproc_endo 
                            INNER JOIN alberoproc 
                            ON alberoproc_endo.idcomune = alberoproc.idcomune 
                            AND alberoproc_endo.fkscid = alberoproc.sc_id
                            WHERE alberoproc_endo.idcomune = {0} 
                            AND alberoproc_endo.codiceinventario = {1} 
                            AND alberoproc.software = {2}";

            sql = this.PreparaQueryParametrica(sql, "IdComune", "CodiceInventario", "Software");

            bool closeCnn = false;

            try
            {
                if (this.db.Connection.State == ConnectionState.Closed)
                {
                    this.db.Connection.Open();
                    closeCnn = true;
                }

                using (IDbCommand cmd = this.db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(this.db.CreateParameter("IdComune", idComune));
                    cmd.Parameters.Add(this.db.CreateParameter("CodiceInventario", codiceInventario));
                    cmd.Parameters.Add(this.db.CreateParameter("Software", software));

                    return this.db.GetClassList<AlberoProcEndo>(cmd, new GetClassListFlags
                    {
                        UseForeign = PersonalLib2.Sql.useForeignEnum.No,
                        SingleRowException = false
                    });
                }
            }
            finally
            {
                if (closeCnn)
                    this.db.Connection.Close();
            }
        }

        public AlberoProcEndo Insert(AlberoProcEndo cls)
        {
            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            return cls;
        }

        public AlberoProcEndo Update(AlberoProcEndo cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(AlberoProcEndo cls)
        {
            this.db.Delete(cls);
        }

        private void Validate(AlberoProcEndo cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


