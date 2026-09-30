using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella ISTANZEEVENTI per la classe IstanzeEventi il 06/11/2009 9.50.32
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
    public partial class IstanzeEventiMgr : BaseManager
    {
        public IstanzeEventiMgr(DataBase dataBase) : base(dataBase) { }

        public IstanzeEventi GetById(string idcomune, int? idevento)
        {
            var c = new IstanzeEventi();


            c.Idcomune = idcomune;
            c.Idevento = idevento;

            return (IstanzeEventi)this.db.GetClass(c);
        }

        public List<IstanzeEventi> GetList(IstanzeEventi filtro)
        {
            return this.db.GetClassList(filtro).ToList<IstanzeEventi>();
        }

        public IstanzeEventi Insert(IstanzeEventi cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }



        private IstanzeEventi ChildInsert(IstanzeEventi cls)
        {
            return cls;
        }

        public IstanzeEventi Update(IstanzeEventi cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(IstanzeEventi cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void VerificaRecordCollegati(IstanzeEventi cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void EffettuaCancellazioneACascata(IstanzeEventi cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }

    }
}


