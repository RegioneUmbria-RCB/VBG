using Init.SIGePro.Manager;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;
using VBG.Backend.Protocollo.AppLogic.Legacy.Sigepro.Data;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Sigepro.Manager
{

    ///
    /// File generato automaticamente dalla tabella PROT_MOTIVIANNULLAMENTO per la classe ProtMotiviAnnullamento il 03/03/2009 12.09.15
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
    public partial class ProtMotiviAnnullamentoMgr : BaseManager
    {
        public ProtMotiviAnnullamentoMgr(DataBase dataBase) : base(dataBase) { }

        public ProtMotiviAnnullamento GetById(int ma_id, string idcomune)
        {
            var c = new ProtMotiviAnnullamento();


            c.MaId = ma_id;
            c.Idcomune = idcomune;

            return (ProtMotiviAnnullamento)this.db.GetClass(c);
        }

        public List<ProtMotiviAnnullamento> GetList(ProtMotiviAnnullamento filtro)
        {
            return this.db.GetClassList(filtro).ToList<ProtMotiviAnnullamento>();
        }

        public ProtMotiviAnnullamento Insert(ProtMotiviAnnullamento cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }

        private ProtMotiviAnnullamento ChildInsert(ProtMotiviAnnullamento cls)
        {
            return cls;
        }

        private ProtMotiviAnnullamento DataIntegrations(ProtMotiviAnnullamento cls)
        {
            return cls;
        }


        public ProtMotiviAnnullamento Update(ProtMotiviAnnullamento cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(ProtMotiviAnnullamento cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void VerificaRecordCollegati(ProtMotiviAnnullamento cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void EffettuaCancellazioneACascata(ProtMotiviAnnullamento cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(ProtMotiviAnnullamento cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


