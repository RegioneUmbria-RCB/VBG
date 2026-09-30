

using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella O_CLASSIADDETTI per la classe OClassiAddetti il 27/06/2008 13.01.35
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
    public partial class OClassiAddettiMgr : BaseManager
    {
        public OClassiAddettiMgr(DataBase dataBase) : base(dataBase) { }

        public OClassiAddetti GetById(string idcomune, int id)
        {
            var c = new OClassiAddetti();


            c.Idcomune = idcomune;
            c.Id = id;

            return (OClassiAddetti)this.db.GetClass(c);
        }

        public List<OClassiAddetti> GetList(string idcomune, int id, string classe, string software)
        {
            var c = new OClassiAddetti();
            if (!String.IsNullOrEmpty(idcomune)) c.Idcomune = idcomune;
            c.Id = id;
            if (!String.IsNullOrEmpty(classe)) c.Classe = classe;
            if (!String.IsNullOrEmpty(software)) c.Software = software;


            return this.db.GetClassList(c).ToList<OClassiAddetti>();
        }

        public List<OClassiAddetti> GetList(OClassiAddetti filtro)
        {
            return this.db.GetClassList(filtro).ToList<OClassiAddetti>();
        }

        public OClassiAddetti Insert(OClassiAddetti cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }



        private OClassiAddetti ChildInsert(OClassiAddetti cls)
        {
            return cls;
        }

        private OClassiAddetti DataIntegrations(OClassiAddetti cls)
        {
            return cls;
        }


        public OClassiAddetti Update(OClassiAddetti cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(OClassiAddetti cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void EffettuaCancellazioneACascata(OClassiAddetti cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }

        private void Validate(OClassiAddetti cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


