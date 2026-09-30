using Init.SIGePro.Manager;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;
using VBG.Backend.Protocollo.AppLogic.Legacy.Sigepro.Data;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Sigepro.Manager
{

    ///
    /// File generato automaticamente dalla tabella PROT_AOO per la classe ProtAOO il 19/01/2009 11.25.08
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
    public partial class ProtAOOMgr : BaseManager
    {
        public ProtAOOMgr(DataBase dataBase) : base(dataBase) { }

        public ProtAOO GetById(int ao_id, string idcomune)
        {
            var c = new ProtAOO();


            c.Ao_Id = ao_id;
            c.Idcomune = idcomune;

            return (ProtAOO)this.db.GetClass(c);
        }

        public List<ProtAOO> GetList(ProtAOO filtro)
        {
            return this.db.GetClassList(filtro).ToList<ProtAOO>();
        }

        public ProtAOO Insert(ProtAOO cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }

        private ProtAOO ChildInsert(ProtAOO cls)
        {
            return cls;
        }

        private ProtAOO DataIntegrations(ProtAOO cls)
        {
            return cls;
        }


        public ProtAOO Update(ProtAOO cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(ProtAOO cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void VerificaRecordCollegati(ProtAOO cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void EffettuaCancellazioneACascata(ProtAOO cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(ProtAOO cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


