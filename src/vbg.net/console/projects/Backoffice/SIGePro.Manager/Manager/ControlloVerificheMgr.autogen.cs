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
    /// File generato automaticamente dalla tabella CONTROLLOVERIFICHE per la classe ControlloVerifiche il 30/07/2008 16.34.43
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
    public partial class ControlloVerificheMgr : BaseManager
    {
        public ControlloVerificheMgr(DataBase dataBase) : base(dataBase) { }

        public ControlloVerifiche GetById(int codiceistanza, int codiceinventario, string idcomune)
        {
            ControlloVerifiche c = new ControlloVerifiche();


            c.Codiceistanza = codiceistanza;
            c.Codiceinventario = codiceinventario;
            c.Idcomune = idcomune;

            return (ControlloVerifiche)db.GetClass(c);
        }

        public List<ControlloVerifiche> GetList(int codiceistanza, int codiceinventario, DateTime data, string parere, int esito, string note, string fileverbale, int codiceoggetto, string idcomune)
        {
            ControlloVerifiche c = new ControlloVerifiche();
            c.Codiceistanza = codiceistanza;
            c.Codiceinventario = codiceinventario;
            c.Data = data;
            if (!String.IsNullOrEmpty(parere)) c.Parere = parere;
            c.Esito = esito;
            if (!String.IsNullOrEmpty(note)) c.Note = note;
            if (!String.IsNullOrEmpty(fileverbale)) c.Fileverbale = fileverbale;
            c.Codiceoggetto = codiceoggetto;
            if (!String.IsNullOrEmpty(idcomune)) c.Idcomune = idcomune;


            return db.GetClassList(c).ToList<ControlloVerifiche>();
        }

        public List<ControlloVerifiche> GetList(ControlloVerifiche filtro)
        {
            return db.GetClassList(filtro).ToList<ControlloVerifiche>();
        }

        public ControlloVerifiche Insert(ControlloVerifiche cls)
        {
            cls = DataIntegrations(cls);

            Validate(cls, AmbitoValidazione.Insert);

            db.Insert(cls);

            cls = (ControlloVerifiche)ChildDataIntegrations(cls);

            ChildInsert(cls);

            return cls;
        }

        public override DataClass ChildDataIntegrations(DataClass cls)
        {
            return cls;
        }

        private ControlloVerifiche ChildInsert(ControlloVerifiche cls)
        {
            return cls;
        }

        private ControlloVerifiche DataIntegrations(ControlloVerifiche cls)
        {
            return cls;
        }


        public ControlloVerifiche Update(ControlloVerifiche cls)
        {
            Validate(cls, AmbitoValidazione.Update);

            db.Update(cls);

            return cls;
        }

        public void Delete(ControlloVerifiche cls)
        {
            VerificaRecordCollegati(cls);

            EffettuaCancellazioneACascata(cls);

            db.Delete(cls);
        }

        private void VerificaRecordCollegati(ControlloVerifiche cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void EffettuaCancellazioneACascata(ControlloVerifiche cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(ControlloVerifiche cls, AmbitoValidazione ambitoValidazione)
        {
            RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


