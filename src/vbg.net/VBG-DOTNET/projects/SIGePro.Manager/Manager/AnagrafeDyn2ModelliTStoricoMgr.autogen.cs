using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella ANAGRAFEDYN2MODELLIT_STORICO per la classe AnagrafeDyn2ModelliTStorico il 22/02/2010 12.32.50
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
    public partial class AnagrafeDyn2ModelliTStoricoMgr : BaseManager
    {
        public AnagrafeDyn2ModelliTStoricoMgr(DataBase dataBase) : base(dataBase) { }

        public AnagrafeDyn2ModelliTStorico GetById(string idcomune, int? idversione, int? codiceanagrafe, int? fk_d2mt_id)
        {
            var c = new AnagrafeDyn2ModelliTStorico();


            c.Idcomune = idcomune;
            c.Idversione = idversione;
            c.Codiceanagrafe = codiceanagrafe;
            c.FkD2mtId = fk_d2mt_id;

            return (AnagrafeDyn2ModelliTStorico)this.db.GetClass(c);
        }

        public List<AnagrafeDyn2ModelliTStorico> GetList(AnagrafeDyn2ModelliTStorico filtro)
        {
            return this.db.GetClassList(filtro).ToList<AnagrafeDyn2ModelliTStorico>();
        }

        public AnagrafeDyn2ModelliTStorico Insert(AnagrafeDyn2ModelliTStorico cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);


            this.ChildInsert(cls);

            return cls;
        }



        private AnagrafeDyn2ModelliTStorico ChildInsert(AnagrafeDyn2ModelliTStorico cls)
        {
            return cls;
        }

        //private AnagrafeDyn2ModelliTStorico DataIntegrations(AnagrafeDyn2ModelliTStorico cls)
        //{
        //    return cls;
        //}


        public AnagrafeDyn2ModelliTStorico Update(AnagrafeDyn2ModelliTStorico cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(AnagrafeDyn2ModelliTStorico cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void VerificaRecordCollegati(AnagrafeDyn2ModelliTStorico cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        //private void EffettuaCancellazioneACascata(AnagrafeDyn2ModelliTStorico cls )
        //{
        //    // Inserire la logica di cancellazione a cascata di dati collegati
        //}


        private void Validate(AnagrafeDyn2ModelliTStorico cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


