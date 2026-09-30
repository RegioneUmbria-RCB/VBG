using Init.SIGePro.Manager;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;
using VBG.Backend.Protocollo.AppLogic.Legacy.Sigepro.Data;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Sigepro.Manager
{

    ///
    /// File generato automaticamente dalla tabella PROT_ASSEGNAZIONI per la classe ProtAssegnazioni il 17/03/2009 14.24.21
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
    public partial class ProtAssegnazioniMgr : BaseManager
    {
        public ProtAssegnazioniMgr(DataBase dataBase) : base(dataBase) { }

        public ProtAssegnazioni GetById(int as_id, string idcomune)
        {
            var c = new ProtAssegnazioni();


            c.As_Id = as_id;
            c.Idcomune = idcomune;

            return (ProtAssegnazioni)this.db.GetClass(c);
        }

        public List<ProtAssegnazioni> GetList(ProtAssegnazioni filtro)
        {
            return this.db.GetClassList(filtro).ToList<ProtAssegnazioni>();
        }

        public ProtAssegnazioni Insert(ProtAssegnazioni cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }

        private ProtAssegnazioni ChildInsert(ProtAssegnazioni cls)
        {
            return cls;
        }

        private ProtAssegnazioni DataIntegrations(ProtAssegnazioni cls)
        {
            return cls;
        }


        public ProtAssegnazioni Update(ProtAssegnazioni cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(ProtAssegnazioni cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void VerificaRecordCollegati(ProtAssegnazioni cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void EffettuaCancellazioneACascata(ProtAssegnazioni cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(ProtAssegnazioni cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


