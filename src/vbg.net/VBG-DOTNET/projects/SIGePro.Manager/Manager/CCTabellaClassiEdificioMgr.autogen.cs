

using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Linq;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella CC_TABELLA_CLASSIEDIFICIO per la classe CCTabellaClassiEdificio il 27/06/2008 13.01.40
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
    public partial class CCTabellaClassiEdificioMgr : BaseManager
    {
        public CCTabellaClassiEdificioMgr(DataBase dataBase) : base(dataBase) { }

        public CCTabellaClassiEdificio GetById(string idcomune, int id)
        {
            FormattableString sql = $"select * from CC_TABELLA_CLASSIEDIFICIO where IDCOMUNE = {idcomune} and ID = {id}";

            return this.db.GetClassList<CCTabellaClassiEdificio>(sql).FirstOrDefault();
        }

        public List<CCTabellaClassiEdificio> GetList(string idcomune, int id, string descrizione, int da, int a, decimal maggiorazione, string software)
        {
            var c = new CCTabellaClassiEdificio();
            if (!String.IsNullOrEmpty(idcomune)) c.Idcomune = idcomune;
            c.Id = id;
            if (!String.IsNullOrEmpty(descrizione)) c.Descrizione = descrizione;
            c.Da = da;
            c.A = a;
            c.Maggiorazione = maggiorazione;
            if (!String.IsNullOrEmpty(software)) c.Software = software;


            return this.db.GetClassList(c).ToList<CCTabellaClassiEdificio>();
        }

        public List<CCTabellaClassiEdificio> GetList(CCTabellaClassiEdificio filtro)
        {
            return this.db.GetClassList(filtro).ToList<CCTabellaClassiEdificio>();
        }

        public CCTabellaClassiEdificio Insert(CCTabellaClassiEdificio cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }



        private CCTabellaClassiEdificio ChildInsert(CCTabellaClassiEdificio cls)
        {
            return cls;
        }

        private CCTabellaClassiEdificio DataIntegrations(CCTabellaClassiEdificio cls)
        {
            return cls;
        }


        public CCTabellaClassiEdificio Update(CCTabellaClassiEdificio cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(CCTabellaClassiEdificio cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void EffettuaCancellazioneACascata(CCTabellaClassiEdificio cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(CCTabellaClassiEdificio cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


