using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella INVENTARIOPROC_TIPITITOLO per la classe InventarioProcTipiTitolo il 20/05/2011 10.54.43
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
    public partial class InventarioProcTipiTitoloMgr : BaseManager
    {
        public InventarioProcTipiTitoloMgr(DataBase dataBase) : base(dataBase) { }

        public InventarioProcTipiTitolo GetById(string idcomune, int? id)
        {
            var c = new InventarioProcTipiTitolo();


            c.Idcomune = idcomune;
            c.Id = id;

            return (InventarioProcTipiTitolo)this.db.GetClass(c);
        }

        public List<InventarioProcTipiTitolo> GetList(InventarioProcTipiTitolo filtro)
        {
            return this.db.GetClassList(filtro).ToList<InventarioProcTipiTitolo>();
        }

        public InventarioProcTipiTitolo Insert(InventarioProcTipiTitolo cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }



        private InventarioProcTipiTitolo ChildInsert(InventarioProcTipiTitolo cls)
        {
            return cls;
        }

        private InventarioProcTipiTitolo DataIntegrations(InventarioProcTipiTitolo cls)
        {
            return cls;
        }


        public InventarioProcTipiTitolo Update(InventarioProcTipiTitolo cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(InventarioProcTipiTitolo cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void VerificaRecordCollegati(InventarioProcTipiTitolo cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void EffettuaCancellazioneACascata(InventarioProcTipiTitolo cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(InventarioProcTipiTitolo cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


