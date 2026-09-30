using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella ELENCHIPROFESSIONALIBASE per la classe ElenchiProfessionaliBase il 15/09/2010 12.28.49
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
    public partial class ElenchiProfessionaliBaseMgr : BaseManager
    {
        public ElenchiProfessionaliBaseMgr(DataBase dataBase) : base(dataBase) { }

        public ElenchiProfessionaliBase GetById(int? ep_id)
        {
            var c = new ElenchiProfessionaliBase();


            c.EpId = ep_id;

            return (ElenchiProfessionaliBase)this.db.GetClass(c);
        }

        public List<ElenchiProfessionaliBase> GetList(ElenchiProfessionaliBase filtro)
        {
            filtro.OrderBy = "ep_descrizione asc";

            return this.db.GetClassList(filtro).ToList<ElenchiProfessionaliBase>();
        }

        public ElenchiProfessionaliBase Insert(ElenchiProfessionaliBase cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }



        private ElenchiProfessionaliBase ChildInsert(ElenchiProfessionaliBase cls)
        {
            return cls;
        }

        private ElenchiProfessionaliBase DataIntegrations(ElenchiProfessionaliBase cls)
        {
            return cls;
        }


        public ElenchiProfessionaliBase Update(ElenchiProfessionaliBase cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(ElenchiProfessionaliBase cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void VerificaRecordCollegati(ElenchiProfessionaliBase cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void EffettuaCancellazioneACascata(ElenchiProfessionaliBase cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(ElenchiProfessionaliBase cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


