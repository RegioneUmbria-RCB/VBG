

using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Manager;
using PersonalLib2.Data;
using Init.SIGePro.Data;
using Init.SIGePro.Validator;
using PersonalLib2.Sql;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella RAGGRUPPAMENTOCAUSALIONERI per la classe RaggruppamentoCausaliOneri il 03/09/2008 9.29.28
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
    public partial class RaggruppamentoCausaliOneriMgr : BaseManager
    {
        public RaggruppamentoCausaliOneriMgr(DataBase dataBase) : base(dataBase) { }

        public RaggruppamentoCausaliOneri GetById(string idcomune, int rco_id)
        {
            RaggruppamentoCausaliOneri c = new RaggruppamentoCausaliOneri();


            c.Idcomune = idcomune;
            c.RcoId = rco_id;

            return (RaggruppamentoCausaliOneri)db.GetClass(c);
        }

        public List<RaggruppamentoCausaliOneri> GetList(RaggruppamentoCausaliOneri filtro)
        {
            return db.GetClassList(filtro).ToList<RaggruppamentoCausaliOneri>();
        }

        public RaggruppamentoCausaliOneri Insert(RaggruppamentoCausaliOneri cls)
        {
            cls = DataIntegrations(cls);

            Validate(cls, AmbitoValidazione.Insert);

            db.Insert(cls);

            cls = (RaggruppamentoCausaliOneri)ChildDataIntegrations(cls);

            ChildInsert(cls);

            return cls;
        }

        public override DataClass ChildDataIntegrations(DataClass cls)
        {
            return cls;
        }

        private RaggruppamentoCausaliOneri ChildInsert(RaggruppamentoCausaliOneri cls)
        {
            return cls;
        }

        private RaggruppamentoCausaliOneri DataIntegrations(RaggruppamentoCausaliOneri cls)
        {
            return cls;
        }


        public RaggruppamentoCausaliOneri Update(RaggruppamentoCausaliOneri cls)
        {
            Validate(cls, AmbitoValidazione.Update);

            db.Update(cls);

            return cls;
        }

        public void Delete(RaggruppamentoCausaliOneri cls)
        {
            VerificaRecordCollegati(cls);

            EffettuaCancellazioneACascata(cls);

            db.Delete(cls);
        }

        private void VerificaRecordCollegati(RaggruppamentoCausaliOneri cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void EffettuaCancellazioneACascata(RaggruppamentoCausaliOneri cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(RaggruppamentoCausaliOneri cls, AmbitoValidazione ambitoValidazione)
        {
            RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


