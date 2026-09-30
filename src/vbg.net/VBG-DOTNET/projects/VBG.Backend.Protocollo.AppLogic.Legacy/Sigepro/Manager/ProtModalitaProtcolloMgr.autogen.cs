using Init.SIGePro.Manager;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;
using VBG.Backend.Protocollo.AppLogic.Legacy.Sigepro.Data;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Sigepro.Manager
{

    ///
    /// File generato automaticamente dalla tabella PROT_MODALITAPROTOCOLLO per la classe ProtModalitaProtcollo il 19/01/2009 10.47.52
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
    public partial class ProtModalitaProtcolloMgr : BaseManager
    {
        public ProtModalitaProtcolloMgr(DataBase dataBase) : base(dataBase) { }

        public ProtModalitaProtcollo GetById(int mp_id)
        {
            var c = new ProtModalitaProtcollo();


            c.Mp_Id = mp_id;

            return (ProtModalitaProtcollo)this.db.GetClass(c);
        }

        public List<ProtModalitaProtcollo> GetList(ProtModalitaProtcollo filtro)
        {
            return this.db.GetClassList(filtro).ToList<ProtModalitaProtcollo>();
        }

        public ProtModalitaProtcollo Insert(ProtModalitaProtcollo cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }

        private ProtModalitaProtcollo ChildInsert(ProtModalitaProtcollo cls)
        {
            return cls;
        }

        private ProtModalitaProtcollo DataIntegrations(ProtModalitaProtcollo cls)
        {
            return cls;
        }


        public ProtModalitaProtcollo Update(ProtModalitaProtcollo cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(ProtModalitaProtcollo cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void VerificaRecordCollegati(ProtModalitaProtcollo cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void EffettuaCancellazioneACascata(ProtModalitaProtcollo cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(ProtModalitaProtcollo cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


