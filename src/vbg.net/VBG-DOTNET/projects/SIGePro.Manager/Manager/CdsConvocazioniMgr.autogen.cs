

using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella CDSCONVOCAZIONI per la classe CdsConvocazioni il 30/07/2008 16.24.11
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
    public partial class CdsConvocazioniMgr : BaseManager
    {
        public CdsConvocazioniMgr(DataBase dataBase) : base(dataBase) { }

        public CdsConvocazioni GetById(int id, int codiceistanza, int idtestata, string idcomune)
        {
            var c = new CdsConvocazioni();


            c.Id = id;
            c.Codiceistanza = codiceistanza;
            c.Idtestata = idtestata;
            c.Idcomune = idcomune;

            return (CdsConvocazioni)this.db.GetClass(c);
        }

        public List<CdsConvocazioni> GetList(DateTime dataconvocazione, string oraconvocazione, int id, int codiceistanza, int idtestata, string idcomune)
        {
            var c = new CdsConvocazioni();
            c.Dataconvocazione = dataconvocazione;
            if (!String.IsNullOrEmpty(oraconvocazione)) c.Oraconvocazione = oraconvocazione;
            c.Id = id;
            c.Codiceistanza = codiceistanza;
            c.Idtestata = idtestata;
            if (!String.IsNullOrEmpty(idcomune)) c.Idcomune = idcomune;


            return this.db.GetClassList(c).ToList<CdsConvocazioni>();
        }

        public List<CdsConvocazioni> GetList(CdsConvocazioni filtro)
        {
            return this.db.GetClassList(filtro).ToList<CdsConvocazioni>();
        }

        public CdsConvocazioni Insert(CdsConvocazioni cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }



        private CdsConvocazioni ChildInsert(CdsConvocazioni cls)
        {
            return cls;
        }

        private CdsConvocazioni DataIntegrations(CdsConvocazioni cls)
        {
            return cls;
        }


        public CdsConvocazioni Update(CdsConvocazioni cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(CdsConvocazioni cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void VerificaRecordCollegati(CdsConvocazioni cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void EffettuaCancellazioneACascata(CdsConvocazioni cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(CdsConvocazioni cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


