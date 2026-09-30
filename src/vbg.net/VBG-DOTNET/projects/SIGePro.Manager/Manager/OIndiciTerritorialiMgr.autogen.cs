

using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella O_INDICITERRITORIALI per la classe OIndiciTerritoriali il 27/06/2008 13.01.36
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
    public partial class OIndiciTerritorialiMgr : BaseManager
    {
        public OIndiciTerritorialiMgr(DataBase dataBase) : base(dataBase) { }

        public OIndiciTerritoriali GetById(string idcomune, int id)
        {
            var c = new OIndiciTerritoriali();


            c.Idcomune = idcomune;
            c.Id = id;

            return (OIndiciTerritoriali)this.db.GetClass(c);
        }

        public List<OIndiciTerritoriali> GetList(string idcomune, int id, float dtz, float ift, float iff, string software)
        {
            var c = new OIndiciTerritoriali();
            if (!String.IsNullOrEmpty(idcomune)) c.Idcomune = idcomune;
            c.Id = id;
            c.Dtz = dtz;
            c.Ift = ift;
            c.Iff = iff;
            if (!String.IsNullOrEmpty(software)) c.Software = software;


            return this.db.GetClassList(c).ToList<OIndiciTerritoriali>();
        }

        public List<OIndiciTerritoriali> GetList(OIndiciTerritoriali filtro)
        {
            return this.db.GetClassList(filtro).ToList<OIndiciTerritoriali>();
        }

        public OIndiciTerritoriali Insert(OIndiciTerritoriali cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }



        private OIndiciTerritoriali ChildInsert(OIndiciTerritoriali cls)
        {
            return cls;
        }

        private OIndiciTerritoriali DataIntegrations(OIndiciTerritoriali cls)
        {
            return cls;
        }


        public OIndiciTerritoriali Update(OIndiciTerritoriali cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(OIndiciTerritoriali cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void EffettuaCancellazioneACascata(OIndiciTerritoriali cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(OIndiciTerritoriali cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


