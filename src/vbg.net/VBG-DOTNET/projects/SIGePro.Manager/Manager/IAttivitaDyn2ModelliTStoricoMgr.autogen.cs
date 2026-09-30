using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella I_ATTIVITADYN2MODELLIT_STORICO per la classe IAttivitaDyn2ModelliTStorico il 26/10/2010 15.11.30
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
    public partial class IAttivitaDyn2ModelliTStoricoMgr : BaseManager
    {
        public IAttivitaDyn2ModelliTStoricoMgr(DataBase dataBase) : base(dataBase) { }

        public IAttivitaDyn2ModelliTStorico GetById(string idcomune, int? idversione, int? fk_ia_id, int? fk_d2mt_id)
        {
            var c = new IAttivitaDyn2ModelliTStorico();


            c.Idcomune = idcomune;
            c.Idversione = idversione;
            c.FkIaId = fk_ia_id;
            c.FkD2mtId = fk_d2mt_id;

            return (IAttivitaDyn2ModelliTStorico)this.db.GetClass(c);
        }

        public List<IAttivitaDyn2ModelliTStorico> GetList(IAttivitaDyn2ModelliTStorico filtro)
        {
            return this.db.GetClassList(filtro).ToList<IAttivitaDyn2ModelliTStorico>();
        }

        public IAttivitaDyn2ModelliTStorico Insert(IAttivitaDyn2ModelliTStorico cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }



        private IAttivitaDyn2ModelliTStorico ChildInsert(IAttivitaDyn2ModelliTStorico cls)
        {
            return cls;
        }

        private IAttivitaDyn2ModelliTStorico DataIntegrations(IAttivitaDyn2ModelliTStorico cls)
        {
            return cls;
        }


        public IAttivitaDyn2ModelliTStorico Update(IAttivitaDyn2ModelliTStorico cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(IAttivitaDyn2ModelliTStorico cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void VerificaRecordCollegati(IAttivitaDyn2ModelliTStorico cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void EffettuaCancellazioneACascata(IAttivitaDyn2ModelliTStorico cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(IAttivitaDyn2ModelliTStorico cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


