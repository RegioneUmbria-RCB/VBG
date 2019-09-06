

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
            AlberoProcEndo c = new AlberoProcEndo();

            c.Idcomune = idcomune;
            c.FkScid = fkscid;
            c.CodiceInventario = codiceInventario;

            return (AlberoProcEndo)db.GetClass(c);
        }

        public List<AlberoProcEndo> GetList(AlberoProcEndo filtro)
        {
            return db.GetClassList(filtro).ToList<AlberoProcEndo>();
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

            sql = PreparaQueryParametrica(sql, "IdComune", "CodiceInventario", "Software");

            bool closeCnn = false;

            try
            {
                if (db.Connection.State == ConnectionState.Closed)
                {
                    db.Connection.Open();
                    closeCnn = true;
                }

                using (IDbCommand cmd = db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(db.CreateParameter("IdComune", idComune));
                    cmd.Parameters.Add(db.CreateParameter("CodiceInventario", codiceInventario));
                    cmd.Parameters.Add(db.CreateParameter("Software", software));

                    return db.GetClassList(cmd, new AlberoProcEndo(), false, true).ToList<AlberoProcEndo>();
                }
            }
            finally
            {
                if (closeCnn)
                    db.Connection.Close();
            }
        }

        public AlberoProcEndo Insert(AlberoProcEndo cls)
        {
            cls = DataIntegrations(cls);

            Validate(cls, AmbitoValidazione.Insert);

            db.Insert(cls);

            cls = (AlberoProcEndo)ChildDataIntegrations(cls);

            ChildInsert(cls);

            return cls;
        }

        public override DataClass ChildDataIntegrations(DataClass cls)
        {
            return cls;
        }

        private AlberoProcEndo ChildInsert(AlberoProcEndo cls)
        {
            return cls;
        }

        private AlberoProcEndo DataIntegrations(AlberoProcEndo cls)
        {
            return cls;
        }


        public AlberoProcEndo Update(AlberoProcEndo cls)
        {
            Validate(cls, AmbitoValidazione.Update);

            db.Update(cls);

            return cls;
        }

        public void Delete(AlberoProcEndo cls)
        {
            VerificaRecordCollegati(cls);

            EffettuaCancellazioneACascata(cls);

            db.Delete(cls);
        }

        private void VerificaRecordCollegati(AlberoProcEndo cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void EffettuaCancellazioneACascata(AlberoProcEndo cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(AlberoProcEndo cls, AmbitoValidazione ambitoValidazione)
        {
            RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


