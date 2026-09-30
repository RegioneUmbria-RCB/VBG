using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella MOVIMENTIDYN2MODELLIT per la classe MovimentiDyn2ModelliT il 08/09/2008 10.32.28
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
    public partial class MovimentiDyn2ModelliTMgr : BaseManager
    {
        public MovimentiDyn2ModelliTMgr(DataBase dataBase) : base(dataBase) { }

        public MovimentiDyn2ModelliT GetById(string idcomune, int fk_d2mt_id, int codicemovimento)
        {
            var c = new MovimentiDyn2ModelliT();


            c.Idcomune = idcomune;
            c.FkD2mtId = fk_d2mt_id;
            c.Codicemovimento = codicemovimento;

            return (MovimentiDyn2ModelliT)this.db.GetClass(c);
        }

        public List<MovimentiDyn2ModelliT> GetList(MovimentiDyn2ModelliT filtro)
        {
            return this.db.GetClassList(filtro).ToList<MovimentiDyn2ModelliT>();
        }

        public MovimentiDyn2ModelliT Insert(MovimentiDyn2ModelliT cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }



        private MovimentiDyn2ModelliT ChildInsert(MovimentiDyn2ModelliT cls)
        {
            return cls;
        }

        private MovimentiDyn2ModelliT DataIntegrations(MovimentiDyn2ModelliT cls)
        {
            return cls;
        }


        public MovimentiDyn2ModelliT Update(MovimentiDyn2ModelliT cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(MovimentiDyn2ModelliT cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void VerificaRecordCollegati(MovimentiDyn2ModelliT cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void EffettuaCancellazioneACascata(MovimentiDyn2ModelliT cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(MovimentiDyn2ModelliT cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


