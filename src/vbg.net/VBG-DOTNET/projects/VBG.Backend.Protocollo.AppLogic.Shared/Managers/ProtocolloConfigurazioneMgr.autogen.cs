using Init.SIGePro.Manager;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;

namespace VBG.Backend.Protocollo.AppLogic.Shared.Managers
{
    ///
	/// File generato automaticamente dalla tabella PROTOCOLLO_CONFIGURAZIONE per la classe ProtocolloConfigurazione il 11/12/2008 11.38.33
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
	public partial class ProtocolloConfigurazioneMgr : BaseManager
    {
        public ProtocolloConfigurazioneMgr(DataBase dataBase) : base(dataBase) { }

        public ProtocolloConfigurazione GetById(string idcomune, string software)
        {
            var c = new ProtocolloConfigurazione();


            c.Idcomune = idcomune;
            c.Software = software;

            return (ProtocolloConfigurazione)this.db.GetClass(c);
        }

        public List<ProtocolloConfigurazione> GetList(ProtocolloConfigurazione filtro)
        {
            return this.db.GetClassList(filtro).ToList<ProtocolloConfigurazione>();
        }

        public ProtocolloConfigurazione Insert(ProtocolloConfigurazione cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }


        private ProtocolloConfigurazione ChildInsert(ProtocolloConfigurazione cls)
        {
            return cls;
        }

        private ProtocolloConfigurazione DataIntegrations(ProtocolloConfigurazione cls)
        {
            return cls;
        }


        public ProtocolloConfigurazione Update(ProtocolloConfigurazione cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(ProtocolloConfigurazione cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void VerificaRecordCollegati(ProtocolloConfigurazione cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void EffettuaCancellazioneACascata(ProtocolloConfigurazione cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(ProtocolloConfigurazione cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}
