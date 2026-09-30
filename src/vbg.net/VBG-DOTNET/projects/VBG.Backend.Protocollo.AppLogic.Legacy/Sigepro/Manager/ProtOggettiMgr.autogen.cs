using Init.SIGePro.Manager;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;
using VBG.Backend.Protocollo.AppLogic.Legacy.Sigepro.Data;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Sigepro.Manager
{

    ///
    /// File generato automaticamente dalla tabella PROT_OGGETTI per la classe ProtOggetti il 23/08/2011 11.15.49
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
    public partial class ProtOggettiMgr : BaseManager
    {
        public ProtOggettiMgr(DataBase dataBase) : base(dataBase) { }

        public ProtOggetti GetById(int? codiceoggetto, string idcomune)
        {
            var c = new ProtOggetti();


            c.Codiceoggetto = codiceoggetto;
            c.Idcomune = idcomune;

            return (ProtOggetti)this.db.GetClass(c);
        }

        public List<ProtOggetti> GetList(ProtOggetti filtro)
        {
            return this.db.GetClassList(filtro).ToList<ProtOggetti>();
        }

        public ProtOggetti Insert(ProtOggetti cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }

        private ProtOggetti ChildInsert(ProtOggetti cls)
        {
            return cls;
        }

        private ProtOggetti DataIntegrations(ProtOggetti cls)
        {
            return cls;
        }


        public ProtOggetti Update(ProtOggetti cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(ProtOggetti cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void VerificaRecordCollegati(ProtOggetti cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void EffettuaCancellazioneACascata(ProtOggetti cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(ProtOggetti cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


