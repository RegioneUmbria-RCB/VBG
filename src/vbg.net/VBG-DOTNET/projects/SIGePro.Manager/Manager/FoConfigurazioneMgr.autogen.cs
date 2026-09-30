using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella FO_CONFIGURAZIONE per la classe FoConfigurazione il 14/09/2010 10.14.53
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
    public partial class FoConfigurazioneMgr : BaseManager
    {
        public FoConfigurazioneMgr(DataBase dataBase) : base(dataBase) { }

        public FoConfigurazione GetById(string idcomune, int? codice)
        {
            var c = new FoConfigurazione();


            c.Idcomune = idcomune;
            c.Codice = codice;

            return (FoConfigurazione)this.db.GetClass(c);
        }

        public List<FoConfigurazione> GetList(FoConfigurazione filtro)
        {
            return this.db.GetClassList(filtro).ToList<FoConfigurazione>();
        }

        public FoConfigurazione Insert(FoConfigurazione cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }



        private FoConfigurazione ChildInsert(FoConfigurazione cls)
        {
            return cls;
        }

        private FoConfigurazione DataIntegrations(FoConfigurazione cls)
        {
            return cls;
        }


        public FoConfigurazione Update(FoConfigurazione cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(FoConfigurazione cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void VerificaRecordCollegati(FoConfigurazione cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void EffettuaCancellazioneACascata(FoConfigurazione cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(FoConfigurazione cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


