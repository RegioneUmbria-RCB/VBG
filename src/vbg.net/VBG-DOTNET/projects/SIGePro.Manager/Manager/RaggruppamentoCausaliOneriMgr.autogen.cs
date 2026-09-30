using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;

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
            var c = new RaggruppamentoCausaliOneri();


            c.Idcomune = idcomune;
            c.RcoId = rco_id;

            return (RaggruppamentoCausaliOneri)this.db.GetClass(c);
        }

        public List<RaggruppamentoCausaliOneri> GetList(RaggruppamentoCausaliOneri filtro)
        {
            return this.db.GetClassList(filtro).ToList<RaggruppamentoCausaliOneri>();
        }

        public RaggruppamentoCausaliOneri Insert(RaggruppamentoCausaliOneri cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

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
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(RaggruppamentoCausaliOneri cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
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
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


