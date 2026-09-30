using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella INVENTARIOPROC_LEGGI per la classe InventarioprocLeggi il 10/01/2011 12.18.09
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
    public partial class InventarioprocLeggiMgr : BaseManager
    {
        public InventarioprocLeggiMgr(DataBase dataBase) : base(dataBase) { }

        public InventarioprocLeggi GetById(int? id, string idcomune)
        {
            var c = new InventarioprocLeggi();


            c.Id = id;
            c.Idcomune = idcomune;

            return (InventarioprocLeggi)this.db.GetClass(c);
        }

        public List<InventarioprocLeggi> GetList(InventarioprocLeggi filtro)
        {
            return this.db.GetClassList(filtro).ToList<InventarioprocLeggi>();
        }

        public InventarioprocLeggi Insert(InventarioprocLeggi cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }



        private InventarioprocLeggi ChildInsert(InventarioprocLeggi cls)
        {
            return cls;
        }

        private InventarioprocLeggi DataIntegrations(InventarioprocLeggi cls)
        {
            return cls;
        }


        public InventarioprocLeggi Update(InventarioprocLeggi cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(InventarioprocLeggi cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void VerificaRecordCollegati(InventarioprocLeggi cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void EffettuaCancellazioneACascata(InventarioprocLeggi cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(InventarioprocLeggi cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


