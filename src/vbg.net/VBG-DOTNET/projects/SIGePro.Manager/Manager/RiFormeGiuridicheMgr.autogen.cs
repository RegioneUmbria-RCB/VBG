using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella RI_FORMEGIURIDICHE per la classe RiFormeGiuridiche il 01/09/2014 11.48.22
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
    public partial class RiFormeGiuridicheMgr : BaseManager
    {
        public RiFormeGiuridicheMgr(DataBase dataBase) : base(dataBase) { }

        public RiFormeGiuridiche GetById(string codice)
        {
            var c = new RiFormeGiuridiche();


            c.Codice = codice;

            return (RiFormeGiuridiche)this.db.GetClass(c);
        }

        public List<RiFormeGiuridiche> GetList(RiFormeGiuridiche filtro)
        {
            return this.db.GetClassList(filtro).ToList<RiFormeGiuridiche>();
        }

        public RiFormeGiuridiche Insert(RiFormeGiuridiche cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }



        private RiFormeGiuridiche ChildInsert(RiFormeGiuridiche cls)
        {
            return cls;
        }

        private RiFormeGiuridiche DataIntegrations(RiFormeGiuridiche cls)
        {
            return cls;
        }


        public RiFormeGiuridiche Update(RiFormeGiuridiche cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(RiFormeGiuridiche cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void VerificaRecordCollegati(RiFormeGiuridiche cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void EffettuaCancellazioneACascata(RiFormeGiuridiche cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(RiFormeGiuridiche cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


