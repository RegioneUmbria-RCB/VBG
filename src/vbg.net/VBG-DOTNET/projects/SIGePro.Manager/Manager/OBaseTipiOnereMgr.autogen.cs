

using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella O_BASETIPIONERE per la classe OBaseTipiOnere il 27/06/2008 13.01.35
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
    public partial class OBaseTipiOnereMgr : BaseManager
    {
        public OBaseTipiOnereMgr(DataBase dataBase) : base(dataBase) { }

        public OBaseTipiOnere GetById(string id)
        {
            var c = new OBaseTipiOnere();


            c.Id = id;

            return (OBaseTipiOnere)this.db.GetClass(c);
        }

        public List<OBaseTipiOnere> GetList(string id, string descrizione)
        {
            var c = new OBaseTipiOnere();
            if (!String.IsNullOrEmpty(id)) c.Id = id;
            if (!String.IsNullOrEmpty(descrizione)) c.Descrizione = descrizione;


            return this.db.GetClassList(c).ToList<OBaseTipiOnere>();
        }

        public List<OBaseTipiOnere> GetList(OBaseTipiOnere filtro)
        {
            return this.db.GetClassList(filtro).ToList<OBaseTipiOnere>();
        }

        public OBaseTipiOnere Insert(OBaseTipiOnere cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }



        private OBaseTipiOnere ChildInsert(OBaseTipiOnere cls)
        {
            return cls;
        }

        private OBaseTipiOnere DataIntegrations(OBaseTipiOnere cls)
        {
            return cls;
        }


        public OBaseTipiOnere Update(OBaseTipiOnere cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(OBaseTipiOnere cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void VerificaRecordCollegati(OBaseTipiOnere cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void EffettuaCancellazioneACascata(OBaseTipiOnere cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(OBaseTipiOnere cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


