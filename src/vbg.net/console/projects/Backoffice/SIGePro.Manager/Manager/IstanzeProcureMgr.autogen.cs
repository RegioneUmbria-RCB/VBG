

using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Manager;
using PersonalLib2.Data;
using Init.SIGePro.Data;
using Init.SIGePro.Validator;
using PersonalLib2.Sql;

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
            IstanzeProcure c = new IstanzeProcure();


            c.IdComune = idcomune;
            c.Id = id;

            return (IstanzeProcure)db.GetClass(c);
        }

        public List<IstanzeProcure> GetList(IstanzeProcure filtro)
        {
            return db.GetClassList(filtro).ToList<IstanzeProcure>();
        }

        public IstanzeProcure Insert(IstanzeProcure cls)
        {
            cls = DataIntegrations(cls);

            Validate(cls, AmbitoValidazione.Insert);

            db.Insert(cls);

            cls = (IstanzeProcure)ChildDataIntegrations(cls);

            ChildInsert(cls);

            return cls;
        }

        public override DataClass ChildDataIntegrations(DataClass cls)
        {
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
            Validate(cls, AmbitoValidazione.Update);

            db.Update(cls);

            return cls;
        }

        public void Delete(IstanzeProcure cls)
        {
            VerificaRecordCollegati(cls);

            EffettuaCancellazioneACascata(cls);

            db.Delete(cls);
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
            RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


