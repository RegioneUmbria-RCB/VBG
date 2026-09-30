using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella TIPI_SCADENZA per la classe TIPI_SCADENZA il 03/06/2009 11.07.33
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
    public partial class TIPI_SCADENZAMgr : BaseManager
    {
        public TIPI_SCADENZAMgr(DataBase dataBase) : base(dataBase) { }

        public TIPI_SCADENZA GetById(int id)
        {
            var c = new TIPI_SCADENZA();


            c.Id = id;

            return (TIPI_SCADENZA)this.db.GetClass(c);
        }

        public List<TIPI_SCADENZA> GetList(TIPI_SCADENZA filtro)
        {
            return this.db.GetClassList(filtro).ToList<TIPI_SCADENZA>();
        }

        public TIPI_SCADENZA Insert(TIPI_SCADENZA cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }



        private TIPI_SCADENZA ChildInsert(TIPI_SCADENZA cls)
        {
            return cls;
        }

        private TIPI_SCADENZA DataIntegrations(TIPI_SCADENZA cls)
        {
            return cls;
        }


        public TIPI_SCADENZA Update(TIPI_SCADENZA cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(TIPI_SCADENZA cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void VerificaRecordCollegati(TIPI_SCADENZA cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void EffettuaCancellazioneACascata(TIPI_SCADENZA cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(TIPI_SCADENZA cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


