using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella DYN2_BASETIPITESTO per la classe Dyn2BaseTipiTesto il 05/08/2008 16.49.58
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
    public partial class Dyn2BaseTipiTestoMgr : BaseManager
    {
        public Dyn2BaseTipiTestoMgr(DataBase dataBase) : base(dataBase) { }

        public Dyn2BaseTipiTesto GetById(string id)
        {
            var c = new Dyn2BaseTipiTesto();


            c.Id = id;

            return (Dyn2BaseTipiTesto)this.db.GetClass(c);
        }

        public List<Dyn2BaseTipiTesto> GetList(Dyn2BaseTipiTesto filtro)
        {
            return this.db.GetClassList(filtro).ToList<Dyn2BaseTipiTesto>();
        }

        public Dyn2BaseTipiTesto Insert(Dyn2BaseTipiTesto cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }



        private Dyn2BaseTipiTesto ChildInsert(Dyn2BaseTipiTesto cls)
        {
            return cls;
        }

        private Dyn2BaseTipiTesto DataIntegrations(Dyn2BaseTipiTesto cls)
        {
            return cls;
        }


        public Dyn2BaseTipiTesto Update(Dyn2BaseTipiTesto cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(Dyn2BaseTipiTesto cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void VerificaRecordCollegati(Dyn2BaseTipiTesto cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void EffettuaCancellazioneACascata(Dyn2BaseTipiTesto cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(Dyn2BaseTipiTesto cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


