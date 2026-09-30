using Init.SIGePro.Exceptions;
using Init.SIGePro.Manager;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;
using VBG.Backend.Protocollo.AppLogic.Legacy.Sigepro.Data;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Sigepro.Manager
{

    ///
    /// File generato automaticamente dalla tabella PROT_ALLEGATIPROTOCOLLO per la classe ProtAllegatiProtocollo il 09/01/2009 12.28.26
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
    public partial class ProtAllegatiProtocolloMgr : BaseManager
    {
        public ProtAllegatiProtocolloMgr(DataBase dataBase) : base(dataBase) { }

        public ProtAllegatiProtocollo GetById(int ad_id, string idcomune)
        {
            var c = new ProtAllegatiProtocollo();


            c.Ad_Id = ad_id;
            c.Idcomune = idcomune;

            return (ProtAllegatiProtocollo)this.db.GetClass(c);
        }

        public List<ProtAllegatiProtocollo> GetList(ProtAllegatiProtocollo filtro)
        {
            return this.db.GetClassList(filtro).ToList<ProtAllegatiProtocollo>();
        }

        public ProtAllegatiProtocollo Insert(ProtAllegatiProtocollo cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }


        private ProtAllegatiProtocollo ChildInsert(ProtAllegatiProtocollo cls)
        {
            return cls;
        }

        private ProtAllegatiProtocollo DataIntegrations(ProtAllegatiProtocollo cls)
        {
            return cls;
        }


        public ProtAllegatiProtocollo Update(ProtAllegatiProtocollo cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(ProtAllegatiProtocollo cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void VerificaRecordCollegati(ProtAllegatiProtocollo cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void EffettuaCancellazioneACascata(ProtAllegatiProtocollo cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(ProtAllegatiProtocollo cls, AmbitoValidazione ambitoValidazione)
        {
            if (string.IsNullOrEmpty(cls.Ad_Descrizione))
                throw new RequiredFieldException("PROT_ALLEGATIPROTOCOLLO.AD_DESCRIZIONE obbligatorio");

            this.RequiredFieldValidate(cls, ambitoValidazione);

            this.ForeignValidate(cls);
        }
    }
}


