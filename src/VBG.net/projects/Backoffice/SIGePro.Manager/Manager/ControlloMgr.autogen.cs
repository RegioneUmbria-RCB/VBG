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
    /// File generato automaticamente dalla tabella CONTROLLO per la classe Controllo il 30/07/2008 16.33.27
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
    public partial class ControlloMgr : BaseManager
    {
        public ControlloMgr(DataBase dataBase) : base(dataBase) { }

        public Controllo GetById(int codiceistanza, string idcomune)
        {
            Controllo c = new Controllo();


            c.Codiceistanza = codiceistanza;
            c.Idcomune = idcomune;

            return (Controllo)db.GetClass(c);
        }

        public List<Controllo> GetList(int codiceistanza, DateTime dataconvocazione, string odg, string note, string idcomune)
        {
            Controllo c = new Controllo();
            c.Codiceistanza = codiceistanza;
            c.Dataconvocazione = dataconvocazione;
            if (!String.IsNullOrEmpty(odg)) c.Odg = odg;
            if (!String.IsNullOrEmpty(note)) c.Note = note;
            if (!String.IsNullOrEmpty(idcomune)) c.Idcomune = idcomune;


            return db.GetClassList(c).ToList<Controllo>();
        }

        public List<Controllo> GetList(Controllo filtro)
        {
            return db.GetClassList(filtro).ToList<Controllo>();
        }

        public Controllo Insert(Controllo cls)
        {
            cls = DataIntegrations(cls);

            Validate(cls, AmbitoValidazione.Insert);

            db.Insert(cls);

            cls = (Controllo)ChildDataIntegrations(cls);

            ChildInsert(cls);

            return cls;
        }

        public override DataClass ChildDataIntegrations(DataClass cls)
        {
            return cls;
        }

        private Controllo ChildInsert(Controllo cls)
        {
            return cls;
        }

        private Controllo DataIntegrations(Controllo cls)
        {
            return cls;
        }


        public Controllo Update(Controllo cls)
        {
            Validate(cls, AmbitoValidazione.Update);

            db.Update(cls);

            return cls;
        }

        public void Delete(Controllo cls)
        {
            VerificaRecordCollegati(cls);

            EffettuaCancellazioneACascata(cls);

            db.Delete(cls);
        }

        private void VerificaRecordCollegati(Controllo cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void EffettuaCancellazioneACascata(Controllo cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(Controllo cls, AmbitoValidazione ambitoValidazione)
        {
            RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


