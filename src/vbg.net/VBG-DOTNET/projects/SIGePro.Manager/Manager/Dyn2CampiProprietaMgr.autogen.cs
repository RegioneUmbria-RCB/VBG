using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella DYN2_CAMPIPROPRIETA per la classe Dyn2CampiProprieta il 05/08/2008 16.49.58
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
    public partial class Dyn2CampiProprietaMgr : BaseManager
    {
        public Dyn2CampiProprietaMgr(DataBase dataBase) : base(dataBase) { }

        public Dyn2CampiProprieta GetById(string idcomune, int fk_d2c_id, string proprieta)
        {
            var c = new Dyn2CampiProprieta();


            c.Idcomune = idcomune;
            c.FkD2cId = fk_d2c_id;
            c.Proprieta = proprieta;

            return (Dyn2CampiProprieta)this.db.GetClass(c);
        }

        public List<Dyn2CampiProprieta> GetList(Dyn2CampiProprieta filtro)
        {
            return this.db.GetClassList(filtro).ToList<Dyn2CampiProprieta>();
        }

        public Dyn2CampiProprieta Insert(Dyn2CampiProprieta cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }



        private Dyn2CampiProprieta ChildInsert(Dyn2CampiProprieta cls)
        {
            return cls;
        }

        private Dyn2CampiProprieta DataIntegrations(Dyn2CampiProprieta cls)
        {
            return cls;
        }


        public Dyn2CampiProprieta Update(Dyn2CampiProprieta cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(Dyn2CampiProprieta cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void VerificaRecordCollegati(Dyn2CampiProprieta cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void EffettuaCancellazioneACascata(Dyn2CampiProprieta cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(Dyn2CampiProprieta cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }


    }
}


