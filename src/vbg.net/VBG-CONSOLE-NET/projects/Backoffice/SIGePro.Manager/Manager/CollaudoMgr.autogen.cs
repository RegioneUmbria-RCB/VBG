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
    /// File generato automaticamente dalla tabella COLLAUDO per la classe Collaudo il 30/07/2008 16.36.27
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
    public partial class CollaudoMgr : BaseManager
    {
        public CollaudoMgr(DataBase dataBase) : base(dataBase) { }

        public Collaudo GetById()
        {
            Collaudo c = new Collaudo();



            return (Collaudo)db.GetClass(c);
        }

        public List<Collaudo> GetList(int codiceistanza, DateTime dataconvocazione, string odg, string note, int proprio, DateTime dataverbale, string parere, int esito, string fileverbale, int codiceoggetto, string idcomune)
        {
            Collaudo c = new Collaudo();
            c.Codiceistanza = codiceistanza;
            c.Dataconvocazione = dataconvocazione;
            if (!String.IsNullOrEmpty(odg)) c.Odg = odg;
            if (!String.IsNullOrEmpty(note)) c.Note = note;
            c.Proprio = proprio;
            c.Dataverbale = dataverbale;
            if (!String.IsNullOrEmpty(parere)) c.Parere = parere;
            c.Esito = esito;
            if (!String.IsNullOrEmpty(fileverbale)) c.Fileverbale = fileverbale;
            c.Codiceoggetto = codiceoggetto;
            if (!String.IsNullOrEmpty(idcomune)) c.Idcomune = idcomune;


            return db.GetClassList(c).ToList<Collaudo>();
        }

        public List<Collaudo> GetList(Collaudo filtro)
        {
            return db.GetClassList(filtro).ToList<Collaudo>();
        }

        public Collaudo Insert(Collaudo cls)
        {
            cls = DataIntegrations(cls);

            Validate(cls, AmbitoValidazione.Insert);

            db.Insert(cls);

            cls = (Collaudo)ChildDataIntegrations(cls);

            ChildInsert(cls);

            return cls;
        }

        public override DataClass ChildDataIntegrations(DataClass cls)
        {
            return cls;
        }

        private Collaudo ChildInsert(Collaudo cls)
        {
            return cls;
        }

        private Collaudo DataIntegrations(Collaudo cls)
        {
            return cls;
        }


        public Collaudo Update(Collaudo cls)
        {
            Validate(cls, AmbitoValidazione.Update);

            db.Update(cls);

            return cls;
        }

        public void Delete(Collaudo cls)
        {
            VerificaRecordCollegati(cls);

            EffettuaCancellazioneACascata(cls);

            db.Delete(cls);
        }

        private void VerificaRecordCollegati(Collaudo cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void EffettuaCancellazioneACascata(Collaudo cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(Collaudo cls, AmbitoValidazione ambitoValidazione)
        {
            RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


