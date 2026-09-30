using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella ISTANZEONERI_CANONI per la classe IstanzeOneri_Canoni il 16/09/2008 18.51.41
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
    public partial class IstanzeProcureMgr : BaseManager
    {
        public IstanzeProcureMgr(DataBase dataBase) : base(dataBase) { }

        public IstanzeProcure GetById(string idcomune, int id)
        {
            var c = new IstanzeProcure();


            c.IdComune = idcomune;
            c.Id = id;

            return (IstanzeProcure)this.db.GetClass(c);
        }

        public List<IstanzeProcure> GetList(IstanzeProcure filtro)
        {
            return this.db.GetClassList(filtro).ToList<IstanzeProcure>();
        }

        public IstanzeProcure Insert(IstanzeProcure cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }



        private IstanzeProcure ChildInsert(IstanzeProcure cls)
        {
            return cls;
        }

        private IstanzeProcure DataIntegrations(IstanzeProcure cls)
        {
            return cls;
        }


        public IstanzeProcure Update(IstanzeProcure cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(IstanzeProcure cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void VerificaRecordCollegati(IstanzeProcure cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void EffettuaCancellazioneACascata(IstanzeProcure cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(IstanzeProcure cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


