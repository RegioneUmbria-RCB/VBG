

using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella CDSATTI per la classe CdsAtti il 30/07/2008 16.20.43
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
    public partial class CdsAttiMgr : BaseManager
    {
        public CdsAttiMgr(DataBase dataBase) : base(dataBase) { }

        public CdsAtti GetById(int codiceatto, int codiceistanza, string idcomune, int fkidtestata)
        {
            var c = new CdsAtti();


            c.Codiceatto = codiceatto;
            c.Codiceistanza = codiceistanza;
            c.Idcomune = idcomune;
            c.Fkidtestata = fkidtestata;

            return (CdsAtti)this.db.GetClass(c);
        }

        public List<CdsAtti> GetList(int codiceatto, int codiceistanza, DateTime data, string ora, string verbale, string note, DateTime dataconvocazione, string oraconvocazione, DateTime dataconvocazione2, string oraconvocazione2, string chiusa, string fileverbale, int positivia, int codiceoggetto, string idcomune, int fkidtestata)
        {
            var c = new CdsAtti();
            c.Codiceatto = codiceatto;
            c.Codiceistanza = codiceistanza;
            c.Data = data;
            if (!String.IsNullOrEmpty(ora)) c.Ora = ora;
            if (!String.IsNullOrEmpty(verbale)) c.Verbale = verbale;
            if (!String.IsNullOrEmpty(note)) c.Note = note;
            c.Dataconvocazione = dataconvocazione;
            if (!String.IsNullOrEmpty(oraconvocazione)) c.Oraconvocazione = oraconvocazione;
            c.Dataconvocazione2 = dataconvocazione2;
            if (!String.IsNullOrEmpty(oraconvocazione2)) c.Oraconvocazione2 = oraconvocazione2;
            if (!String.IsNullOrEmpty(chiusa)) c.Chiusa = chiusa;
            if (!String.IsNullOrEmpty(fileverbale)) c.Fileverbale = fileverbale;
            c.Positivia = positivia;
            c.Codiceoggetto = codiceoggetto;
            if (!String.IsNullOrEmpty(idcomune)) c.Idcomune = idcomune;
            c.Fkidtestata = fkidtestata;


            return this.db.GetClassList(c).ToList<CdsAtti>();
        }

        public List<CdsAtti> GetList(CdsAtti filtro)
        {
            return this.db.GetClassList(filtro).ToList<CdsAtti>();
        }

        public CdsAtti Insert(CdsAtti cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }



        private CdsAtti ChildInsert(CdsAtti cls)
        {
            return cls;
        }

        private CdsAtti DataIntegrations(CdsAtti cls)
        {
            return cls;
        }


        public CdsAtti Update(CdsAtti cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(CdsAtti cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);

            this.EliminaOggetto(cls);
        }

        private void EliminaOggetto(CdsAtti cls)
        {
            new OggettiMgr(this.db).EliminaOggetto(cls.Idcomune, cls.Codiceoggetto.GetValueOrDefault(-1));
        }


        private void EffettuaCancellazioneACascata(CdsAtti cls)
        {
        }


        private void VerificaRecordCollegati(CdsAtti cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void Validate(CdsAtti cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


