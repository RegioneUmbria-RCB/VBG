using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella INVENTARIOPROCDYN2MODELLIT per la classe InventarioProcDyn2ModelliT il 05/08/2008 16.49.58
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
    public partial class InventarioProcDyn2ModelliTMgr : BaseManager
    {
        public InventarioProcDyn2ModelliTMgr(DataBase dataBase) : base(dataBase) { }

        public InventarioProcDyn2ModelliT GetById(string idcomune, int codiceinventario, int fk_d2mt_id)
        {
            var c = new InventarioProcDyn2ModelliT();


            c.Idcomune = idcomune;
            c.Codiceinventario = codiceinventario;
            c.FkD2mtId = fk_d2mt_id;

            return (InventarioProcDyn2ModelliT)this.db.GetClass(c);
        }

        public List<InventarioProcDyn2ModelliT> GetList(InventarioProcDyn2ModelliT filtro)
        {
            return this.db.GetClassList(filtro).ToList<InventarioProcDyn2ModelliT>();
        }

        public InventarioProcDyn2ModelliT Insert(InventarioProcDyn2ModelliT cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }



        private InventarioProcDyn2ModelliT ChildInsert(InventarioProcDyn2ModelliT cls)
        {
            return cls;
        }

        private InventarioProcDyn2ModelliT DataIntegrations(InventarioProcDyn2ModelliT cls)
        {
            return cls;
        }


        public InventarioProcDyn2ModelliT Update(InventarioProcDyn2ModelliT cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(InventarioProcDyn2ModelliT cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void VerificaRecordCollegati(InventarioProcDyn2ModelliT cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void EffettuaCancellazioneACascata(InventarioProcDyn2ModelliT cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(InventarioProcDyn2ModelliT cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


