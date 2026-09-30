using Init.SIGePro.Manager;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;
using VBG.Backend.Protocollo.AppLogic.Legacy.Sigepro.Data;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Sigepro.Manager
{

    ///
    /// File generato automaticamente dalla tabella PROT_TIPOLOGIAPROTOCOLLO per la classe ProtTipologiaProtocollo il 19/01/2009 10.56.08
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
    public partial class ProtTipologiaProtocolloMgr : BaseManager
    {
        public ProtTipologiaProtocolloMgr(DataBase dataBase) : base(dataBase) { }

        public ProtTipologiaProtocollo GetById(int tp_id, string idcomune)
        {
            var c = new ProtTipologiaProtocollo();


            c.Tp_Id = tp_id;
            c.Idcomune = idcomune;

            return (ProtTipologiaProtocollo)this.db.GetClass(c);
        }

        public List<ProtTipologiaProtocollo> GetList(ProtTipologiaProtocollo filtro)
        {
            return this.db.GetClassList(filtro).ToList<ProtTipologiaProtocollo>();
        }

        public ProtTipologiaProtocollo Insert(ProtTipologiaProtocollo cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }

        private ProtTipologiaProtocollo ChildInsert(ProtTipologiaProtocollo cls)
        {
            return cls;
        }

        private ProtTipologiaProtocollo DataIntegrations(ProtTipologiaProtocollo cls)
        {
            return cls;
        }


        public ProtTipologiaProtocollo Update(ProtTipologiaProtocollo cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(ProtTipologiaProtocollo cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void VerificaRecordCollegati(ProtTipologiaProtocollo cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void EffettuaCancellazioneACascata(ProtTipologiaProtocollo cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(ProtTipologiaProtocollo cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


