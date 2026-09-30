using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella MERCATI_D_CONTI per la classe Mercati_DConti il 27/03/2009 11.58.29
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
    public partial class Mercati_DContiMgr : BaseManager
    {
        public Mercati_DContiMgr(DataBase dataBase) : base(dataBase) { }

        public Mercati_DConti GetById(int id, string idcomune)
        {
            var c = new Mercati_DConti();


            c.Id = id;
            c.Idcomune = idcomune;

            return (Mercati_DConti)this.db.GetClass(c);
        }

        public List<Mercati_DConti> GetList(Mercati_DConti filtro)
        {
            return this.db.GetClassList(filtro).ToList<Mercati_DConti>();
        }

        public Mercati_DConti Insert(Mercati_DConti cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }



        private Mercati_DConti ChildInsert(Mercati_DConti cls)
        {
            return cls;
        }

        private Mercati_DConti DataIntegrations(Mercati_DConti cls)
        {
            return cls;
        }


        public Mercati_DConti Update(Mercati_DConti cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(Mercati_DConti cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void VerificaRecordCollegati(Mercati_DConti cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void EffettuaCancellazioneACascata(Mercati_DConti cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }
    }
}


