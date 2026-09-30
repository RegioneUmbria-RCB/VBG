using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using PersonalLib2.Sql;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella INVENTARIOPROCEDIMENTI per la classe InventarioProcedimenti il 05/11/2008 11.16.35
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
    public partial class InventarioProcedimentiMgr : BaseManager
    {
        public InventarioProcedimentiMgr(DataBase dataBase) : base(dataBase) { }

        public InventarioProcedimenti GetById(string idcomune, int codiceinventario, useForeignEnum useForeign)
        {
            var c = new InventarioProcedimenti();


            c.Codiceinventario = codiceinventario;
            c.Idcomune = idcomune;
            c.UseForeign = useForeign;

            return (InventarioProcedimenti)this.db.GetClass(c);
        }

        public InventarioProcedimenti GetById(string idcomune, int codiceinventario)
        {
            return this.GetById(idcomune, codiceinventario, useForeignEnum.No);
        }

        public List<InventarioProcedimenti> GetList(InventarioProcedimenti filtro)
        {
            return this.db.GetClassList(filtro).ToList<InventarioProcedimenti>();
        }

        public InventarioProcedimenti Insert(InventarioProcedimenti cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }



        private InventarioProcedimenti ChildInsert(InventarioProcedimenti cls)
        {
            return cls;
        }

        private InventarioProcedimenti DataIntegrations(InventarioProcedimenti cls)
        {
            return cls;
        }


        public InventarioProcedimenti Update(InventarioProcedimenti cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(InventarioProcedimenti cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void VerificaRecordCollegati(InventarioProcedimenti cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void EffettuaCancellazioneACascata(InventarioProcedimenti cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(InventarioProcedimenti cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }


    }
}


