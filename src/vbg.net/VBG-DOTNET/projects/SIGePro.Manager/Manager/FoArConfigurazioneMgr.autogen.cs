using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella FO_ARCONFIGURAZIONE per la classe FoArConfigurazione il 13/11/2009 15.50.56
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
    public partial class FoArConfigurazioneMgr : BaseManager
    {
        public FoArConfigurazioneMgr(DataBase dataBase) : base(dataBase) { }

        public FoArConfigurazione GetById(string idcomune, string software)
        {
            var c = new FoArConfigurazione();


            c.Idcomune = idcomune;
            c.Software = software;

            return (FoArConfigurazione)this.db.GetClass(c);
        }

        public List<FoArConfigurazione> GetList(FoArConfigurazione filtro)
        {
            return this.db.GetClassList(filtro).ToList<FoArConfigurazione>();
        }

        public FoArConfigurazione Insert(FoArConfigurazione cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }



        private FoArConfigurazione ChildInsert(FoArConfigurazione cls)
        {
            return cls;
        }

        private FoArConfigurazione DataIntegrations(FoArConfigurazione cls)
        {
            return cls;
        }


        public FoArConfigurazione Update(FoArConfigurazione cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(FoArConfigurazione cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void VerificaRecordCollegati(FoArConfigurazione cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void EffettuaCancellazioneACascata(FoArConfigurazione cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(FoArConfigurazione cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


