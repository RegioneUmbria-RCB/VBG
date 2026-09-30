using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella INTERVENTIDYN2MODELLIT per la classe InterventiDyn2ModelliT il 05/08/2008 16.49.58
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
    public partial class InterventiDyn2ModelliTMgr : BaseManager
    {
        public InterventiDyn2ModelliTMgr(DataBase dataBase) : base(dataBase) { }

        public InterventiDyn2ModelliT GetById(string idcomune, int codiceintervento, int fk_d2mt_id)
        {
            var c = new InterventiDyn2ModelliT();

            c.Idcomune = idcomune;
            c.CodiceIntervento = codiceintervento;
            c.FkD2mtId = fk_d2mt_id;

            return (InterventiDyn2ModelliT)this.db.GetClass(c);
        }

        public List<InterventiDyn2ModelliT> GetList(InterventiDyn2ModelliT filtro)
        {
            return this.db.GetClassList(filtro).ToList<InterventiDyn2ModelliT>();
        }

        public InterventiDyn2ModelliT Insert(InterventiDyn2ModelliT cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }



        private InterventiDyn2ModelliT ChildInsert(InterventiDyn2ModelliT cls)
        {
            return cls;
        }

        private InterventiDyn2ModelliT DataIntegrations(InterventiDyn2ModelliT cls)
        {
            return cls;
        }


        public InterventiDyn2ModelliT Update(InterventiDyn2ModelliT cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(InterventiDyn2ModelliT cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void VerificaRecordCollegati(InterventiDyn2ModelliT cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void EffettuaCancellazioneACascata(InterventiDyn2ModelliT cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(InterventiDyn2ModelliT cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


