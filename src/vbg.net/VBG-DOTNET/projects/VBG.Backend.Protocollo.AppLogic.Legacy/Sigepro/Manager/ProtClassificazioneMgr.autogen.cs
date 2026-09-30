using Init.SIGePro.Manager;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;
using VBG.Backend.Protocollo.AppLogic.Legacy.Sigepro.Data;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Sigepro.Manager
{

    ///
    /// File generato automaticamente dalla tabella PROT_CLASSIFICAZIONE per la classe ProtClassificazione il 19/01/2009 10.46.00
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
    public partial class ProtClassificazioneMgr : BaseManager
    {
        public ProtClassificazioneMgr(DataBase dataBase) : base(dataBase) { }

        public ProtClassificazione GetById(int cl_id, string idcomune)
        {
            var c = new ProtClassificazione();


            c.Cl_Id = cl_id;
            c.Idcomune = idcomune;

            return (ProtClassificazione)this.db.GetClass(c);
        }

        public List<ProtClassificazione> GetList(ProtClassificazione filtro)
        {
            return this.db.GetClassList(filtro).ToList<ProtClassificazione>();
        }

        public ProtClassificazione Insert(ProtClassificazione cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }

        private ProtClassificazione ChildInsert(ProtClassificazione cls)
        {
            return cls;
        }

        private ProtClassificazione DataIntegrations(ProtClassificazione cls)
        {
            return cls;
        }


        public ProtClassificazione Update(ProtClassificazione cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(ProtClassificazione cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void VerificaRecordCollegati(ProtClassificazione cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void EffettuaCancellazioneACascata(ProtClassificazione cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(ProtClassificazione cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


