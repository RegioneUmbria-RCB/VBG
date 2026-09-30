using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella STILIFRONTOFFICE per la classe StiliFrontOffice il 24/03/2010 10.11.26
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
    public partial class StiliFrontOfficeMgr : BaseManager
    {
        public StiliFrontOfficeMgr(DataBase dataBase) : base(dataBase) { }

        public StiliFrontOffice GetById(string idcomune)
        {
            var c = new StiliFrontOffice();


            c.Idcomune = idcomune;

            return (StiliFrontOffice)this.db.GetClass(c);
        }

        public List<StiliFrontOffice> GetList(StiliFrontOffice filtro)
        {
            return this.db.GetClassList(filtro).ToList<StiliFrontOffice>();
        }

        public StiliFrontOffice Insert(StiliFrontOffice cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }



        private StiliFrontOffice ChildInsert(StiliFrontOffice cls)
        {
            return cls;
        }

        private StiliFrontOffice DataIntegrations(StiliFrontOffice cls)
        {
            return cls;
        }


        public StiliFrontOffice Update(StiliFrontOffice cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(StiliFrontOffice cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void VerificaRecordCollegati(StiliFrontOffice cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void EffettuaCancellazioneACascata(StiliFrontOffice cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(StiliFrontOffice cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


